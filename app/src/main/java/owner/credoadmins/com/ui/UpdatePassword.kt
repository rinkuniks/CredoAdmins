package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_update_password.*
import kotlinx.android.synthetic.main.content_update_password.*
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.updatepassword.UpdatePassRequest
import owner.credoadmins.com.models.updatepassword.UpdatePassResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UpdatePassword : AppCompatActivity() {

    private val TAG = Login::class.java.getSimpleName()
    private var constant = Constants()
    var mobile = String()
    private var exit: Boolean? = false
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)
        setSupportActionBar(toolbar)

        //Status Bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        submitButtonUpdatePassword.setOnClickListener {
            val newpass = newPasswordChangePassword.text.toString()
            val newpassrepeat = repeatNewPassword.text.toString()

            //Shared Preferneces For mobile
            val sp = getSharedPreferences(constant.PREFERENCE_NAME , Context.MODE_PRIVATE)
            mobile =sp.getString(constant.MOBILE_NO, mobile )

            //validation
            if (newpass.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newpassrepeat.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                newpasssubmit(mobile , newpass , newpassrepeat)
            } else {
                constant.IntenetSettings(this)
            }
        }

    }

    private fun newpasssubmit(mobile: String?, newpass: String, newpassrepeat: String) {
        showLoadingDialog()
        val request = UpdatePassRequest()
        request.setUserdata(mobile!!)
        request.setPass(newpass)
        request.setPassRepeat(newpassrepeat)

        source.getRestAPI()!!.userupdatepassword(request).enqueue(object : Callback<UpdatePassResponse> {
            override fun onResponse(call: Call<UpdatePassResponse>, response: Response<UpdatePassResponse>) {
                Log.d(TAG , "onResponse : login $response")
                passUpdateResponse(Objects.requireNonNull<UpdatePassResponse>(response.body()))
                dismissLoadingDialog()
            }

            override fun onFailure(call: Call<UpdatePassResponse>, t: Throwable) {
                Log.d(TAG , "onResponse : login nfcghfghfhgfdhgdhgd")
                t.printStackTrace()
                dismissLoadingDialog()
            }
        })
    }

    private fun passUpdateResponse(body: UpdatePassResponse?) {
        val response = body!!.responseCode
        val description = body.description
        Log.d(TAG , "responseCode   :  $response")
        when (response) {
            "200" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
                val i = Intent(this , SchoolList::class.java)
                startActivity(i)
                finish()
                overridePendingTransition(R.anim.slide_from_right , R.anim.slide_to_left)
            }
            "301" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
            }
            "204" ->Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (exit!!) {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(homeIntent)
            finish() // finish activity
        } else {
            Toast.makeText(this , "Press Back again to Exit." , Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({ exit = false } , (2 * 1000).toLong())
        }
    }

    fun showLoadingDialog() {

        if (progress == null) {
            progress = ProgressDialog(this)
            progress!!.setTitle(R.string.loading_title)
            progress!!.setMessage("Loading......")
        }
        progress!!.show()
        progress!!.setCancelable(false)
    }

    fun dismissLoadingDialog() {

        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
        }
    }

    override// disable keyboard out side the edit text
    fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken , 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
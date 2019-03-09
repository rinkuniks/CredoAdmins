package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.content_change_password.*
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.changePassword.ChangePasswordRequest
import owner.credoadmins.com.models.changePassword.ChangePasswordResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ChangePassword : AppCompatActivity() {

    var userid : String? = null
    private val TAG = ChangePassword::class.java.getSimpleName()
    private var constant = Constants()
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setSupportActionBar(toolbar)

        //Status bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //shared preference for admin id
        val sp = getSharedPreferences(constant.PREFERENCE_NAME, Context.MODE_PRIVATE)
        userid = sp.getString(constant.USER_ID, userid)

        //for keyboard to come when clicked
        val oldpassword = old_password_chnage
        val newpassword = new_password_chnage
        val repeatpassword = repeat_password_chnage

        oldpassword.setOnTouchListener(View.OnTouchListener { v, event ->
            v.isFocusable = true
            v.isFocusableInTouchMode = true
            false
        })
        newpassword.setOnTouchListener(View.OnTouchListener { v, event ->
            v.isFocusable = true
            v.isFocusableInTouchMode = true
            false
        })
        repeatpassword.setOnTouchListener(View.OnTouchListener { v, event ->
            v.isFocusable = true
            v.isFocusableInTouchMode = true
            false
        })

        button_change_password.setOnClickListener {

            val oldpass = old_password_chnage.text.toString()
            val newpass = new_password_chnage.text.toString()
            val repeatpass = repeat_password_chnage.text.toString()

            //validations
            if (oldpass.isNotEmpty()) {
                Log.d("", "")
            } else {
                Toast.makeText(this, "Enter Old Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newpass.isNotEmpty()) {
                Log.d("", "")
            } else {
                Toast.makeText(this, "Enter New Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (repeatpass.isNotEmpty()) {
                Log.d("", "")
            } else {
                Toast.makeText(this, "Enter Repeat Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                changepassProcess(userid , oldpass , newpass, repeatpass)
            } else {
                constant.IntenetSettings(this)
            }
        }

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun changepassProcess(userid: String?, oldpass: String, newpass: String, repeatpass: String)
    {
        showLoadingDialog()
        Log.d("==========>", userid + " , " + oldpass + " , " + newpass + " , " + repeatpass)

        val request = ChangePasswordRequest()
        request.setUserId(userid!!)
        request.setUserOldPass(oldpass)
        request.setUserNewPass(newpass)
        request.setUserConfirm(repeatpass)
        Log.d("==>", userid + " , " + oldpass + " , " + newpass + " , " + repeatpass)

        old_password_chnage.setText("").toString()
        new_password_chnage.setText("").toString()
        repeat_password_chnage.setText("").toString()

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userChangePassword(request).enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    changepassResponse(Objects.requireNonNull<ChangePasswordResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    Log.d(TAG, "onResponse : login nfcghfghfhgfdhgdhgd")
                    t.printStackTrace()
                    dismissLoadingDialog()
                }
            })
        } else {
            dismissLoadingDialog()
            constant.IntenetSettings(this)
        }
    }

    private fun changepassResponse(body: ChangePasswordResponse?) {
        val response = body!!.responseCode
        val description = body.description
        when (response) {
            "200" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
            }
            "204" -> Toast.makeText(this, description.toString(), Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this, description.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoadingDialog() {
        if (progress == null) {
            progress = ProgressDialog(this)
            progress!!.setTitle(R.string.loading_title)
            progress!!.setMessage("Loading......")
        }
        progress!!.show()
        progress!!.setCancelable(false)
    }

    private fun dismissLoadingDialog() {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

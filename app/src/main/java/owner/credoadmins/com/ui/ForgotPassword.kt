package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.forgotPassword.ForgotPasswordRequest
import owner.credoadmins.com.models.forgotPassword.ForgotPasswordResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ForgotPassword : AppCompatActivity() {

    private val TAG = ForgotPassword::class.java.getSimpleName()
    private var constant = Constants()
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setSupportActionBar(toolbar)

        //Status bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //back Button Forgot Password
        val backLayoutForgot = back_layout_forgot
        backLayoutForgot.setOnClickListener({ onBackPressed() })

        submit_button_forgot_password.setOnClickListener {

            val mobile = mobile_textView.text.toString()

            //Validations
            if (mobile.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (mobile.matches(constant.MobilePattern.toRegex())) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter Valid Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                forgotProcess(mobile)
            } else {
                constant.IntenetSettings(this)
            }
        }

    }

    private fun forgotProcess(mobile: String) {
        showLoadingDialog()
        Log.d("==========>", mobile)

        val request = ForgotPasswordRequest()
        request.setUserdata(mobile)
        Log.d("===>", mobile)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userForgot(request).enqueue(object : Callback<ForgotPasswordResponse>
            {
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>)
                {
                    Log.d(TAG, "onResponse : login $response")
                    forgotResponse(Objects.requireNonNull<ForgotPasswordResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
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

    private fun forgotResponse(body: ForgotPasswordResponse?) {
        val response = body!!.responseCode
        val description = body.description
        when (response) {
            "200" -> {
                Toast.makeText(this,description.toString(), Toast.LENGTH_SHORT).show()
                val i = Intent(this , OtpVerify::class.java)
                i.putExtra("value","2")
                startActivity(i)
                overridePendingTransition(R.anim.slide_to_right , R.anim.slide_from_left)
                finish()
            }
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
}
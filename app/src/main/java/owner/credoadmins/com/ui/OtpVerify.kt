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
import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.content_otp_verify.*
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.forgotOtpVerify.OtpVerificationRequest
import owner.credoadmins.com.models.forgotOtpVerify.OtpVerificationResponse
import owner.credoadmins.com.models.resendOtp.ResendOtpRequest
import owner.credoadmins.com.models.resendOtp.ResendOtpResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OtpVerify : AppCompatActivity() {

    private var constant = Constants()
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()
    private var exit: Boolean? = false
    var mobile = String()
    var ss = String()
    private val TAG = OtpVerify::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)
        setSupportActionBar(toolbar)

        //for above title bar color set
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        resendOtpTextView.setOnClickListener {
            editTextOtp.setText(" ")
            val sp = getSharedPreferences(constant.PREFERENCE_NAME , Context.MODE_PRIVATE)
            mobile =sp.getString(constant.MOBILE_NO, mobile )
            mobile_no_text.text = mobile

            if (constant.haveInternet(this)) {
                //Function to call Api
                otpresend(mobile)
            } else {
                constant.IntenetSettings(this)
            }
        }

        button_submit_otp.setOnClickListener {

            val otp = editTextOtp.text.toString()
            val sp = getSharedPreferences(constant.PREFERENCE_NAME , Context.MODE_PRIVATE)
            mobile =sp.getString(constant.MOBILE_NO, mobile )
            mobile_no_text.text = mobile

            //Validations
            if (otp.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter OTP Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                    forgotpassword(mobile,otp)
            } else {
                constant.IntenetSettings(this)
            }
        }

    }

    private fun otpresend(mobile: String?) {
        showLoadingDialog()
        Log.d("==========>",mobile)
        val request = ResendOtpRequest()
        request.setUserdata(mobile!!)

        source.getRestAPI()!!.userOtpResend(request).enqueue(object : Callback<ResendOtpResponse> {
            override fun onResponse(call: Call<ResendOtpResponse>, response: Response<ResendOtpResponse>) {
                Log.d(TAG , "onResponse : login $response")
                otpResendResponse(Objects.requireNonNull<ResendOtpResponse>(response.body()))
                dismissLoadingDialog()
            }

            override fun onFailure(call: Call<ResendOtpResponse>, t: Throwable) {
                Log.d(TAG , "onResponse : login nfcghfghfhgfdhgdhgd")
                t.printStackTrace()
                dismissLoadingDialog()
            }
        })
    }

    private fun otpResendResponse(body: ResendOtpResponse?) {
        val response = body!!.responseCode
        val description = body.description
        Log.d(TAG , "responseCode   :  $response")
        when (response) {
            "200" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
            }
            "301" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
            }
            "204" ->Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun forgotpassword(mobile: String?, otp: String) {
        showLoadingDialog()
        Log.d("==========>", mobile)
        val request = OtpVerificationRequest()
        request.setUserdata(mobile!!)
        request.setOtp(otp)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userOtpVerify(request).enqueue(object : Callback<OtpVerificationResponse> {
                override fun onResponse(
                    call: Call<OtpVerificationResponse>,
                    response: Response<OtpVerificationResponse>
                ) {
                    Log.d(TAG, "onResponse : login $response")
                    forgotpassResponse(Objects.requireNonNull<OtpVerificationResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<OtpVerificationResponse>, t: Throwable) {
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

    private fun forgotpassResponse(body: OtpVerificationResponse?) {
        val response = body!!.responseCode
        val description = body.description
        Log.d(TAG , "responseCode   :  $response")
        when (response) {
            "200" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
                val i = Intent(this , UpdatePassword::class.java)
                startActivity(i)
                finish()
                overridePendingTransition(R.anim.slide_from_right , R.anim.slide_to_left)
            }
            "422" -> {
                Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show()
                val i = Intent(this , UpdatePassword::class.java)
                startActivity(i)
                finish()
                overridePendingTransition(R.anim.slide_from_right , R.anim.slide_to_left)
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

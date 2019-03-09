package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.content_login.*
import owner.credoadmins.com.R
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.loginModel.LoginRequest
import owner.credoadmins.com.models.loginModel.LoginResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Login : AppCompatActivity() {

    private val TAG = Login::class.java.getSimpleName()
    private var constant = Constants()
    private var exit: Boolean? = false
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        //setSupportActionBar(toolbar)
        loginforgotpassword.setOnClickListener {
            val i = Intent(this,ForgotPassword::class.java)
            startActivity(i)
        }

        loginSubmit.setOnClickListener {

            val mobile = loginMobile.text.toString()
            val password = loginPassword.text.toString()

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
            if (password.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                logInProcess(mobile , password)
            } else {
                constant.IntenetSettings(this)
            }
        }
    }

    private fun logInProcess(mobile: String, password: String) {
        showLoadingDialog()
        Log.d("==========>", mobile + " , " + password)

        val request = LoginRequest()
        request.setUserdata(mobile)
        request.setPassword(password)
        Log.d("==>", mobile + " , " + password)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userLogin(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    logInResponse(Objects.requireNonNull<LoginResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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

    private fun logInResponse(body: LoginResponse?) {
        val response = body!!.responseCode
        val description = body.description
        when (response) {
            "200" -> {
                val email = body.loginresult.email
                val mobile = body.loginresult.mobile
                val owner_id = body.loginresult.owner_id
                val name = body.loginresult.name

                val preferences = getSharedPreferences(constant.PREFERENCE_NAME , 0)
                preferences.edit().putString(constant.USER_ID , owner_id).apply()
                preferences.edit().putString(constant.MOBILE_NO , mobile).apply()
                preferences.edit().putString(constant.NAME , name).apply()
                preferences.edit().putString(constant.EMAIL_ID , email).apply()

                Toast.makeText(this , "Welcome " + name , Toast.LENGTH_SHORT).show()
                val i = Intent(this , SchoolList::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.slide_to_right , R.anim.slide_from_left)
                finish()
            }
            "204" -> Toast.makeText(this,description.toString(), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this,description.toString(), Toast.LENGTH_SHORT).show()
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
package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import owner.credoadmins.com.R

import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.content_notifications.*
import owner.credoadmins.com.adapters.NotificationsAdapters
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.loginModel.LoginResponse
import owner.credoadmins.com.models.notifications.NotificationModel
import owner.credoadmins.com.models.notifications.NotificationRequest
import owner.credoadmins.com.models.notifications.NotificationResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Notifications : AppCompatActivity() {

    var adminid = String()
    private val TAG = Notifications::class.java.getSimpleName()
    private var constant = Constants()
    private var notificationdata = ArrayList<NotificationModel>()
    private var myAdapter: NotificationsAdapters? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        setSupportActionBar(toolbar)

        //Status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //Shared Preference for admin id
        val sp = getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //RecyclerView & Adapter
        val recyclerView = notificationRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this , LinearLayout.VERTICAL , false)
        myAdapter = NotificationsAdapters(notificationdata)
        recyclerView.adapter = myAdapter

        //Function to call Api
        getnotifications(adminid)

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getnotifications(adminid: String?) {
        showLoadingDialog()
        val request = NotificationRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userNotification(request).enqueue(object : Callback<NotificationResponse> {
                override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    notifiResponse(Objects.requireNonNull<NotificationResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
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

    private fun notifiResponse(body: NotificationResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                notificationdata.clear()
                notificationdata.addAll(body.notification!!)
                myAdapter!!.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(this , description , Toast.LENGTH_SHORT).show()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

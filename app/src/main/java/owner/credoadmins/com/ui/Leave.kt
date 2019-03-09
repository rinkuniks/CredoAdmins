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
import kotlinx.android.synthetic.main.activity_leave.*
import kotlinx.android.synthetic.main.content_leave.*
import owner.credoadmins.com.adapters.LeaveAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.leave.LeaveModel
import owner.credoadmins.com.models.leave.LeaveRequest
import owner.credoadmins.com.models.leave.LeaveResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Leave : AppCompatActivity() {

    var adminid = String()
    private val TAG = Leave::class.java.getSimpleName()
    private var constant = Constants()
    private var myAdapter: LeaveAdapter? = null
    private var leavedata = ArrayList<LeaveModel>()
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave)
        setSupportActionBar(toolbar)

        //get color in status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        val sp = getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //Recycler View for leaves
        val recyclerView = leaverecycler
        leaverecycler.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        myAdapter = LeaveAdapter(leavedata, this@Leave)
        recyclerView.adapter = myAdapter

        //function to call Api
        getholidays(adminid)

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getholidays(adminid: String?) {
        showLoadingDialog()
        val request = LeaveRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userLeavelist(request).enqueue(object : Callback<LeaveResponse> {
                override fun onResponse(call: Call<LeaveResponse>, response: Response<LeaveResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    leaveResponse(Objects.requireNonNull<LeaveResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<LeaveResponse>, t: Throwable) {
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

    private fun leaveResponse(body: LeaveResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                leavedata.clear()
                leavedata.addAll(body.leaveresult!!)
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
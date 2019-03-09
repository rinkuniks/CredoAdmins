package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_school_list.*
import kotlinx.android.synthetic.main.content_school_list.*
import owner.credoadmins.com.adapters.SchoolListAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.schoollist.SchoolListRequest
import owner.credoadmins.com.models.schoollist.SchoolListResponse
import owner.credoadmins.com.models.schoollist.SchoolListResult
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class  SchoolList : AppCompatActivity() {

    var userId = String()
    private var constant = Constants()
    private var schoollist = ArrayList<SchoolListResult>()
    private var exit: Boolean? = false
    private var myAdapter : SchoolListAdapter? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()
    private val TAG = SchoolList::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)
        setSupportActionBar(toolbar)

        //Status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //shared preferneces for user id
        val sp = getSharedPreferences(constant.PREFERENCE_NAME , Context.MODE_PRIVATE)
        userId = sp.getString(constant.USER_ID , userId)

        //recycler view
        val recyclerView = recyclerviewSchoolList
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        myAdapter = SchoolListAdapter(this,schoollist)
        recyclerView.adapter = myAdapter

        if (userId == "") run {
            val i = Intent(this@SchoolList , Login::class.java)
            startActivity(i)
            finish()
        }else{
            //Function to call Api
            getSchoolList(userId)
        }
    }

    private fun getSchoolList(userId: String?) {
        showLoadingDialog()
        Log.d("==========>",userId)

        val request = SchoolListRequest()
        request.setUserdata(userId!!)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userSchoolList(request).enqueue(object : Callback<SchoolListResponse> {
                override fun onResponse(call: Call<SchoolListResponse>, response: Response<SchoolListResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    schoollistResponse(Objects.requireNonNull<SchoolListResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<SchoolListResponse>, t: Throwable) {
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

    private fun schoollistResponse(body: SchoolListResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                schoollist.clear()
                schoollist.addAll(body.schoolListresult!!)
                myAdapter!!.notifyDataSetChanged()
                Log.d("adminid",body.schoolListresult!!.get(0).admin_id)
                val preferences = getSharedPreferences(constant.PREFERENCE_NAME , 0)
                preferences.edit().putString(constant.ADMIN_ID , body.schoolListresult!!.get(0).admin_id).apply()
            }
            "204" -> {
                Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dismissLoadingDialog() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
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
}
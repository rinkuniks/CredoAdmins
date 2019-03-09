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
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.content_events.*
import owner.credoadmins.com.adapters.EventAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.events.EventModel
import owner.credoadmins.com.models.events.EventRequest
import owner.credoadmins.com.models.events.EventResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Events : AppCompatActivity() {

    var adminid = String()
    private val TAG = Events::class.java.getSimpleName()
    private var constant = Constants()
    private var eventList = ArrayList<EventModel>()
    private var myAdapter: EventAdapter? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setSupportActionBar(toolbar)

        //Status bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //shared prefernece calling admin id
        val sp = this.getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        val recyclerView = eventsrecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        myAdapter = EventAdapter(this,eventList)
        recyclerView.adapter = myAdapter

        //function to call Api
        getevents(adminid)

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getevents(adminid: String?) {
        showLoadingDialog()
        val request = EventRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userEventlist(request).enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    eventlistResponse(Objects.requireNonNull<EventResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
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

    private fun eventlistResponse(body: EventResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                eventList.clear()
                eventList.addAll(body.eventresult!!)
                myAdapter!!.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(this , description , Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
       finish()
        //onBackPressed()
        return true
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
}

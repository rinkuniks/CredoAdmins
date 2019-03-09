package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_detail.*
import owner.credoadmins.com.R
import owner.credoadmins.com.adapters.ImagesAdaptorEvents
import owner.credoadmins.com.adapters.VideosAdaptorEvents
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsPhotos
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsRequest
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsResponse
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsVideos
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.util.*

class EventDetail : AppCompatActivity() {

    internal var photosLay: LinearLayout? = null
    internal var videosLay:LinearLayout? = null
    internal var photosButton: RelativeLayout? = null
    var videosButton:RelativeLayout? = null
    private var constant = Constants()
    internal var recyclerViewPhotosInEvents: RecyclerView? = null
    internal var recyclerViewVideosInEvents:RecyclerView? = null
    private val eventDetailsPhotosList = ArrayList<EventDetailsPhotos>()
    private val eventDetailsVideosList = ArrayList<EventDetailsVideos>()
    internal var iAdaptor: ImagesAdaptorEvents? = null
    internal var vAdaptor: VideosAdaptorEvents? = null
    internal var mainImage: ImageView? = null
    internal var title: TextView? = null
    internal var dateTextEvent:TextView? = null
    internal var descriptionInEvent:TextView? = null
    private var progress: ProgressDialog? = null
    internal var eventId: String? = null
    internal var photoUrl:String? = null
    private val TAG = EventDetail::class.java.getSimpleName()
    private var source = CredoAdminSource()
    internal var numberOfColumns = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
        }

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                eventId = null
            } else {
                eventId = extras.getString("value")
                photoUrl = extras.getString("photoUrl")
            }
        } else {
            eventId = savedInstanceState.getSerializable("value") as String
        }

        backarrow.setOnClickListener {
            finish()
        }

        photosButton = photosButtonRelative
        videosButton = videosButtonRelative
        photosLay = photosLayout
        videosLay = videosLayout

        videosLayout!!.visibility = View.GONE
        photosButton!!.setBackgroundResource(R.drawable.image_video_back_ground)

        mainImage = mainImageInDetails
        title = titleText
        dateTextEvent = dateTextEventDetails
        descriptionInEvent = descriptionInEventDetails
        getEventDetails()

        recyclerViewPhotosInEvents = findViewById(R.id.recyclerViewPhotosInEvents)
        recyclerViewPhotosInEvents!!.layoutManager = GridLayoutManager(this, numberOfColumns) as RecyclerView.LayoutManager?
        iAdaptor = ImagesAdaptorEvents(this , eventDetailsPhotosList)
        recyclerViewPhotosInEvents!!.adapter = iAdaptor

        recyclerViewVideosInEvents = findViewById(R.id.recyclerViewVideosInEvents)
        recyclerViewVideosInEvents!!.setLayoutManager(GridLayoutManager(this, numberOfColumns))
        vAdaptor = VideosAdaptorEvents(this , eventDetailsVideosList)
        recyclerViewVideosInEvents!!.setAdapter(vAdaptor)

        photosButton!!.setOnClickListener {
            photosLayout!!.visibility = View.VISIBLE
            videosLayout!!.visibility = View.GONE
            videosButton!!.setBackgroundDrawable(resources.getDrawable(R.drawable.image_video_back_ground_null))
            photosButton!!.setBackgroundResource(R.drawable.image_video_back_ground)
        }

        videosButton!!.setOnClickListener(View.OnClickListener {
            photosLayout!!.visibility = View.GONE
            videosLayout!!.visibility = View.VISIBLE
            photosButton!!.setBackgroundDrawable(resources.getDrawable(R.drawable.image_video_back_ground_null))
            videosButton!!.setBackgroundResource(R.drawable.image_video_back_ground)
        })
    }

    private fun getEventDetails() {
        showLoadingDialog()
        val request = EventDetailsRequest()
        request.setUserdata(eventId!!)
        Toast.makeText(this, eventId, Toast.LENGTH_SHORT).show()
        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userEventDetaillist(request).enqueue(object : Callback<EventDetailsResponse> {
                override fun onResponse(call: Call<EventDetailsResponse>, response: Response<EventDetailsResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    getEventList(Objects.requireNonNull<EventDetailsResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<EventDetailsResponse>, t: Throwable) {
                    t.printStackTrace()
                    dismissLoadingDialog()
                    if (t is SocketTimeoutException) {
                    }
                }
            })
        } else {
            dismissLoadingDialog()
            constant.IntenetSettings(this)
        }
    }

    private fun getEventList(body: EventDetailsResponse) {
        val response = body.responseCode
        val description = body.description
        when (response) {
            "200" -> {
                //String event = body.getResults().getEventId();
                val descriptionOfEvent = body.results!!.description
                val dateEvent = body.results!!.eventDate
                val eventTitle = body.results!!.title
                descriptionInEventDetails!!.text = descriptionOfEvent
                dateTextEventDetails!!.text = dateEvent
                titleText!!.text = eventTitle

                Glide.with(applicationContext).load(photoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .centerCrop()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mainImageInDetails)

                eventDetailsPhotosList.clear()
                eventDetailsPhotosList.addAll(body.photos!!)
                iAdaptor!!.notifyDataSetChanged()

                eventDetailsVideosList.clear()
                eventDetailsVideosList.addAll(body.videos!!)
                vAdaptor!!.notifyDataSetChanged()
            }
            "204" -> Snackbar.make(findViewById<View>(android.R.id.content), description!!, Snackbar.LENGTH_SHORT).show()
            "301" -> Snackbar.make(findViewById<View>(android.R.id.content), description!!, Snackbar.LENGTH_SHORT).show()
            else -> Snackbar.make(findViewById<View>(android.R.id.content), description!!, Snackbar.LENGTH_SHORT).show()
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
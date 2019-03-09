package owner.credoadmins.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import owner.credoadmins.com.R
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsVideos
import java.util.regex.Pattern

class VideosAdaptorEvents(private val context: Context, private val eventDetailsVideosList: List<EventDetailsVideos>) :
    RecyclerView.Adapter<VideosAdaptorEvents.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdaptorEvents.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_adaptor_videos, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideosAdaptorEvents.MyViewHolder, position: Int) {
        val list = eventDetailsVideosList[position]
        var vId: String? = null
        val youTubeUrl = list.youtubeUrl

        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"

        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youTubeUrl)

        if (matcher.find()) {
            vId = matcher.group()
            Log.d("vId", vId!! + "")

            val url = "https://img.youtube.com/vi/$vId/0.jpg"

            Glide.with(context).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewVideo)
        }

        val finalVId = vId
        holder.imageViewVideo.setOnClickListener {
//            val i = Intent(context, YoutubeVideos::class.java)
//            i.putExtra("value", finalVId)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return eventDetailsVideosList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewVideo: ImageView

        init {
            imageViewVideo = itemView.findViewById(R.id.imageViewVideo)
        }
    }
}
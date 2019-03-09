package owner.credoadmins.com.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import owner.credoadmins.com.R
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsPhotos

class ImagesAdaptorEvents(private val context: Context, private val eventDetailsPhotosList: List<EventDetailsPhotos>) :
    RecyclerView.Adapter<ImagesAdaptorEvents.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdaptorEvents.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_details_photos, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = eventDetailsPhotosList[position]
        val imageUrl = list.photo
        Glide.with(context).load(imageUrl)
            .thumbnail(0.5f)
            .crossFade()
            .centerCrop()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageViewPhotos)
        holder.imageViewPhotos.setOnClickListener { view -> showFullImageInDialog(imageUrl, view) }
    }

    private fun showFullImageInDialog(imageUrl: String?, view: View) {

        //        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        val builder = AlertDialog.Builder(view.context)


        @SuppressLint("InflateParams") val myView =
            LayoutInflater.from(context).inflate(R.layout.image_dialog_layout, null)
        //        LayoutInflater inflater = LayoutInflater.from(context);
        ////                getLayoutInflater();
        //        View dialoglayout = inflater.inflate(R.layout.image_dialog_layout, null);
        val imgOriginal = myView.findViewById<ImageView>(R.id.imgOriginal)

        Glide.with(context).load(imageUrl)
            .thumbnail(0.5f)
            .crossFade()
            .centerCrop()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgOriginal)

        builder.setView(myView)
        builder.create()
        builder.show()
    }

    override fun getItemCount(): Int {
        return eventDetailsPhotosList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewPhotos: ImageView

        init {
            imageViewPhotos = itemView.findViewById(R.id.imageViewPhotos)

        }
    }
}

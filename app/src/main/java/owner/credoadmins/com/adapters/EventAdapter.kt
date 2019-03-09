package owner.credoadmins.com.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerviewevents.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.events.EventModel
import owner.credoadmins.com.ui.EventDetail

class EventAdapter (val context : Context,val eventlist : ArrayList<EventModel>) : RecyclerView.Adapter<EventAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v= LayoutInflater.from(p0.context).inflate(R.layout.recyclerviewevents, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return eventlist.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val event = eventlist[p1]
        Picasso.get().load(event.photo)
            .fit().placeholder(R.drawable.app_icon).into(p0.image)
        p0.title.text = event.title
        p0.date.text = event.event_date
        val eventId = event.event_id
        val photoUrl = event.photo
        p0.eventchange.setOnClickListener{
            val i = Intent(context,EventDetail::class.java)
            i.putExtra("value", eventId)
            i.putExtra("photoUrl", photoUrl)
            context.startActivity(i)
        }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.eventimage
        val title = itemView.eventtitletext
        val date = itemView.eventdate
        val eventchange = itemView.eventSelect
    }

}
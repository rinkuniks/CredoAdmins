package owner.credoadmins.com.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.notificationrecyclerview.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.notifications.NotificationModel
import java.util.ArrayList

class NotificationsAdapters ( val notificationlist : ArrayList<NotificationModel>) :
    RecyclerView.Adapter<NotificationsAdapters.MyViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.notificationrecyclerview,p0,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notificationlist.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val notilist = notificationlist[p1]
        p0.datedis.text = notilist.date
        p0.titlenot.text = notilist.title
        p0.notidetail.text = notilist.description
        p0.datee.text = notilist.date
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val datedis = itemView.datedisplay
        val titlenot = itemView.titlenotification
        val notidetail = itemView.detailnotification
        val datee = itemView.datenotification
    }
}
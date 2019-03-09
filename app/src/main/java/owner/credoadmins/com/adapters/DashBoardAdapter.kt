package owner.credoadmins.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dashboard.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.DashBoardModel
import java.util.ArrayList

class DashBoardAdapter (val mContext: Context, val dashboardlist : ArrayList<DashBoardModel>) :
    RecyclerView.Adapter<DashBoardAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.dashboard,p0,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dashboardlist.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val dash = dashboardlist[p1]
        p0.icon.setImageResource(dash.images.toInt())
        p0.icontitle.text = dash.textView
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val icon = itemView.image_view_dash_board
        val icontitle = itemView.text_view_dash_board
    }
}
package owner.credoadmins.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerviewclass.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.classeslist.ClassesListModel
import java.util.ArrayList

class ClassesListAdapter(val mContext: Context, val classeslist: ArrayList<ClassesListModel>) :
    RecyclerView.Adapter<ClassesListAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.dashboard,p0,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return classeslist.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
       val classes = classeslist[p1]
        p0.timefrom.text = classes.timefrom
        p0.timeto.text = classes.timeto
        p0.subject.text = classes.subjectname
        p0.teacher.text = classes.teachername
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val timefrom = itemView.timefrom
        val timeto = itemView.timeto
        val subject = itemView.subjectname
        val teacher = itemView.teachername
    }

}
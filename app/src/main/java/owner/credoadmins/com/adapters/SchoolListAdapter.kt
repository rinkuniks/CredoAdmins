package owner.credoadmins.com.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerviewschoollist.view.*
import owner.credoadmins.com.MainActivity
import owner.credoadmins.com.R
import owner.credoadmins.com.models.schoollist.SchoolListResult
import java.util.*

class SchoolListAdapter (val mContext: Context, val schoollist : ArrayList<SchoolListResult>) :
    RecyclerView.Adapter<SchoolListAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.recyclerviewschoollist,p0
            ,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
       return schoollist.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val listSchool = schoollist[p1]

        //set Image with Url given in API
        Picasso.get().
            load("https://credoapp.in/superadmin_assets/images/schoolpackages_uploads/"+listSchool.logo)
            .fit().placeholder(R.drawable.app_icon).into(p0.image)

        p0.schoolname.text = listSchool.school_name
        p0.schooladdress.text = listSchool.address
        p0.schoolcity.text = listSchool.city

        p0.schoolid.setOnClickListener {
            val i = Intent(mContext, MainActivity::class.java)

            //set accordingly
            i.putExtra("id",listSchool.admin_id)
            i.putExtra("scname",listSchool.school_name)
            i.putExtra("sccity",listSchool.city)
            i.putExtra("add",listSchool.address)
            i.putExtra("sclogo","https://credoapp.in/superadmin_assets/images/schoolpackages_uploads/"+listSchool.logo)

            // for specfic phones like motorola
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            mContext.startActivity(i)
        }
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.circularimageschool
        val schoolname = itemView.schoolname
        val schooladdress = itemView.schooladdress
        val schoolcity = itemView.schoolcity
        val schoolid = itemView.individualSchoolTouch

    }
}
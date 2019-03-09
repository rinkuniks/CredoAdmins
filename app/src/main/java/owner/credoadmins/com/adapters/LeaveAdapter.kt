package owner.credoadmins.com.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.leaverecyclerview.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.leave.LeaveModel
import owner.credoadmins.com.ui.Leave
import java.util.ArrayList

class LeaveAdapter (val leaveList : ArrayList<LeaveModel>, val context : Leave) : RecyclerView.Adapter<LeaveAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.leaverecyclerview, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return leaveList.size
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       val list = leaveList[p1]
        //p0.todaydate.text = list.
        p0.teachername.text = list.teacher_name
        p0.section.text = list.class_name
        p0.todate.text = list.to_date
        p0.reason.text = list.reason
        p0.result.text = list.leave_status

        //convert leave_status code into Text
        if (list.leave_status.equals("1")){
            p0.result.text = "Approved"
        }else if (list.leave_status.equals("2")){
            p0.result.text = "Rejected"
        }

        //button accept
        p0.accept.setOnClickListener {
            p0.approvallayout.visibility = View.GONE
            p0.result.text = "Approved"
            p0.result.setTextColor(Color.parseColor("#FF0000"))
            p0.result.text = list.leave_status
        }

        //button reject
        p0.reject.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Reason For")
            val view = LayoutInflater.from(context).inflate(R.layout.alertlayout,null)
            dialogBuilder.setView(view)
            val alertdialog = dialogBuilder.create()
            alertdialog.show()
        }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val todaydate = itemView.buttontodayDate
        val teachername = itemView.textViewLeaveName
        val section = itemView.textViewLeaveSection
        val todate = itemView.textViewLeaveToDate
        val reason = itemView.textViewLeaveReason
        val result = itemView.textViewResult
        val approvallayout = itemView.linearLayoutApproval
        val reject = itemView.buttonLeaveReject
        val accept = itemView.buttonLeaveApprove

    }

}
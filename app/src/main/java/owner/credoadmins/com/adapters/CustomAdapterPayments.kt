package owner.credoadmins.com.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.amount_list.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.payments.PaidModel

class CustomAdapterPayments(val paymentList : ArrayList<PaidModel>) : RecyclerView.Adapter<CustomAdapterPayments.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
      val v = LayoutInflater.from(p0.context).inflate(R.layout.amount_list,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val payments: PaidModel = paymentList[p1]
        p0.nameview.text = payments.student_name
        p0.paidview.text = payments.paid
        p0.sectionview.text = payments.section //in api class is written not accepting that keyword
        p0.dueview.text = payments.balance
        p0.dateview.text = payments.paid_on
        p0.rollview.text = payments.roll
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val nameview = itemView.nameTextview
        val paidview = itemView.paidamont
        val sectionview = itemView.section
        val dueview = itemView.dueAmount
        val dateview = itemView.date
        val rollview = itemView.rollTextview
    }
}
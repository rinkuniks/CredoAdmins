package owner.credoadmins.com.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.accountrecyclerview.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.models.income.IncomeModel

class IncomeAdapter (val incomelist : ArrayList<IncomeModel>) : RecyclerView.Adapter<IncomeAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v= LayoutInflater.from(p0.context).inflate(R.layout.accountrecyclerview, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return incomelist.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val inclist = incomelist[p1]
        p0.showdate.text = inclist.date
        p0.shownarration.text = inclist.narration
        p0.showamount.text = inclist.amount

        //set the background alternate color in recyclerView
        if (p1%2==1){
            p0.layout.setBackgroundColor(Color.parseColor("#F1F1F1"))
        }else{
            p0.layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val showdate = itemView.textviewDate
        val shownarration = itemView.textviewNarration
        val showamount = itemView.textviewAmount
        val layout = itemView.accountlistrecyclerview
    }

}
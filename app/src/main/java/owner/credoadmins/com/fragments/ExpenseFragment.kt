package owner.credoadmins.com.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.android.synthetic.main.fragment_expense.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.adapters.ExpenseAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.accounts.ExpenseModel
import owner.credoadmins.com.models.accounts.ExpenseRequest
import owner.credoadmins.com.models.accounts.ExpenseResponse
import owner.credoadmins.com.models.payments.PaidListRequest
import owner.credoadmins.com.models.payments.PaidListResponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import owner.credoadmins.com.ui.AccountFilterExpense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ExpenseFragment : Fragment() {

    var adminid = String()
    private val TAG = ExpenseFragment::class.java.getSimpleName()
    private var constant = Constants()
    private var expenseslist = ArrayList<ExpenseModel>()
    private var myAdapter: ExpenseAdapter? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_expense, container, false)

        v.fabexpense.setOnClickListener {
            val i = Intent(context , AccountFilterExpense::class.java)
            startActivity(i)
        }

        //shared prefernece calling admin id
        val sp = activity!!.getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //Set RecyclerView
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerviewExpense)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        myAdapter = ExpenseAdapter(expenseslist)
        recyclerView.adapter = myAdapter

        //function to call Api
        getExpenseList(adminid)
        return v
    }

    private fun getExpenseList(adminid: String?) {
        showLoadingDialog()
        val request = ExpenseRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(context!!)) {
            source.getRestAPI()!!.userExpenselist(request).enqueue(object : Callback<ExpenseResponse> {
                override fun onResponse(call: Call<ExpenseResponse>, response: Response<ExpenseResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    expenselistResponse(Objects.requireNonNull<ExpenseResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<ExpenseResponse>, t: Throwable) {
                    Log.d(TAG, "onResponse : Expense nfcghfghfhgfdhgdhgd")
                    t.printStackTrace()
                    dismissLoadingDialog()
                }
            })
        } else {
            dismissLoadingDialog()
            constant.IntenetSettings(context!!)
        }
    }

    private fun expenselistResponse(body: ExpenseResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                totalexpensedisplay.text = body.total
                expenseslist.clear()
                expenseslist.addAll(body.expenselistresult!!)
                myAdapter!!.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(context , description , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoadingDialog() {
        if (progress == null) {
            progress = ProgressDialog(context)
            progress!!.setTitle(R.string.loading_title)
            progress!!.setMessage("Loading......")
        }
        progress!!.show()
        progress!!.setCancelable(false)
    }

    private fun dismissLoadingDialog() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
        }
    }
}

package owner.credoadmins.com.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.content_event_detail.view.*
import kotlinx.android.synthetic.main.fragment_expense.view.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.adapters.IncomeAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.income.IncomeModel
import owner.credoadmins.com.models.income.IncomeRequest
import owner.credoadmins.com.models.income.Incomeresponse
import owner.credoadmins.com.retrofit.CredoAdminSource
import owner.credoadmins.com.ui.AccountFilterIncome
import owner.credoadmins.com.ui.Accounts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class IncomeFragment : Fragment() {

    var adminid = String()
    private val TAG = IncomeFragment::class.java.getSimpleName()
    private var constant = Constants()
    private var incomelist = ArrayList<IncomeModel>()
    private var myAdapter: IncomeAdapter? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_income, container, false)

        v.fabincome.setOnClickListener {
            val i =Intent(context ,AccountFilterIncome::class.java)
            startActivity(i)
        }

        //shared prefernece calling admin id
        val sp = activity!!.getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //Set recyclerview
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerviewIncome)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        myAdapter = IncomeAdapter(incomelist)
        recyclerView.adapter = myAdapter

        //Function to call Api
        getIncomeList(adminid)
        return v
    }

    private fun getIncomeList(adminid: String?) {
        showLoadingDialog()
        val request = IncomeRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(context!!)) {
            source.getRestAPI()!!.userIncomelist(request).enqueue(object : Callback<Incomeresponse> {
                override fun onResponse(call: Call<Incomeresponse>, response: Response<Incomeresponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    incomelistResponse(Objects.requireNonNull<Incomeresponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<Incomeresponse>, t: Throwable) {
                    Log.d(TAG, "onResponse : login nfcghfghfhgfdhgdhgd")
                    t.printStackTrace()
                    dismissLoadingDialog()
                }
            })
        } else {
            dismissLoadingDialog()
            constant.IntenetSettings(context!!)
        }
    }

    private fun incomelistResponse(body: Incomeresponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                totalincomedisplay.text = body.total
                incomelist.clear()
                incomelist.addAll(body.incomelistresult!!)
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

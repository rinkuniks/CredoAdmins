package owner.credoadmins.com.fragments

import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.fragment_due_list.view.*
import owner.credoadmins.com.R
import owner.credoadmins.com.adapters.CustomAdapterPayments
import owner.credoadmins.com.adapters.NotificationsAdapters
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.payments.PaidListRequest
import owner.credoadmins.com.models.payments.PaidListResponse
import owner.credoadmins.com.models.payments.PaidModel
import owner.credoadmins.com.retrofit.CredoAdminSource
import owner.credoadmins.com.ui.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DueListFragment : Fragment() {

    var adminid = String()
    private val TAG = DueListFragment::class.java.getSimpleName()
    private var constant = Constants()
    private var paymentsPaid = ArrayList<PaidModel>()
    private var myAdapter: CustomAdapterPayments? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_due_list,container, false)

        //shared prefernece calling admin id
        val sp = activity!!.getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //Set recycler View
        val recyclerViewDue = v.recyclerViewDue
        recyclerViewDue.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        myAdapter = CustomAdapterPayments(paymentsPaid)
        recyclerViewDue.adapter = myAdapter

        // function to call Api
        getduelist(adminid)
        return v
    }

    private fun getduelist(adminid: String?) {
        showLoadingDialog()
        val request = PaidListRequest()
        request.setUserdata(adminid!!)

        if (constant.haveInternet(context!!)) {
            source.getRestAPI()!!.userPaidlist(request).enqueue(object : Callback<PaidListResponse> {
                override fun onResponse(call: Call<PaidListResponse>, response: Response<PaidListResponse>) {
                    Log.d(TAG, "onResponse : login $response")
                    duelistResponse(Objects.requireNonNull<PaidListResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<PaidListResponse>, t: Throwable) {
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

    private fun duelistResponse(body: PaidListResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
                paymentsPaid.clear()
                paymentsPaid.addAll(body.paidlistresult!!)
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
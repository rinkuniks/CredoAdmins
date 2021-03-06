package owner.credoadmins.com.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_account_filter.*
import kotlinx.android.synthetic.main.content_account_filter.*
import owner.credoadmins.com.adapters.AccountFilterExpenseAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.models.accountfilterexpense.AccExpFilterRequest
import owner.credoadmins.com.models.accountfilterexpense.AccExpFilterResponse
import owner.credoadmins.com.models.accountfilterexpense.AccountFilterExpenseModel
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AccountFilterExpense : AppCompatActivity() {

    var adminid = String()
    var fromdate = String()
    var todate = String()
    private val TAG = AccountFilterExpense::class.java.getSimpleName()
    private var constant = Constants()
    private var filterExpenselist = ArrayList<AccountFilterExpenseModel>()
    private var myAdapter: AccountFilterExpenseAdapter? = null
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_filter)
        setSupportActionBar(toolbar)

        //Declaration for calender
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        //Status bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //shared prefernece calling admin id
        val sp = this.getSharedPreferences(constant.PREFERENCE_NAME , 0)
        adminid = sp.getString(constant.ADMIN_ID , adminid)

        //Set recyclerview
        val recyclerView = filterExpenseRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        myAdapter = AccountFilterExpenseAdapter(filterExpenselist)
        recyclerView.adapter = myAdapter

        fromDateAccFilExpense.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear,
                                                                                 mMonth, mDay ->
             fromdate = fromDateAccFilExpense.setText(""+ mDay +"/"+ (mMonth+1) +"/"+ mYear).toString()
            },year,month,day)
            dpd.show()
        }

        toDateAccFilExpense.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear,
                                                                                 mMonth, mDay ->
            todate = toDateAccFilExpense.setText(""+ mDay +"/"+ (mMonth+1) +"/"+ mYear).toString()
            },year,month,day)
            dpd.show()
        }

        applyAccFilExpense.setOnClickListener {

            //Validations
            if (fromdate.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Select From Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (todate.isNotEmpty()) {
                Log.d("" , "")
            } else {
                Toast.makeText(this,"Select To Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (constant.haveInternet(this)) {
                //Function to call Api
                getfiltereddata(adminid)
            } else {
                constant.IntenetSettings(this)
            }
        }

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    private fun getfiltereddata(adminid: String)
    {
        showLoadingDialog()
        val from = fromDateAccFilExpense.text.toString()
        val to = toDateAccFilExpense.text.toString()
        val request = AccExpFilterRequest()
        request.setUserdata(adminid)
        request.setFromDate(from)
        request.setToDate(to)

        if (constant.haveInternet(this)) {
            source.getRestAPI()!!.userExpenseFilterlist(request).enqueue(object : Callback<AccExpFilterResponse>
            {
                override fun onResponse(call: Call<AccExpFilterResponse>, response:
                Response<AccExpFilterResponse>)
                {
                    Log.d(TAG, "onResponse : login $response")
                    expenseFilterListResponse(Objects.requireNonNull<AccExpFilterResponse>(response.body()))
                    dismissLoadingDialog()
                }

                override fun onFailure(call: Call<AccExpFilterResponse>, t: Throwable) {
                    t.printStackTrace()
                    dismissLoadingDialog()
                }
            })
        } else {
            dismissLoadingDialog()
            constant.IntenetSettings(this)
        }
    }

    private fun expenseFilterListResponse(body: AccExpFilterResponse?) {
        val response = body!!.responseCode
        val description = body.description

        when (response) {
            "200" -> {
             //  totalexpensedisplay.text = body.total (no layout for total is there in xml)
                filterExpenselist.clear()
                filterExpenselist.addAll(body.accfiltexpresult!!)
                myAdapter!!.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(this , description , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoadingDialog() {
        if (progress == null) {
            progress = ProgressDialog(this)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
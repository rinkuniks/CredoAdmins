package owner.credoadmins.com.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_payments.*
import kotlinx.android.synthetic.main.content_payments.*
import kotlinx.android.synthetic.main.fragment_due_list.*
import kotlinx.android.synthetic.main.fragment_paid_list.*
import owner.credoadmins.com.adapters.CustomAdapterPayments
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.fragments.DueListFragment
import owner.credoadmins.com.fragments.PaidListFragment
import owner.credoadmins.com.models.payments.PaidListRequest
import owner.credoadmins.com.models.payments.PaidListResponse
import owner.credoadmins.com.models.payments.PaidModel
import owner.credoadmins.com.retrofit.CredoAdminSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

class Payments : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)
        setSupportActionBar(toolbar)

        //Status  bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        paidList.setOnClickListener {
            val fManager = supportFragmentManager
            val tx = fManager.beginTransaction()
            tx.replace(R.id.fragment, PaidListFragment())
            tx.addToBackStack("true")
            tx.commit()
        }

        dueList.setOnClickListener {
            val fManager = supportFragmentManager
            val tx = fManager.beginTransaction()
            tx.replace(R.id.fragment, DueListFragment())
            tx.addToBackStack("true")
            tx.commit()
        }

        fab.setOnClickListener {
            val i = Intent(this,PaymentFilter::class.java)
            startActivity(i)
        }

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        //onBackPressed()
        return true
    }
}

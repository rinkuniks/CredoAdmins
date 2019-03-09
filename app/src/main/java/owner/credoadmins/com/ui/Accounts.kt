package owner.credoadmins.com.ui

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.content_accounts.*
import owner.credoadmins.com.fragments.ExpenseFragment
import owner.credoadmins.com.fragments.IncomeFragment

class Accounts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)
        setSupportActionBar(toolbar)

        //Status bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //Expense list will come
        expenseList.setOnClickListener {
            val fManager = supportFragmentManager
            val tx = fManager.beginTransaction()
            tx.replace(R.id.fragmentAccounts, ExpenseFragment())
            tx.addToBackStack("true")
            tx.commit()
        }

        //Income List will come
        incomeList.setOnClickListener {
            val fManager = supportFragmentManager
            val tx = fManager.beginTransaction()
            tx.replace(R.id.fragmentAccounts, IncomeFragment())
            tx.addToBackStack("true")
            tx.commit()
        }

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

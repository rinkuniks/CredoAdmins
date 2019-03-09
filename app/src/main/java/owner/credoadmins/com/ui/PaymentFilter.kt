package owner.credoadmins.com.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import owner.credoadmins.com.R
import kotlinx.android.synthetic.main.activity_payment_filter.*
import kotlinx.android.synthetic.main.content_payment_filter.*
import java.util.*

class PaymentFilter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_filter)
        setSupportActionBar(toolbar)

        //Status Bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        //Claender decleration
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        fromdateButton.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                fromdateButton.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            },year,month,day)
            dpd.show()
        }

        todateButton.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay ->
                todateButton.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            },year,month,day)
            dpd.show()
        }

        //spinner array
        val personNames = arrayOf("Class (Optional)",  "I Class",  "II Class",  "III Class",  "IV Class",  "V Class",  "VI Class", "VII Class",  "VIII Class",  "IX Class",  "X Class",  "XI Class","XII Class")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personNames)
        spinerclass.adapter = arrayAdapter

        spinerclass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Toast.makeText(this@MainActivity, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        //back button setup
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

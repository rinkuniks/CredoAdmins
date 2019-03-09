package owner.credoadmins.com.ui

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import owner.credoadmins.com.R

import kotlinx.android.synthetic.main.activity_messaging.*
import kotlinx.android.synthetic.main.content_messaging.*

class Messaging : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setSupportActionBar(toolbar)

        //Status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(owner.credoadmins.com.R.color.colorPrimary)
        }

        val personNames = arrayOf("Class",  "I Class",  "II Class",  "III Class",  "IV Class",  "V Class",  "VI Class", "VII Class",  "VIII Class",  "IX Class",  "X Class",  "XI Class","XII Class")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personNames)
        spinerrr.adapter = arrayAdapter

        spinerrr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Toast.makeText(this@MainActivity, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show()
                if (position.equals(1)){
                    Toast.makeText(this@Messaging,"Class I Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(2)){
                    Toast.makeText(this@Messaging,"Class II Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(3)){
                    Toast.makeText(this@Messaging,"Class III Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(4)){
                    Toast.makeText(this@Messaging,"Class IV Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(5)){
                    Toast.makeText(this@Messaging,"Class V Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(6)){
                    Toast.makeText(this@Messaging,"Class VI Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(7)){
                    Toast.makeText(this@Messaging,"Class VII Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(8)){
                    Toast.makeText(this@Messaging,"Class VIII Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(9)){
                    Toast.makeText(this@Messaging,"Class XI Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(10)){
                    Toast.makeText(this@Messaging,"Class X Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(11)){
                    Toast.makeText(this@Messaging,"Class XI Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(12)){
                    Toast.makeText(this@Messaging,"Class XII Selected",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        val msgNames = arrayOf("Message to",  "All",  "Teachers",  "Parents",  "Specific")
        val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, msgNames)
        spinerr2.adapter = arrayAdapter2

        spinerr2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               //  Toast.makeText(this@MainActivity, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show()
                if (position.equals(1)){
                    customlayout.visibility = View.GONE
                    Toast.makeText(this@Messaging,"All Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(2)){
                    customlayout.visibility = View.GONE
                    Toast.makeText(this@Messaging,"Teachers Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(3)){
                    customlayout.visibility = View.GONE
                    Toast.makeText(this@Messaging,"Parents Selected",Toast.LENGTH_SHORT).show()
                }else if (position.equals(4)){
                    customlayout.visibility = View.VISIBLE
                }else {
                    customlayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                //Toast.makeText(this@Messaging,"Please Select any from Message To",Toast.LENGTH_LONG).show()
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

package owner.credoadmins.com

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import owner.credoadmins.com.adapters.DashBoardAdapter
import owner.credoadmins.com.adapters.SchoolListAdapter
import owner.credoadmins.com.common.Constants
import owner.credoadmins.com.common.RecyclerTouchListen
import owner.credoadmins.com.interfaces.ClickListener
import owner.credoadmins.com.models.DashBoardModel
import owner.credoadmins.com.retrofit.CredoAdminSource
import owner.credoadmins.com.ui.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //runtime permission ,we can add more if required by using , after CAMERA
    private val permissions = arrayOf<String>(Manifest.permission.CAMERA)
    var value: String? = null
    var sname: String? = null
    var name = String()
    var url: String? = null
    var city: String? = null
    var address: String? = null
    private var dashboardlist = ArrayList<DashBoardModel>()
    private var myAdaptor: DashBoardAdapter? = null
    private var constant = Constants()
    private var progress: ProgressDialog? = null
    private var source = CredoAdminSource()
    private var schooladapter: SchoolListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        checkPermissions() // Check permissions on rumtime to allow Camera and Storage

        //set of values called from School List Activity Adapter
        value = intent.getStringExtra("id")
        sname = intent.getStringExtra("scname")
        url = intent.getStringExtra("sclogo")
        city = intent.getStringExtra("sccity")
        address = intent.getStringExtra("add")
        //set Image with Url given in API
        Picasso.get().load(url)
            .fit().placeholder(R.drawable.app_icon).into(dashboardCircularImage)
        schoolNameDashBoard.text = sname
        schoolCityDashBoard.text = city
        schoolAddressDashBoard.text = address

        //shared prefernece calling Name
        val sp = this.getSharedPreferences(constant.PREFERENCE_NAME , Context.MODE_PRIVATE)
        name = sp.getString(constant.NAME , name)

        //no. of item display in one coloum of recycler view
        val numberOfColumns = 4
        recyclerviewDashBoard.layoutManager = GridLayoutManager(this, numberOfColumns) as RecyclerView.LayoutManager?
        myAdaptor = DashBoardAdapter(this@MainActivity, dashboardlist)
        recyclerviewDashBoard.adapter = myAdaptor
        prepareDashBoardList()
        myAdaptor!!.notifyDataSetChanged()

        // Click and Move to other activity from Recycler view
        recyclerviewDashBoard.addOnItemTouchListener(
            RecyclerTouchListen(this,
                recyclerviewDashBoard, object : ClickListener {

                    override fun onClick(view: View, position: Int) {
                        Log.d("position=====>", position.toString() + "")

                        if (position == 0) {
                            val i = Intent(this@MainActivity, ClassSchedule::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 1) {
                            val i = Intent(this@MainActivity, Payments::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 2) {
                            val i = Intent(this@MainActivity, Leave::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 3) {
                            val i = Intent(this@MainActivity, Messaging::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 4) {
                            val i = Intent(this@MainActivity, Accounts::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 5) {
                            val i = Intent(this@MainActivity, VehicleTracking::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 6) {
                            val i = Intent(this@MainActivity, Events::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 7) {
                            val i = Intent(this@MainActivity, Notifications::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 8) {
                            val i = Intent(this@MainActivity, ChangePassword::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        if (position == 9) {
                            logOutProcess()
                        }
                        if (position == 10) {
                            val i = Intent()
                            i.action = Intent.ACTION_SEND
                            val sharesubject = "Enjoy !! and GO GREEN"
                            i.putExtra(Intent.EXTRA_TEXT, sharesubject)
                            i.type = "text/plain"
                            startActivity(Intent.createChooser(i, "Share to :"))
                        }
                        if (position == 11) {
                            val i = Intent(this@MainActivity, RateUs::class.java)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                    }
                    override fun onLongClick(view: View, position: Int) {}
                })
        )

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        //set username in navigation bar
//        val hView = nav_view.getHeaderView(0)
//        val nav_user = hView.navbartitle
//        nav_user.text = name
    }

    private fun checkPermissions() {
        var result: Int
        val listPermissionsNeeded = java.util.ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            val MULTIPLE_PERMISSIONS = 10
            ActivityCompat.requestPermissions(
                this as Activity,
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun logOutProcess() {
        showLoadingDialog()
        val preferences = getSharedPreferences(constant.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        val handler = Handler()
        handler.postDelayed({
            // Do something after 5s = 5000ms
            val i = Intent(this, Login::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finishAffinity()
            dismissLoadingDialog()
            startActivity(i)
            //finish()
        }, 2000)
    }

    private fun prepareDashBoardList() {
        var list = DashBoardModel("CLASS SCHEDLES ", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("PAYMENTS ", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("LEAVES", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("MESSAGING", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("ACCOUNTS", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("VEHICLE TRACKING", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("EVENTS", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("NOTIFICATIONS ", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("CHANGE PASSWORD ", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("LOGOUT", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("SHARE", R.drawable.app_icon.toString())
        dashboardlist.add(list)

        list = DashBoardModel("RATE US", R.drawable.app_icon.toString())
        dashboardlist.add(list)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                openCamera()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                val sharesubject = "Enjoy !! and GO GREEN"
                i.putExtra(Intent.EXTRA_TEXT, sharesubject)
                i.type = "text/plain"
                startActivity(Intent.createChooser(i, "Share to :"))
            }
            R.id.nav_send -> {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                val sharesubject = "Enjoy !! and GO GREEN"
                i.putExtra(Intent.EXTRA_TEXT, sharesubject)
                i.type = "text/plain"
                startActivity(Intent.createChooser(i, "Send to :"))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openCamera() {
        val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camIntent, 0)
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
}
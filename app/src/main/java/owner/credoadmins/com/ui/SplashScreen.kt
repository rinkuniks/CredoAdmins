package owner.credoadmins.com.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Window
import android.view.WindowManager
import owner.credoadmins.com.MainActivity
import owner.credoadmins.com.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        val splash = object : Thread()
        {
            override fun run() {
                try {
                    Thread.sleep(2000)
                    val intent = Intent(baseContext, SchoolList::class.java) // change to School List activity
                    startActivity(intent)

                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        splash.start()
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}
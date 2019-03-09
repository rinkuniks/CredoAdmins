package owner.credoadmins.com.common

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import android.util.Log

import java.util.Objects

class Constants {

    var USER_ID = "userId"
    var ADMIN_ID = "adminId"
    var EXISTS = "exists"
    var emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    var MobilePattern = "[6-9][0-9]{9}"
    var PREFERENCE_NAME = "credoadmin"
    val MOBILE_NO = "mobileNo"
    val EMAIL_ID = "emailId"
    val PUSH_NOTIFICATION_TOKEN = "pushNotificationToken"
    val PUSH_NOTIFICATION_CHANGED = "0"
    val NAME = "name"
    val CUSTOMER_CARE_MOBILE_NO = "7032676767"
    val CUSTOMER_CARE_TEXT = "Need help? Call to our customer support team"
    val MOBILE_NO_FORGOT_PASSWORD = "forgot_password_mobile"
    val Error_occurred_try_again = "Unexpected error occurred please try again"
    val enter_valid_mobile_no = "Enter valid mobile number"
    val enter_valid_email_id = "Enter valid email id"
    val passwords_not_matched = "New Password and Repeat password dose not match"
    val API_KEY = "AIzaSyDjTkCfXVsqBCCqdQkemSpmlmhcx4GAMmg"

    fun haveInternet(ctx: Context): Boolean {
        try {
            val info = (Objects.requireNonNull(ctx.getSystemService(Context.CONNECTIVITY_SERVICE))
                    as ConnectivityManager).activeNetworkInfo
            if (info == null || !info.isConnected) {
                return false
            }
        } catch (e: Exception) {
            Log.d("err" , e.toString())
        }
        return true
    }

    fun IntenetSettings(ctx: Context) {

        val alertDialogBuilder = AlertDialog.Builder(ctx)
        alertDialogBuilder
            .setMessage("No internet connection on your device. Would you like to enable it?")
            .setTitle("No Internet Connection")
            .setCancelable(false)
            .setPositiveButton(
                " Enable Internet ")
            { dialog , id ->
                val dialogIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ctx.startActivity(dialogIntent)
            }

        alertDialogBuilder.setNegativeButton(" Cancel ") { dialog , id -> dialog.cancel() }

        val alert = alertDialogBuilder.create()
        alert.show()
    }
}
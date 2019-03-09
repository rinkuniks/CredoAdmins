package owner.credoadmins.com.models.loginModel

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("result")
    lateinit var loginresult: LoginResult

    inner class LoginResult
    {
        var owner_id : String? = null
        var name : String? = null
        var email : String? = null
        var mobile : String? = null
        var otp_status : String? = null
    }
}
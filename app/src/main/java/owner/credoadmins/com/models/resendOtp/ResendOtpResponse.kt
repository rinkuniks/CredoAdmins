package owner.credoadmins.com.models.resendOtp

import com.google.gson.annotations.SerializedName

class ResendOtpResponse {
    @SerializedName("code")
    var responseCode : String? = null
    var description : String? = null
    var message : String? = null
    var mobile : String? = null
    var otp : String? = null
}
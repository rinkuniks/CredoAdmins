package owner.credoadmins.com.models.forgotOtpVerify

import com.google.gson.annotations.SerializedName

class OtpVerificationResponse {
    @SerializedName("code")
    var responseCode : String? = null
    var description : String? = null
    var message : String? = null
    var mobile : String? = null
}
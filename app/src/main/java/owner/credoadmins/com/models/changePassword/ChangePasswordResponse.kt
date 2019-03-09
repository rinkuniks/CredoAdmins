package owner.credoadmins.com.models.changePassword

import com.google.gson.annotations.SerializedName

class ChangePasswordResponse {
    @SerializedName("code")
    var responseCode : String? = null
    var description : String? = null
    var message : String? = null
}
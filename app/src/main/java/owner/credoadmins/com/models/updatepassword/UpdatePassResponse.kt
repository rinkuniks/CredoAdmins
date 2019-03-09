package owner.credoadmins.com.models.updatepassword

import com.google.gson.annotations.SerializedName

class UpdatePassResponse {
    @SerializedName("code")
    var responseCode : String? = null
    var description : String? = null
    var message : String? = null
}
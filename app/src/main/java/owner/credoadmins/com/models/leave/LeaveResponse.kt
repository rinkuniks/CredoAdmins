package owner.credoadmins.com.models.leave

import com.google.gson.annotations.SerializedName

class LeaveResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("res")
    var leaveresult : List<LeaveModel>? = null
}
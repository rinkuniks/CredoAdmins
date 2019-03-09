package owner.credoadmins.com.models.notifications

import com.google.gson.annotations.SerializedName

class NotificationResponse {

    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("result")
    var notification: List<NotificationModel>? = null
}
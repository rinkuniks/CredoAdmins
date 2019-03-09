package owner.credoadmins.com.models.payments

import com.google.gson.annotations.SerializedName
import owner.credoadmins.com.models.notifications.NotificationModel

class PaidListResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("res")
    var paidlistresult : List<PaidModel>? = null

}
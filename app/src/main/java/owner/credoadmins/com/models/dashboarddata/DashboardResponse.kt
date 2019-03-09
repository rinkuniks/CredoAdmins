package owner.credoadmins.com.models.dashboarddata

import com.google.gson.annotations.SerializedName

class DashboardResponse {
    @SerializedName("code")
    var responseCode : String? = null
    var description : String? = null
    var message : String? = null
}
package owner.credoadmins.com.models.schoollist

import com.google.gson.annotations.SerializedName

class SchoolListResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var message: String? = null
    var description : String? = null
    @SerializedName("result")
    var schoolListresult : List<SchoolListResult>? = null

}
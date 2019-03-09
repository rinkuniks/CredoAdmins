package owner.credoadmins.com.models.accountfilterincome

import com.google.gson.annotations.SerializedName

class AccIncFilterResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    var total: String? = null
    @SerializedName("result")
    var accfiltincresult : List<AccountFilterIncomeModel>? = null
}
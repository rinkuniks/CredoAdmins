package owner.credoadmins.com.models.accountfilterexpense

import com.google.gson.annotations.SerializedName

class AccExpFilterResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    var total: String? = null
    @SerializedName("result")
    var accfiltexpresult : List<AccountFilterExpenseModel>? = null
}
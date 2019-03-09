package owner.credoadmins.com.models.income

import com.google.gson.annotations.SerializedName

class Incomeresponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    var total: String? = null
    @SerializedName("result")
    var incomelistresult : List<IncomeModel>? = null
}
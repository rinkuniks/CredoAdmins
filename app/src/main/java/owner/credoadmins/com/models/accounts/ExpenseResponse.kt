package owner.credoadmins.com.models.accounts

import com.google.gson.annotations.SerializedName

class ExpenseResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    var total: String? = null
    @SerializedName("result")
    var expenselistresult : List<ExpenseModel>? = null
}
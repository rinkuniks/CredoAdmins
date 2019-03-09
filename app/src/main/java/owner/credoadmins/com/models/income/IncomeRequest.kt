package owner.credoadmins.com.models.income

class IncomeRequest {
    var admin_id: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
}
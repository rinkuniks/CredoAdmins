package owner.credoadmins.com.models.accounts

class ExpenseRequest {
    var admin_id: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
}
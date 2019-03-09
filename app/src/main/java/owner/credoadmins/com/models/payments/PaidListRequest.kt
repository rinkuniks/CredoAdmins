package owner.credoadmins.com.models.payments

class PaidListRequest {
    var admin_id: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
}
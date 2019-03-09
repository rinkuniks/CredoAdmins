package owner.credoadmins.com.models.accountfilterexpense

class AccExpFilterRequest {
    var admin_id: String? = null
    var from_date: String? = null
    var to_date: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
    fun setFromDate( date : String) {
        this.from_date = date
    }
    fun setToDate( date : String) {
        this.to_date = date
    }
}
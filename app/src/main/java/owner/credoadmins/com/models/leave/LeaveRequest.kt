package owner.credoadmins.com.models.leave

class LeaveRequest {
    var admin_id: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
}
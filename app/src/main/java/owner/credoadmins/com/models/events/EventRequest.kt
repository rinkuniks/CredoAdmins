package owner.credoadmins.com.models.events

class EventRequest {
    var admin_id : String? = null

    fun setUserdata(adminid: String) {
        this.admin_id = adminid
    }
}
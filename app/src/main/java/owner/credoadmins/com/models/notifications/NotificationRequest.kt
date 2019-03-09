package owner.credoadmins.com.models.notifications

class NotificationRequest {

    var admin_id: String? = null

    fun setUserdata( id : String) {
        this.admin_id = id
    }
}
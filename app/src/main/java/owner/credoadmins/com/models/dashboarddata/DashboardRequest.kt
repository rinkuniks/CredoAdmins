package owner.credoadmins.com.models.dashboarddata

class DashboardRequest {
    var admin_id: String? = null

    fun setUserdata(adminid: String) {
        this.admin_id = adminid
    }
}
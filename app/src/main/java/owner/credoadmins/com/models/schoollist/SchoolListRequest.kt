package owner.credoadmins.com.models.schoollist

class SchoolListRequest {

    var owner_id: String? = null

    fun setUserdata(id: String) {
        this.owner_id = id
    }
}
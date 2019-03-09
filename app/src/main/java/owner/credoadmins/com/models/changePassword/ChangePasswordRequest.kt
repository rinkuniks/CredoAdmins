package owner.credoadmins.com.models.changePassword

class ChangePasswordRequest {
    var user_id: String? = null
    var old_password: String? = null
    var new_password: String? = null
    var confirm_password: String? = null

    fun setUserId(uid: String) {
        this.user_id = uid
    }
    fun setUserOldPass(oldpass : String) {
        this.old_password = oldpass
    }
    fun setUserNewPass(newpass : String) {
        this.new_password = newpass
    }
    fun setUserConfirm(newpassconf : String) {
        this.confirm_password = newpassconf
    }
}
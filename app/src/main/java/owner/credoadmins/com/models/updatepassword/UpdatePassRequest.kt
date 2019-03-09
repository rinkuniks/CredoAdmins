package owner.credoadmins.com.models.updatepassword

class UpdatePassRequest {

    var mobile: String? = null
    var password: String? = null
    var confirm_password: String? = null

    fun setUserdata(mobile: String) {
        this.mobile = mobile
    }
    fun setPass(pass: String) {
        this.password = pass
    }
    fun setPassRepeat(passrepeat: String) {
        this.confirm_password = passrepeat
    }
}
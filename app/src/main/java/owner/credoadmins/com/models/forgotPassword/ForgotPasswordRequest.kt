package owner.credoadmins.com.models.forgotPassword

class ForgotPasswordRequest {

    var mobile: String? = null

    fun setUserdata(mobile: String) {
        this.mobile = mobile
    }
}
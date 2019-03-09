package owner.credoadmins.com.models.resendOtp

class ResendOtpRequest {
    var mobile: String? = null

    fun setUserdata(mobile: String) {
        this.mobile = mobile
    }
}
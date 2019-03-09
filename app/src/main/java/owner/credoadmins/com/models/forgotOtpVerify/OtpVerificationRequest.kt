package owner.credoadmins.com.models.forgotOtpVerify

class OtpVerificationRequest {
    var mobile: String? = null
    private var otp : String? = null

    fun setUserdata(mobile: String) {
        this.mobile = mobile
    }

    fun setOtp (otpres : String) {
        this.otp = otpres
    }
}
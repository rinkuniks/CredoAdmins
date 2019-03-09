package owner.credoadmins.com.interfaces

import owner.credoadmins.com.models.forgotOtpVerify.OtpVerificationRequest
import owner.credoadmins.com.models.forgotOtpVerify.OtpVerificationResponse
import owner.credoadmins.com.common.Urls
import owner.credoadmins.com.models.accountfilterexpense.AccExpFilterRequest
import owner.credoadmins.com.models.accountfilterexpense.AccExpFilterResponse
import owner.credoadmins.com.models.accountfilterincome.AccIncFilterRequest
import owner.credoadmins.com.models.accountfilterincome.AccIncFilterResponse
import owner.credoadmins.com.models.accounts.ExpenseRequest
import owner.credoadmins.com.models.accounts.ExpenseResponse
import owner.credoadmins.com.models.changePassword.ChangePasswordRequest
import owner.credoadmins.com.models.changePassword.ChangePasswordResponse
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsRequest
import owner.credoadmins.com.models.eventDetailsModels.EventDetailsResponse
import owner.credoadmins.com.models.events.EventRequest
import owner.credoadmins.com.models.events.EventResponse
import owner.credoadmins.com.models.forgotPassword.ForgotPasswordRequest
import owner.credoadmins.com.models.forgotPassword.ForgotPasswordResponse
import owner.credoadmins.com.models.income.IncomeRequest
import owner.credoadmins.com.models.income.Incomeresponse
import owner.credoadmins.com.models.leave.LeaveRequest
import owner.credoadmins.com.models.leave.LeaveResponse
import owner.credoadmins.com.models.loginModel.LoginRequest
import owner.credoadmins.com.models.loginModel.LoginResponse
import owner.credoadmins.com.models.notifications.NotificationRequest
import owner.credoadmins.com.models.notifications.NotificationResponse
import owner.credoadmins.com.models.payments.PaidListRequest
import owner.credoadmins.com.models.payments.PaidListResponse
import owner.credoadmins.com.models.resendOtp.ResendOtpRequest
import owner.credoadmins.com.models.resendOtp.ResendOtpResponse
import owner.credoadmins.com.models.schoollist.SchoolListRequest
import owner.credoadmins.com.models.schoollist.SchoolListResponse
import owner.credoadmins.com.models.updatepassword.UpdatePassRequest
import owner.credoadmins.com.models.updatepassword.UpdatePassResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CredoAdminApi {

    @POST(Urls.LOGIN)
    fun userLogin(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Urls.FORGOT_PASSWORD)
    fun userForgot(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    @POST(Urls.OTP_VERIFY)
    fun userOtpVerify(@Body request: OtpVerificationRequest): Call<OtpVerificationResponse>

    @POST(Urls.OTP_RESEND)
    fun userOtpResend(@Body request: ResendOtpRequest): Call<ResendOtpResponse>

    @POST(Urls.UPDATE_PASSWORD)
    fun userupdatepassword(@Body request: UpdatePassRequest): Call<UpdatePassResponse>

    @POST(Urls.SCHOOL_LIST)
    fun userSchoolList(@Body request: SchoolListRequest): Call<SchoolListResponse>

    @POST(Urls.CHANGE_PASSWORD)
    fun userChangePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>

    @POST(Urls.NOTIFICATIONS)
    fun userNotification(@Body request: NotificationRequest): Call<NotificationResponse>

    @POST(Urls.PAID_LIST)
    fun userPaidlist(@Body request: PaidListRequest): Call<PaidListResponse>

    @POST(Urls.LEAVE)
    fun userLeavelist(@Body request: LeaveRequest): Call<LeaveResponse>

    @POST(Urls.EXPENSE_LIST)
    fun userExpenselist(@Body request: ExpenseRequest): Call<ExpenseResponse>

    @POST(Urls.EXPENSE_FILTER_LIST)
    fun userExpenseFilterlist(@Body request: AccExpFilterRequest): Call<AccExpFilterResponse>

    @POST(Urls.INCOME_LIST)
    fun userIncomelist(@Body request: IncomeRequest): Call<Incomeresponse>

    @POST(Urls.INCOME_FILTER_LIST)
    fun userIncomeFilterlist(@Body request: AccIncFilterRequest): Call<AccIncFilterResponse>

    @POST(Urls.EVENTS)
    fun userEventlist(@Body request: EventRequest): Call<EventResponse>

    @POST(Urls.EVENTS_DETAILS)
    fun userEventDetaillist(@Body request: EventDetailsRequest): Call<EventDetailsResponse>

}
package owner.credoadmins.com.models.eventDetailsModels

import com.google.gson.annotations.SerializedName

class EventDetailsRequest {

    @SerializedName("event_id")
    var eventId: String? = null

    fun setUserdata(id: String) {
        this.eventId = id
    }

}

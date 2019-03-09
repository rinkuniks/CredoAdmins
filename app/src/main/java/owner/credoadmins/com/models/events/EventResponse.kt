package owner.credoadmins.com.models.events

import com.google.gson.annotations.SerializedName

class EventResponse {
    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("events")
    var eventresult : List<EventModel>? = null
}
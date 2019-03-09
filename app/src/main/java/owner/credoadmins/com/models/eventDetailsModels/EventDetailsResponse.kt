package owner.credoadmins.com.models.eventDetailsModels

import com.google.gson.annotations.SerializedName

class EventDetailsResponse {

    @SerializedName("code")
    var responseCode: String? = null
    var description: String? = null
    var message: String? = null
    @SerializedName("photos")
    var photos: List<EventDetailsPhotos>? = null
    @SerializedName("videos")
    var videos: List<EventDetailsVideos>? = null
    @SerializedName("eventDetails")
    var results: EventDetailsResults? = null


    inner class EventDetailsResults {


        @SerializedName("event_id")
        var eventId: String? = null
        var title: String? = null
        var description: String? = null
        @SerializedName("event_date")
        var eventDate: String? = null
        @SerializedName("photo")
        var photoUrl: String? = null


    }
}

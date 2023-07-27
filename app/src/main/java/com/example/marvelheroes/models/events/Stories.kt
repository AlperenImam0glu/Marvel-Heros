package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsStories (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<EventsItems> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?             = null

)
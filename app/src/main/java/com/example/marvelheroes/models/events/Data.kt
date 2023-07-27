package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsData (

  @SerializedName("offset"  ) var offset  : Int?               = null,
  @SerializedName("limit"   ) var limit   : Int?               = null,
  @SerializedName("total"   ) var total   : Int?               = null,
  @SerializedName("count"   ) var count   : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<EventsResults> = arrayListOf()

)
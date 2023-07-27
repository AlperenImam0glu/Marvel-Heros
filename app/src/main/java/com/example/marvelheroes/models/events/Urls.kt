package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsUrls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)
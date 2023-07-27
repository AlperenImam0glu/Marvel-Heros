package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsNext (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)
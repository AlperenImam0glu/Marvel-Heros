package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsPrevious (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)
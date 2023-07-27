package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsThumbnail (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)
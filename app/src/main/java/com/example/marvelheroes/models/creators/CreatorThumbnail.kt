package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class CreatorThumbnail (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)
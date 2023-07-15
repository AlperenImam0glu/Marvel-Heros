package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsThumbnail (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)
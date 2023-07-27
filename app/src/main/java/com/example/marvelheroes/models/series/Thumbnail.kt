package com.example.marvelheroes.series

import com.google.gson.annotations.SerializedName


data class SeriesThumbnail (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)
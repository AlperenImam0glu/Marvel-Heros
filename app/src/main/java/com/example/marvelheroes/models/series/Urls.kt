package com.example.marvelheroes.series

import com.google.gson.annotations.SerializedName


data class SeriesUrls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)
package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class CreatorUrls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)
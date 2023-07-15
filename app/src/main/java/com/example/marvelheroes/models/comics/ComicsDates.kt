package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsDates (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("date" ) var date : String? = null

)
package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsPrices (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("price" ) var price : Int?    = null

)
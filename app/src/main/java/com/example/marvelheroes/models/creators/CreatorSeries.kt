package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class CreatorSeries (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<CreatorItems> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?             = null

)
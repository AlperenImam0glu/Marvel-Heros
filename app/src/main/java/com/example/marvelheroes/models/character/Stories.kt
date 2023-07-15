package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class Stories (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?             = null

)
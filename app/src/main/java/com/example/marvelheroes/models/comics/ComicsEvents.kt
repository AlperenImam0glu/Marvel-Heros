package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsEvents (

  @SerializedName("available"     ) var available     : Int?              = null,
  @SerializedName("collectionURI" ) var collectionURI : String?           = null,
  @SerializedName("items"         ) var items         : ArrayList<String> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?              = null

)
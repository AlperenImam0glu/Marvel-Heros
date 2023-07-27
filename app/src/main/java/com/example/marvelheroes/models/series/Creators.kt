package com.example.marvelheroes.series

import com.google.gson.annotations.SerializedName


data class SeriesCreators (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<SeriesItems> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?             = null

)
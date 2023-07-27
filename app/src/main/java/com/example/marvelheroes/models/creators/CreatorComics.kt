package com.example.marvelheroes.models.creators

import com.example.marvelheroes.CreatorItems
import com.google.gson.annotations.SerializedName


data class CreatorComics (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<CreatorItems> = arrayListOf(),
  @SerializedName("returned"      ) var returned      : Int?             = null

)
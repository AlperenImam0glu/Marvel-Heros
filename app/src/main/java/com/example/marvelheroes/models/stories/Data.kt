package com.example.marvelheroes.stories

import com.google.gson.annotations.SerializedName


data class StoriesData (

  @SerializedName("offset"  ) var offset  : Int?               = null,
  @SerializedName("limit"   ) var limit   : Int?               = null,
  @SerializedName("total"   ) var total   : Int?               = null,
  @SerializedName("count"   ) var count   : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<StoriesResults> = arrayListOf()

)
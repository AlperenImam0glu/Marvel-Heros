package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsItems (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("type"        ) var type        : String? = null

)
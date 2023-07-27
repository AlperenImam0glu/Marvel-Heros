package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class CreatorItems (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("type"        ) var type        : String? = null

)
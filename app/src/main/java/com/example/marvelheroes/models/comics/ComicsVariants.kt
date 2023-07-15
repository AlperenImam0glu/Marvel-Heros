package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class ComicsVariants (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)
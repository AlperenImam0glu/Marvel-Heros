package com.example.marvelheroes.stories

import com.google.gson.annotations.SerializedName


data class StoriesOriginalIssue (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)
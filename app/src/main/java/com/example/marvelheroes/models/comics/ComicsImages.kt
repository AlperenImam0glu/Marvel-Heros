package com.example.example

import com.google.gson.annotations.SerializedName


data class ComicsImages (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)
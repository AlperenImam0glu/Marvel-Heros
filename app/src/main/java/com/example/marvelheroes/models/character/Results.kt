package com.example.marvelheroes

import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("id"          ) var id          : Int?            = null,
  @SerializedName("name"        ) var name        : String?         = null,
  @SerializedName("description" ) var description : String?         = null,
  @SerializedName("modified"    ) var modified    : String?         = null,
  @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
  @SerializedName("resourceURI" ) var resourceURI : String?         = null,
  @SerializedName("comics"      ) var comics      : Comics?         = Comics(),
  @SerializedName("series"      ) var series      : Series?         = Series(),
  @SerializedName("stories"     ) var stories     : Stories?        = Stories(),
  @SerializedName("events"      ) var events      : Events?         = Events(),
  @SerializedName("urls"        ) var urls        : ArrayList<Urls> = arrayListOf()

)
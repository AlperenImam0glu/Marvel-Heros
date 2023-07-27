package com.example.marvelheroes

import com.example.marvelheroes.models.creators.CreatorComics
import com.google.gson.annotations.SerializedName


data class CreatorResults (

  @SerializedName("id"          ) var id          : Int?            = null,
  @SerializedName("firstName"   ) var firstName   : String?         = null,
  @SerializedName("middleName"  ) var middleName  : String?         = null,
  @SerializedName("lastName"    ) var lastName    : String?         = null,
  @SerializedName("suffix"      ) var suffix      : String?         = null,
  @SerializedName("fullName"    ) var fullName    : String?         = null,
  @SerializedName("modified"    ) var modified    : String?         = null,
  @SerializedName("thumbnail"   ) var thumbnail   : CreatorThumbnail? = CreatorThumbnail(),
  @SerializedName("resourceURI" ) var resourceURI : String?         = null,
  @SerializedName("comics"      ) var comics      : CreatorComics?         = CreatorComics(),
  @SerializedName("series"      ) var series      : CreatorSeries?         = CreatorSeries(),
  @SerializedName("stories"     ) var stories     : CreatorStories?        = CreatorStories(),
  @SerializedName("events"      ) var events      : CreatorEvents?         = CreatorEvents(),
  @SerializedName("urls"        ) var urls        : ArrayList<CreatorUrls> = arrayListOf()

)
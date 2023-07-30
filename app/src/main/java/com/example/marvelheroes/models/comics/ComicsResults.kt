package com.example.marvelheroes

import com.example.example.ComicsImages
import com.google.gson.annotations.SerializedName


data class ComicsResults (

  @SerializedName("id"                 ) var id                 : Int?                = null,
  @SerializedName("digitalId"          ) var digitalId          : Int?                = null,
  @SerializedName("title"              ) var title              : String?             = null,
  @SerializedName("issueNumber"        ) var issueNumber        : Int?                = null,
  @SerializedName("variantDescription" ) var variantDescription : String?             = null,
  @SerializedName("description"        ) var description        : String?             = null,
  @SerializedName("modified"           ) var modified           : String?             = null,
  @SerializedName("isbn"               ) var isbn               : String?             = null,
  @SerializedName("upc"                ) var upc                : String?             = null,
  @SerializedName("diamondCode"        ) var diamondCode        : String?             = null,
  @SerializedName("ean"                ) var ean                : String?             = null,
  @SerializedName("issn"               ) var issn               : String?             = null,
  @SerializedName("format"             ) var format             : String?             = null,
  @SerializedName("pageCount"          ) var pageCount          : Int?                = null,
  @SerializedName("creators"           ) var creators           : ComicsCreators?           = ComicsCreators(),
  @SerializedName("characters"         ) var characters         : ComicsCharacters?         = ComicsCharacters(),
  @SerializedName("stories"            ) var stories            : ComicsStories?            = ComicsStories(),
  @SerializedName("events"             ) var events             : ComicsEvents?             = ComicsEvents(),
  @SerializedName("series"             ) var series             : ComicsSeries?             = ComicsSeries(),
  @SerializedName("thumbnail"          ) var thumbnail          : ComicsThumbnail?          = ComicsThumbnail(),

)

package com.example.marvelheroes

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
  @SerializedName("textObjects"        ) var textObjects        : ArrayList<String>   = arrayListOf(),
  @SerializedName("resourceURI"        ) var resourceURI        : String?             = null,
  @SerializedName("urls"               ) var urls               : ArrayList<ComicsUrls>     = arrayListOf(),
  @SerializedName("series"             ) var series             : ComicsSeries?             = ComicsSeries(),
  @SerializedName("variants"           ) var variants           : ArrayList<ComicsVariants> = arrayListOf(),
  @SerializedName("collections"        ) var collections        : ArrayList<String>   = arrayListOf(),
  @SerializedName("collectedIssues"    ) var collectedIssues    : ArrayList<String>   = arrayListOf(),
  @SerializedName("dates"              ) var dates              : ArrayList<ComicsDates>    = arrayListOf(),
  @SerializedName("prices"             ) var prices             : ArrayList<ComicsPrices>   = arrayListOf(),
  @SerializedName("thumbnail"          ) var thumbnail          : ComicsThumbnail?          = ComicsThumbnail(),
  @SerializedName("images"             ) var images             : ArrayList<String>   = arrayListOf(),
  @SerializedName("creators"           ) var creators           : ComicsCreators?           = ComicsCreators(),
  @SerializedName("characters"         ) var characters         : ComicsCharacters?         = ComicsCharacters(),
  @SerializedName("stories"            ) var stories            : ComicsStories?            = ComicsStories(),
  @SerializedName("events"             ) var events             : ComicsEvents?             = ComicsEvents()

)
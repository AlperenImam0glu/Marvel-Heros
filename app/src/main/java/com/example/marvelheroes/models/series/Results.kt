package com.example.marvelheroes.series

import com.google.gson.annotations.SerializedName


data class SeriesResults (

  @SerializedName("id"          ) var id          : Int?            = null,
  @SerializedName("title"       ) var title       : String?         = null,
  @SerializedName("description" ) var description : String?         = null,
  @SerializedName("resourceURI" ) var resourceURI : String?         = null,
  @SerializedName("urls"        ) var urls        : ArrayList<SeriesUrls> = arrayListOf(),
  @SerializedName("startYear"   ) var startYear   : Int?            = null,
  @SerializedName("endYear"     ) var endYear     : Int?            = null,
  @SerializedName("rating"      ) var rating      : String?         = null,
  @SerializedName("type"        ) var type        : String?         = null,
  @SerializedName("modified"    ) var modified    : String?         = null,
  @SerializedName("thumbnail"   ) var thumbnail   : SeriesThumbnail?      = SeriesThumbnail(),
  @SerializedName("creators"    ) var creators    : SeriesCreators?       = SeriesCreators(),
  @SerializedName("characters"  ) var characters  : SeriesCharacters?     = SeriesCharacters(),
  @SerializedName("stories"     ) var stories     : SeriesStories?        = SeriesStories(),
  @SerializedName("comics"      ) var comics      : SeriesComics?         = SeriesComics(),
  @SerializedName("events"      ) var events      : SeriesEvents?         = SeriesEvents(),
  @SerializedName("next"        ) var next        : SeriesNext?         = SeriesNext(),
  //@SerializedName("previous"    ) var previous  : String?         = null

)
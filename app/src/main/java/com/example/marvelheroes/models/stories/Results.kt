package com.example.marvelheroes.stories


import com.example.marvelheroes.series.StoriesThumbnail
import com.google.gson.annotations.SerializedName


data class StoriesResults (

  @SerializedName("id"            ) var id            : Int?           = null,
  @SerializedName("title"         ) var title         : String?        = null,
  @SerializedName("description"   ) var description   : String?        = null,
  @SerializedName("resourceURI"   ) var resourceURI   : String?        = null,
  @SerializedName("type"          ) var type          : String?        = null,
  @SerializedName("modified"      ) var modified      : String?        = null,
  @SerializedName("thumbnail"     ) var thumbnail     : StoriesThumbnail?     = StoriesThumbnail(),
  @SerializedName("creators"      ) var creators      : StoriesCreators?      = StoriesCreators(),
  @SerializedName("characters"    ) var characters    : StoriesCharacters?    = StoriesCharacters(),
  @SerializedName("series"        ) var series        : StoriesSeries?        = StoriesSeries(),
  @SerializedName("comics"        ) var comics        : StoriesComics?        = StoriesComics(),
  @SerializedName("events"        ) var events        : StoriesEvents?        = StoriesEvents(),
  @SerializedName("originalIssue" ) var originalIssue : StoriesOriginalIssue? = StoriesOriginalIssue()

)
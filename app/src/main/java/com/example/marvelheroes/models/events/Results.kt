package  com.example.marvelheroes.models.events

import com.google.gson.annotations.SerializedName


data class EventsResults (

  @SerializedName("id"          ) var id          : Int?            = null,
  @SerializedName("title"       ) var title       : String?         = null,
  @SerializedName("description" ) var description : String?         = null,
  @SerializedName("resourceURI" ) var resourceURI : String?         = null,
  @SerializedName("urls"        ) var urls        : ArrayList<EventsUrls> = arrayListOf(),
  @SerializedName("modified"    ) var modified    : String?         = null,
  @SerializedName("start"       ) var start       : String?         = null,
  @SerializedName("end"         ) var end         : String?         = null,
  @SerializedName("thumbnail"   ) var thumbnail   : EventsThumbnail?      = EventsThumbnail(),
  @SerializedName("creators"    ) var creators    : EventsCreators?       = EventsCreators(),
  @SerializedName("characters"  ) var characters  : EventsCharacters?     = EventsCharacters(),
  @SerializedName("stories"     ) var stories     : EventsStories?        = EventsStories(),
  @SerializedName("comics"      ) var comics      : EventsComics?         = EventsComics(),
  @SerializedName("series"      ) var series      : EventsSeries?         = EventsSeries(),
  @SerializedName("next"        ) var next        : EventsNext?           = EventsNext(),
  @SerializedName("previous"    ) var previous    : EventsPrevious?       = EventsPrevious()

)
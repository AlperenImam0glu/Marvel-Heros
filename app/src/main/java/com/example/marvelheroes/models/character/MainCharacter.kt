package com.example.marvelheroes
import com.example.marvelheroes.models.character.Data
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MainCharacter(

  @SerializedName("code"            ) var code            : Int?    = null,
  @SerializedName("status"          ) var status          : String? = null,
  @SerializedName("copyright"       ) var copyright       : String? = null,
  @SerializedName("attributionText" ) var attributionText : String? = null,
  @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
  @SerializedName("etag"            ) var etag            : String? = null,
  @SerializedName("data"            ) var data            : Data?   = Data()

) : Serializable
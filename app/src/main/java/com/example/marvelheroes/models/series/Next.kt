package com.example.marvelheroes.series

import com.google.gson.annotations.SerializedName


data class SeriesNext (

    @SerializedName("resourceURI" ) var resourceURI : String? = null,
    @SerializedName("name"        ) var name        : String? = null

)
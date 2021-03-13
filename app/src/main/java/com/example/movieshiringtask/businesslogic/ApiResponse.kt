package com.example.movieshiringtask.businesslogic

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiResponse(
    @field:Json(name = "Response") val response: Boolean,
    val items: List<Search>,
    val totalResults: Int
)


@JsonClass(generateAdapter = true)
data class Search(
    @field:Json(name = "Title") val title : String,
    @field:Json(name = "Year") val year : String,
    val imdbID : String,
    @field:Json(name = "Type") val type : String,
    @field:Json(name = "Poster") val poster : String
)
package com.example.movieshiringtask.businesslogic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiResponse(
    @field:Json(name = "Response") val response: String,
    @field:Json(name = "Search")val items: List<Search>,
    val totalResults: String?,
    @field:Json(name = "Error")val errorResponse: String?
)


@JsonClass(generateAdapter = true)
@Entity(tableName = "Search")
data class Search(
    @field:Json(name = "Title") val title : String,
    @field:Json(name = "Year") val year : String,
    @PrimaryKey
    val imdbID : String,
    @field:Json(name = "Type") val type : String,
    @field:Json(name = "Poster") val poster : String
)
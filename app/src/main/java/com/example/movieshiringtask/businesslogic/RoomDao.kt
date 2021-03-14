package com.example.movieshiringtask.businesslogic

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Observable

@Dao
interface RoomDao {

    @Query("DELETE from search WHERE type LIKE :movieType")
    fun deleteAllMovies(movieType: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movieDetails: List<Search>)

    @Query("SELECT * FROM search WHERE type  LIKE :movieType")
    fun getMovieList(movieType: String): LiveData<List<Search>>

    @Transaction
    fun replace(movieDetails: List<Search> , movieType: String){
        deleteAllMovies(movieType)
        insertAllMovies(movieDetails)
    }
}
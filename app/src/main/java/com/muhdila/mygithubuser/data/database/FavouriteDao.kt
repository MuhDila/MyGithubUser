package com.muhdila.mygithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Query("SELECT * FROM Favourite")
    fun getAllFavourite(): LiveData<List<Favourite>>

    @Query("SELECT * FROM Favourite WHERE username = :username")
    fun getFavouriteByUsername(username: String): LiveData<Favourite>
}
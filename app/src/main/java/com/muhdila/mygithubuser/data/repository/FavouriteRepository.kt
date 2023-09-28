package com.muhdila.mygithubuser.data.repository

import androidx.lifecycle.LiveData
import com.muhdila.mygithubuser.data.database.Favourite
import com.muhdila.mygithubuser.data.database.FavouriteDao

class FavouriteRepository(
    private val favouriteDao: FavouriteDao
) {
    fun getAllFavourite(): LiveData<List<Favourite>> = favouriteDao.getAllFavourite()

    fun getFavouriteByUsername(username: String): LiveData<Favourite> =
        favouriteDao.getFavouriteByUsername(username)

    suspend fun insert(favourite: Favourite) {
        favouriteDao.insert(favourite)
    }

    suspend fun delete(favourite: Favourite) {
        favouriteDao.delete(favourite)
    }

    companion object {
        @Volatile
        private var instance: FavouriteRepository? = null
        fun getInstance(
            favouriteDao: FavouriteDao
        ): FavouriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavouriteRepository(favouriteDao)
            }.also { instance = it }
    }
}
package com.muhdila.mygithubuser.data.helper

import android.content.Context
import com.muhdila.mygithubuser.data.database.FavouriteDatabase
import com.muhdila.mygithubuser.data.repository.FavouriteRepository

object Injection {
    fun provideRepository(context: Context): FavouriteRepository {
        val database = FavouriteDatabase.getInstance(context)
        val dao = database.favouriteDao()
        return FavouriteRepository.getInstance(dao)
    }
}
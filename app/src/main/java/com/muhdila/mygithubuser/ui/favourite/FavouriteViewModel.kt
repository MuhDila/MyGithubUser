package com.muhdila.mygithubuser.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhdila.mygithubuser.data.database.Favourite
import com.muhdila.mygithubuser.data.repository.FavouriteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository) : ViewModel() {

    fun getAllFavourite(): LiveData<List<Favourite>> = favouriteRepository.getAllFavourite()

    fun getFavouriteByUsername(username: String): LiveData<Favourite> =
        favouriteRepository.getFavouriteByUsername(username)

    fun insert(favourite: Favourite) = viewModelScope.launch {
        favouriteRepository.insert(favourite)
    }

    fun delete(favourite: Favourite) = viewModelScope.launch {
        favouriteRepository.delete(favourite)
    }
}
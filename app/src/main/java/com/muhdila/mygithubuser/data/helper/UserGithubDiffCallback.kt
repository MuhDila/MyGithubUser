package com.muhdila.mygithubuser.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems

class UserGithubDiffCallback(
    private val oldList: List<UserGithubItems>,
    private val newList: List<UserGithubItems>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
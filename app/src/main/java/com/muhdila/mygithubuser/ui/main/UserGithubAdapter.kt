package com.muhdila.mygithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhdila.mygithubuser.data.helper.UserGithubDiffCallback
import com.muhdila.mygithubuser.data.helper.loadImage
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ItemRowUserBinding

class UserGithubAdapter : RecyclerView.Adapter<UserGithubAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val items = mutableListOf<UserGithubItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val githubUser = items[position]
        holder.bind(githubUser)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun submitList(newList: List<UserGithubItems>) {
        val diffCallback = UserGithubDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubUser: UserGithubItems) {
            with(binding) {
                tvItemName.text = githubUser.login
                tvItemUrl.text = githubUser.homeUrl
                imgItemPhoto.loadImage(githubUser.avatarUrl)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(githubUser)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserGithubItems)
    }
}
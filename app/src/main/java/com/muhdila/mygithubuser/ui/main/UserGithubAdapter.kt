package com.muhdila.mygithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhdila.mygithubuser.data.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ItemRowUserBinding

class UserGithubAdapter(private val listGithubUser: List<UserGithubItems>) :
    RecyclerView.Adapter<UserGithubAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val githubUser = listGithubUser[position]
        with(holder.binding) {
            tvItemName.text = githubUser.login
            tvItemUrl.text = githubUser.homeUrl
            Glide.with(root.context).load(githubUser.avatarUrl).into(imgItemPhoto)
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(githubUser)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: UserGithubItems)
    }
}
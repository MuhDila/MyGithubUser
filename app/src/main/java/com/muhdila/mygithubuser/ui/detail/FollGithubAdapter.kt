package com.muhdila.mygithubuser.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhdila.mygithubuser.data.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ItemRowUserBinding

class FollGithubAdapter(private val listGithubFoll: List<UserGithubItems>) :
    RecyclerView.Adapter<FollGithubAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listGithubFoll.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listGithubFoll[position]
        holder.bind(user)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserGithubItems) {
            with(binding) {
                tvItemName.text = user.login
                Glide.with(root.context).load(user.avatarUrl).into(imgItemPhoto)
            }
        }
    }
}
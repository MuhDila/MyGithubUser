package com.muhdila.mygithubuser.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhdila.mygithubuser.data.helper.loadImage
import com.muhdila.mygithubuser.data.remote.response.UserGithubItems
import com.muhdila.mygithubuser.databinding.ItemRowUserBinding
import com.muhdila.mygithubuser.ui.main.UserGithubAdapter

class FollGithubAdapter(private val listGithubFoll: List<UserGithubItems>) :
    RecyclerView.Adapter<FollGithubAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: UserGithubAdapter.OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listGithubFoll.size

    fun setOnItemClickCallback(onItemClickCallback: UserGithubAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listGithubFoll[position]
        holder.bind(user)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserGithubItems) {
            with(binding) {
                tvItemName.text = user.login
                tvItemUrl.text = user.homeUrl
                imgItemPhoto.loadImage(user.avatarUrl)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }
}
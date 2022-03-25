package com.saadfauzi.githubuser.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.data.local.entity.GithubUserEntity
import com.saadfauzi.githubuser.databinding.ItemGithubUserBinding

class RecyclerUserAdapter(private val onFavorite: (GithubUserEntity) -> Unit): ListAdapter<GithubUserEntity, RecyclerUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)

        val imgFavorite = holder.binding.imgFavorite

        val message = "${users.username} is ${users.isFavorite}"
        Log.d("AdapterUsers", message)

        if (users.isFavorite){
            imgFavorite.setImageDrawable(ContextCompat.getDrawable(holder.binding.imgFavorite.context, R.drawable.ic_favorite))
        } else {
            imgFavorite.setImageDrawable(ContextCompat.getDrawable(holder.binding.imgFavorite.context, R.drawable.ic_unfavorite))
        }

        imgFavorite.setOnClickListener{
            onFavorite(users)
            Log.d("FavoriteAdapter", users.username)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(users)
        }
    }

    class ViewHolder(val binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: GithubUserEntity){
            binding.apply {
                tvName.text = users.username
                Glide.with(itemView.context)
                    .load(users.avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(circleAvatar)

                btnOpen.setOnClickListener {
                    val intentDetail = Intent(Intent.ACTION_VIEW, Uri.parse(users.htmlUrl))
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    interface IOnItemClickCallback {
        fun onItemClicked(data: GithubUserEntity)
    }

    companion object {
        val DIFF_CALLBACK:DiffUtil.ItemCallback<GithubUserEntity> =
            object: DiffUtil.ItemCallback<GithubUserEntity>() {
                override fun areItemsTheSame(
                    oldItem: GithubUserEntity,
                    newItem: GithubUserEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: GithubUserEntity,
                    newItem: GithubUserEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}
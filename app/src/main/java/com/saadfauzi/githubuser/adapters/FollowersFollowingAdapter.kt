package com.saadfauzi.githubuser.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.data.remote.models.GithubUserItem
import com.saadfauzi.githubuser.databinding.ItemFollowersFollowingBinding

class FollowersFollowingAdapter(private val listFollowers: ArrayList<GithubUserItem>): RecyclerView.Adapter<FollowersFollowingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFollowersFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.binding.tvName.text = listFollowers[position].username
        Glide.with(context)
            .load(listFollowers[position].avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(holder.binding.circleAvatar)

        holder.binding.btnOpen.setOnClickListener{
            val intentDetail = Intent(Intent.ACTION_VIEW, Uri.parse(listFollowers[position].htmlUrl))
            context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return listFollowers.size
    }

    //Kotlin Extensions
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(500, 500))
            .centerCrop()
            .into(this)
    }

    class ViewHolder(var binding: ItemFollowersFollowingBinding) : RecyclerView.ViewHolder(binding.root)
}
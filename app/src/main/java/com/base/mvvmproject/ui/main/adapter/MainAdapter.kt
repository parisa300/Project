package com.base.mvvmproject.ui.main.adapter

import android.annotation.SuppressLint
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvmproject.data.model.User
import com.base.mvvmproject.databinding.ItemUserBinding
import com.base.mvvmproject.ui.fragment.ListUserFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import java.util.Collections.addAll

class MainAdapter(private val listener: UserItemListener) :  RecyclerView.Adapter<UserViewHolder>() {
    interface UserItemListener {
        fun onClickedCharacter(userId: String)
    }

    private  val items = ArrayList<User>()

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position])
}

class UserViewHolder(private val itemBinding: ItemUserBinding, private val listener: MainAdapter.UserItemListener) : RecyclerView.ViewHolder(itemBinding.root),


    View.OnClickListener {

    private lateinit var user: User

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: User) {
        this.user = item
        itemBinding.textViewUserName.text = item.userName
        itemBinding.textViewUserEmail.text =item.userEmail
        Glide.with(itemBinding.root)
            .load(item.image)
            .transform(CircleCrop())
            .into(itemBinding.imageViewAvatar)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(user.userId)
    }



}
package com.example.runningbuddy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.R
import com.example.runningbuddy.models.User
import com.example.runningbuddy.ui.friends.FriendsViewModel


class FriendRecyclerViewAdapter(private val listeUsers: MutableList<User>, private val friendsViewModel: FriendsViewModel) :
    RecyclerView.Adapter<FriendRecyclerViewAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false) as View
        return RecyclerViewViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val user = this.listeUsers[position]

        holder.view.findViewById<TextView>(R.id.tvNameCardFriend).text = user.nom

        // Si le user est subscribed on change pour un - a la place d'un +
        if (user.subscribed) {
            holder.view.findViewById<ImageButton>(R.id.btnSubscribeCard)
                .setImageResource(R.drawable.ic_baseline_remove_24)
        }

        // Click du bouton pour like un course
        holder.view.findViewById<ImageButton>(R.id.btnSubscribeCard).setOnClickListener{
            if (friendsViewModel.friends.value?.get(position)!!.subscribed) {
                holder.view.findViewById<ImageButton>(R.id.btnSubscribeCard)
                    .setImageResource(R.drawable.ic_baseline_add_24)
            } else {
                holder.view.findViewById<ImageButton>(R.id.btnSubscribeCard)
                    .setImageResource(R.drawable.ic_baseline_remove_24)
            }

            friendsViewModel.updateSubscribe(position)
        }
    }

    override fun getItemCount() = this.listeUsers.size
}
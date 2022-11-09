package com.example.runningbuddy.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.R
import com.example.runningbuddy.models.Course
import com.example.runningbuddy.ui.home.HomeViewModel
import com.squareup.picasso.Picasso


class HomeRecyclerViewAdapter(private val listeCourses: MutableList<Course>, private val homeViewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_course_item, parent, false) as View
        return RecyclerViewViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val course = this.listeCourses[position]


        // Informations de la course
        holder.view.findViewById<TextView>(R.id.tvNameCard).text = course.nom
        holder.view.findViewById<TextView>(R.id.tvDistanceCard).text = "${course.distance}km"
        holder.view.findViewById<TextView>(R.id.tvDureeCard).text = "Durée: ${course.duree}"
        holder.view.findViewById<TextView>(R.id.tvDateCard).text = course.date.substring(0, 10)

        // Ajustement de couleur du bouton
        if (course.liked) {
            holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                ContextCompat.getColor(holder.view.context, R.color.liked),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        }

        // Click du bouton pour like un course
        holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setOnClickListener{
            homeViewModel.updateLike(position)
            if (homeViewModel.courses.value?.get(position)!!.liked) {
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.liked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
            } else {
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.notLiked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    override fun getItemCount() = this.listeCourses.size
}
package com.example.runningbuddy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.R
import com.example.runningbuddy.converters.Converters
import com.example.runningbuddy.models.RunGet
import com.example.runningbuddy.ui.home.HomeViewModel
import java.math.BigInteger

class HomeRecyclerViewAdapter(private val listeCourses: MutableList<RunGet>, private val homeViewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    private var converters: Converters = Converters()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_course_item, parent, false) as View
        return RecyclerViewViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val course = this.listeCourses[position]

        // Informations de la course
        holder.view.findViewById<TextView>(R.id.tvNameCard).text = course.nom
        holder.view.findViewById<TextView>(R.id.tvDistanceCard).text = "${course.distanceInMeters}m"
        holder.view.findViewById<TextView>(R.id.tvDureeCard).text = "Durée: ${course.timeInMillis}"
        holder.view.findViewById<TextView>(R.id.tvDateCard).text = "${course.timeStamps}"
        holder.view.findViewById<ImageView>(R.id.imageMapCard)
            .setImageBitmap(course.img?.let { converters.toBitmap(it) })

        // Ajustement de couleur du bouton
        if (course.liked) {
            holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                ContextCompat.getColor(holder.view.context, R.color.liked),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        }

        // Click du bouton pour like un course
        holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setOnClickListener{
            if (homeViewModel.courses.value?.get(position)!!.liked) {
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.notLiked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
            } else {
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.liked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
            }
            
            homeViewModel.updateLike(position)

            println("hello ${homeViewModel.courses.value?.get(position)!!.liked}")
        }
    }

    override fun getItemCount() = this.listeCourses.size
}
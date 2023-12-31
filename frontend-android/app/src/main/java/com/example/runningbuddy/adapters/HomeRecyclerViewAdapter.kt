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
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.R
import com.example.runningbuddy.models.RunGet
import com.example.runningbuddy.ui.home.HomeViewModel
import com.squareup.picasso.Picasso

class HomeRecyclerViewAdapter(private val listeCourses: MutableList<RunGet>, private val homeViewModel: HomeViewModel) :
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
        val time = course.timeInMillis / 60000
        holder.view.findViewById<TextView>(R.id.tvDureeCard).text = "Durée: $time min"
        holder.view.findViewById<TextView>(R.id.tvDateCard).text = course.timeStamps
        holder.view.findViewById<TextView>(R.id.tvLikesCard).text = if (course.liked) "${course.likes + 1}" else "${course.likes}"

        // Ajuste la distance a lunite de mesure choisie
        if(MainActivity.uniteMesure == "km"){
            holder.view.findViewById<TextView>(R.id.tvDistanceCard).text = "Distance: ${String.format("%.2f", (course.distanceInMeters/1000))} ${MainActivity.uniteMesure}"
        }
        else{
            holder.view.findViewById<TextView>(R.id.tvDistanceCard).text = "Distance: ${String.format("%.2f", (course.distanceInMeters/1609))} ${MainActivity.uniteMesure}"
        }

        // Affichage de l'image de la course
        val imageView = holder.view.findViewById<ImageView>(R.id.imageMapCard)
        Picasso.get().load("${MainActivity.SRVURL}/course/image/${course.id}").into(imageView)

        // Ajustement de couleur du bouton
        if (course.liked) {
            holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                ContextCompat.getColor(holder.view.context, R.color.liked),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        }

        // Click du bouton pour like un course   0 |
        holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setOnClickListener{
            if (homeViewModel.courses.value?.get(position)!!.liked) {
                // Met la couleur du like a gris
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.notLiked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)

                // Retire 1 du nombre de like affiche
                holder.view.findViewById<TextView>(R.id.tvLikesCard).text = "${course.likes}"
            } else {
                // Met la couleur du like a gris
                holder.view.findViewById<ImageButton>(R.id.btnLikeCard).setColorFilter(
                    ContextCompat.getColor(holder.view.context, R.color.liked),
                    android.graphics.PorterDuff.Mode.MULTIPLY)

                // Ajoute 1 au nombre de like affiche
                holder.view.findViewById<TextView>(R.id.tvLikesCard).text = "${course.likes + 1}"
            }
            
            homeViewModel.updateLike(position)

            println("hello ${homeViewModel.courses.value?.get(position)!!.liked}")
        }
    }

    override fun getItemCount() = this.listeCourses.size
}
package com.example.runningbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.R
import com.example.runningbuddy.models.Course
import com.squareup.picasso.Picasso


class HomeRecyclerViewAdapter(private val listeCourses: MutableList<Course>) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_course_item, parent, false) as View
        return RecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.tvNameCard).text = this.listeCourses[position].nom
        holder.view.findViewById<TextView>(R.id.tvDistanceCard).text = this.listeCourses[position].distance.toString()
        holder.view.findViewById<TextView>(R.id.tvDureeCard).text = this.listeCourses[position].duree
        holder.view.findViewById<TextView>(R.id.tvDateCard).text = this.listeCourses[position].date
    }

    override fun getItemCount() = this.listeCourses.size
}
package com.example.runningbuddy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.R
import com.example.runningbuddy.adapters.HomeRecyclerViewAdapter

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rvCourses: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Viewmodel
        this.homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // RecyclerView des courses
        this.rvCourses = requireView().findViewById(R.id.rvCoursesHome)
        this.rvCourses.layoutManager = LinearLayoutManager(context)

        this.homeViewModel.courses.observe(viewLifecycleOwner) {
            this.rvCourses.adapter = HomeRecyclerViewAdapter(it, this.homeViewModel)
        }
    }
}
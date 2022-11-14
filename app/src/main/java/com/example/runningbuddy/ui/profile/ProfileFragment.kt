package com.example.runningbuddy.ui.profile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runningbuddy.R
import com.example.runningbuddy.models.StatsCourse
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ProfileFragment : Fragment() {

    lateinit var barChartView: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    private lateinit var profilViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        requireActivity().actionBar?.title = "Profil"

        profilViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        profilViewModel.getCoursesUser()


        profilViewModel.statscourse.observe(viewLifecycleOwner){

            barChartView  = view.findViewById(R.id.idBarChart)

            val barWidth: Float
            val barSpace: Float
            val groupSpace: Float
            val groupCount = 12

            barWidth = 0.35f
            barSpace = 0.07f
            groupSpace = 0.17f

            val xAxisValues = ArrayList<String>()
            xAxisValues.add("Jan")
            xAxisValues.add("Feb")
            xAxisValues.add("Mar")
            xAxisValues.add("Apr")
            xAxisValues.add("May")
            xAxisValues.add("June")
            xAxisValues.add("Jul")
            xAxisValues.add("Aug")
            xAxisValues.add("Sep")
            xAxisValues.add("Oct")
            xAxisValues.add("Nov")
            xAxisValues.add("Dec")

            val yValueGroup1 = ArrayList<BarEntry>()
            val yValueGroup2 = ArrayList<BarEntry>()

            // draw the graph
            val barDataSet1: BarDataSet
            val barDataSet2: BarDataSet

            // Enlever les légendes
            val legend = barChartView.legend
            val legenedEntries = arrayListOf<LegendEntry>()
            legenedEntries.add(LegendEntry("", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.WHITE))
            legenedEntries.add(LegendEntry("", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.WHITE))
            legend.setCustom(legenedEntries)


            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentMonth = sdf.format(Date()).substring(3,5)
            println(" C Month is  "+currentMonth)

            for(i in 1..12){
                var found = false
                for(month in it){
                    val monthNumber = month.mois.split("-").toTypedArray()[1].toFloat()
                    if(i.toFloat() == monthNumber){
                        println(monthNumber)
                        println(month.totdist)
                        yValueGroup1.add(BarEntry(monthNumber, month.totdist))
                        yValueGroup2.add(BarEntry(monthNumber, floatArrayOf(0.toFloat())))
                        found = true

                        if(monthNumber == currentMonth.toFloat()){
                            view.findViewById<TextView>(R.id.TvDistance).text = month.totdist.toString()
                            view.findViewById<TextView>(R.id.TvTemps).text = month.tottime.toString()
                            view.findViewById<TextView>(R.id.tvCalorie).text = month.totcalorie.toString()
                            view.findViewById<TextView>(R.id.tvVitesse).text = month.avgspeed.toString()
                        }

                        else{
                            view.findViewById<TextView>(R.id.TvDistance).text = "0"
                            view.findViewById<TextView>(R.id.tvCalorie).text = "0"
                            view.findViewById<TextView>(R.id.tvVitesse).text = "0"
                            view.findViewById<TextView>(R.id.TvTemps).text = "0"
                        }
                    }

                }
                if(!found){
                    yValueGroup1.add(BarEntry(i.toFloat(), 0f))
                    yValueGroup2.add(BarEntry(i.toFloat(), floatArrayOf(0.toFloat())))
                }
            }

            barDataSet1 = BarDataSet(yValueGroup1, "")
            barDataSet1.setDrawIcons(false)
            barDataSet1.setDrawValues(false)
            barDataSet1.setColors(Color.parseColor("#1F7DC8"))

            barDataSet2 = BarDataSet(yValueGroup2, "")

            barDataSet2.setDrawIcons(false)
            barDataSet2.setDrawValues(false)

            var barData = BarData(barDataSet1, barDataSet2)

            barChartView.description.isEnabled = false
            barChartView.description.textSize = 0f
            barData.setValueFormatter(LargeValueFormatter())
            barChartView.setData(barData)
            barChartView.getBarData().setBarWidth(barWidth)
            barChartView.getXAxis().setAxisMinimum(0f)
            barChartView.getXAxis().setAxisMaximum(12f)
            barChartView.groupBars(0f, groupSpace, barSpace)
            //   barChartView.setFitBars(true)
            barChartView.getData().setHighlightEnabled(false)
            barChartView.invalidate()

            val xAxis = barChartView.getXAxis()
            xAxis.setGranularity(1f)
            xAxis.setGranularityEnabled(true)
            xAxis.setCenterAxisLabels(true)
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 13f

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
            xAxis.setValueFormatter(IndexAxisValueFormatter(xAxisValues))

            xAxis.setLabelCount(12)
            xAxis.mAxisMaximum = 12f
            xAxis.setCenterAxisLabels(true)
            xAxis.setAvoidFirstLastClipping(true)
            xAxis.spaceMin = 4f
            xAxis.spaceMax = 4f

            barChartView.setVisibleXRangeMaximum(12f)
            barChartView.setVisibleXRangeMinimum(12f)
            barChartView.setDragEnabled(true)

            //Y-axis
            barChartView.getAxisRight().setEnabled(false)
            barChartView.setScaleEnabled(true)

            val leftAxis = barChartView.getAxisLeft()
            leftAxis.setValueFormatter(LargeValueFormatter())
            leftAxis.setDrawGridLines(false)
            leftAxis.setSpaceTop(1f)
            leftAxis.setAxisMinimum(0f)
            leftAxis.textSize = 13f


            barChartView.data = barData
            barChartView.setVisibleXRange(1f, 12f)
        }





//        yValueGroup1.add(BarEntry(1f, floatArrayOf()))
//        yValueGroup2.add(BarEntry(1f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(2f, floatArrayOf(3.toFloat())))
//        yValueGroup2.add(BarEntry(2f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(3f, floatArrayOf(3.toFloat())))
//        yValueGroup2.add(BarEntry(3f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(4f, floatArrayOf(3.toFloat())))
//        yValueGroup2.add(BarEntry(4f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(5f, floatArrayOf(9.toFloat())))
//        yValueGroup2.add(BarEntry(5f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(6f, floatArrayOf(24.toFloat())))
//        yValueGroup2.add(BarEntry(6f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(7f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(7f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(8f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(8f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(9f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(9f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(10f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(10f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(11f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(11f, floatArrayOf(0.toFloat())))
//
//        yValueGroup1.add(BarEntry(12f, floatArrayOf(11.toFloat())))
//        yValueGroup2.add(BarEntry(12f, floatArrayOf(0.toFloat())))







    }

}
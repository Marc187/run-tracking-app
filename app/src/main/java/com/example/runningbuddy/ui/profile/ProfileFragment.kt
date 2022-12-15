package com.example.runningbuddy.ui.profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    lateinit var barChartView: BarChart
    private lateinit var profilViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("Range", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.title = "Profil"

        profilViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        profilViewModel.getCoursesUser()


        profilViewModel.statscourse.observe(viewLifecycleOwner){

            barChartView  = view.findViewById(R.id.idBarChart)
            val coureur = view.findViewById<ImageView>(R.id.imageCourse)

            //Set couleur graphique blanc si dark mode est activé
            when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    barChartView.axisLeft.textColor = resources.getColor(R.color.white);
                    barChartView.axisLeft.axisLineColor = resources.getColor(R.color.white);
                    barChartView.xAxis.textColor = resources.getColor(R.color.white);
                    barChartView.xAxis.axisLineColor = resources.getColor(R.color.white);
                    coureur.setColorFilter(
                        ContextCompat.getColor(requireContext(), android.R.color.white),
                        PorterDuff.Mode.MULTIPLY);
                }
            }

            //Set les unités de mesure
            view.findViewById<TextView>(R.id.uniteDistance).text = MainActivity.uniteMesure
            view.findViewById<TextView>(R.id.uniteVitesse).text = MainActivity.uniteMesure


            val barWidth: Float = 0.35f
            val barSpace: Float = 0.07f
            val groupSpace: Float = 0.17f

            val barWidth: Float
            val barSpace: Float
            val groupSpace: Float
            val groupCount = 12

            barWidth = 0.35f
            barSpace = 0.07f
            groupSpace = 0.17f

            //Set les mois pour le graphique
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

            // Dessine le graphique
            val barDataSet1: BarDataSet
            val barDataSet2: BarDataSet

            // Enlever les légendes
            val legend = barChartView.legend
            val legenedEntries = arrayListOf<LegendEntry>()
            legenedEntries.add(LegendEntry("", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.WHITE))
            legenedEntries.add(LegendEntry("", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.WHITE))
            legend.setCustom(legenedEntries)

            //Format la date
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentMonth = sdf.format(Date()).substring(3,5).toFloat()

            //Boucle a travers les mois et set les données
            for(i in 1..12){
                var found = false
                for(month in it){
                    val monthNumber = month.mois.split("-").toTypedArray()[1].toFloat()
                    if(i.toFloat() == monthNumber){
                        //Changer les données en fonction de l'unité de mesure
                        if(MainActivity.uniteMesure == "km") {
                            yValueGroup1.add(BarEntry(monthNumber, month.totdist/1000))
                            yValueGroup2.add(BarEntry(monthNumber, floatArrayOf(0.toFloat())))
                        }
                        else{
                            yValueGroup1.add(BarEntry(monthNumber, month.totdist/1609))
                            yValueGroup2.add(BarEntry(monthNumber, floatArrayOf(0.toFloat())))
                        }

                        found = true

                        if(monthNumber == currentMonth){
                            //Changer les données en fonction de l'unité de mesure
                            if(MainActivity.uniteMesure == "km") {
                                val distanceTotKm:Double = String.format("%.2f", month.totdist/1000).toDouble()
                                val avgSpeedKm:Double = String.format("%.2f", month.avgspeed).toDouble()
                                view.findViewById<TextView>(R.id.TvDistance).text = (distanceTotKm).toString()
                                view.findViewById<TextView>(R.id.tvVitesse).text = avgSpeedKm.toString()
                            }
                            else{
                                val distanceTotMiles:Double = String.format("%.2f", month.totdist/1609).toDouble()
                                val avgSpeedMiles:Double = String.format("%.2f", month.avgspeed/1.609).toDouble()
                                view.findViewById<TextView>(R.id.TvDistance).text = (distanceTotMiles).toString()
                                view.findViewById<TextView>(R.id.tvVitesse).text = (avgSpeedMiles).toString()
                            }

                            view.findViewById<TextView>(R.id.TvTemps).text = month.tottemps.toString()
                            view.findViewById<TextView>(R.id.tvCalorie).text = month.totcalorie.toString()

                        }
                    }

                }
                //Si aucune donnée pour un mois
                if(!found){
                    yValueGroup1.add(BarEntry(i.toFloat(), 0f))
                    yValueGroup2.add(BarEntry(i.toFloat(), floatArrayOf(0.toFloat())))
                }
            }

            //Settings du graphique
            barDataSet1 = BarDataSet(yValueGroup1, "")
            barDataSet1.setDrawIcons(false)
            barDataSet1.setDrawValues(false)
            barDataSet1.setColors(Color.parseColor("#1F7DC8"))

            val barDataSet2: BarDataSet = BarDataSet(yValueGroup2, "")

            barDataSet2.setDrawIcons(false)
            barDataSet2.setDrawValues(false)

            val barData = BarData(barDataSet1, barDataSet2)

            barChartView.description.isEnabled = false
            barChartView.description.textSize = 0f
            barData.setValueFormatter(LargeValueFormatter())
            barChartView.data = barData
            barChartView.barData.barWidth = barWidth
            barChartView.xAxis.axisMinimum = 0f
            barChartView.xAxis.axisMaximum = 12f
            barChartView.groupBars(0f, groupSpace, barSpace)
            barChartView.getData().setHighlightEnabled(false)
            barChartView.invalidate()

            val xAxis = barChartView.xAxis
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            xAxis.setCenterAxisLabels(true)
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 13f

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

            xAxis.labelCount = 12
            xAxis.mAxisMaximum = 12f
            xAxis.setCenterAxisLabels(true)
            xAxis.setAvoidFirstLastClipping(true)
            xAxis.spaceMin = 4f
            xAxis.spaceMax = 4f

            barChartView.setVisibleXRangeMaximum(12f)
            barChartView.setVisibleXRangeMinimum(12f)
            barChartView.isDragEnabled = true

            barChartView.getAxisRight().setEnabled(false)
            barChartView.setScaleEnabled(true)

            val leftAxis = barChartView.axisLeft
            leftAxis.valueFormatter = LargeValueFormatter()
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 1f
            leftAxis.axisMinimum = 0f
            leftAxis.textSize = 13f


            barChartView.data = barData
            barChartView.setVisibleXRange(1f, 12f)
        }

    }

}
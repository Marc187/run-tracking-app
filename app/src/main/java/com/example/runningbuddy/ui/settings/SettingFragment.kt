package com.example.runningbuddy.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.R
import com.github.mikephil.charting.charts.BarChart

//Utiliser sharepref pour sauvegarder les settings

class SettingFragment : Fragment() {

    lateinit var barChartView: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        requireActivity().actionBar?.title = "Réglage"

        //Switch bouton for dark mode
        val switchBtn = view.findViewById<Switch>(R.id.switchTheme)

        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                switchBtn.setChecked(true);
            }
        }

        switchBtn.setOnCheckedChangeListener { _, isChecked ->

            if (switchBtn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        //Spinner for unite de mesure
        val spinner: Spinner = view.findViewById(R.id.uniteMesureSpinner)
        spinner.setSelection(
            (spinner.getAdapter() as ArrayAdapter<String?>).getPosition(
                MainActivity.uniteMesure
            )
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                MainActivity.uniteMesure = parent?.selectedItem.toString()
                println(MainActivity.uniteMesure)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

    }

}
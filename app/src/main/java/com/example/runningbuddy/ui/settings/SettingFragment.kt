package com.example.runningbuddy.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.R
import com.github.mikephil.charting.charts.BarChart

//Utiliser sharepref pour sauvegarder les settings

class SettingFragment : Fragment() {

    lateinit var barChartView: BarChart
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings, container, false)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        requireActivity().actionBar?.title = "Réglage"

        //Réinitialistion password
        view.findViewById<EditText>(R.id.oldPassword).setText(settingViewModel.oldPassword)
        view.findViewById<EditText>(R.id.oldPassword).addTextChangedListener {
            settingViewModel.oldPassword = it.toString()
        }
        view.findViewById<EditText>(R.id.newPassword).setText(settingViewModel.newPassword)
        view.findViewById<EditText>(R.id.newPassword).addTextChangedListener {
            settingViewModel.newPassword = it.toString()
        }

        val buttonResetPassword = view.findViewById<Button>(R.id.buttonResetPassword)
        buttonResetPassword.setOnClickListener{
            println(view.findViewById<EditText>(R.id.newPassword).text)
            println(view.findViewById<EditText>(R.id.comfirmNewPassword).text)

            val newpassword = view.findViewById<EditText>(R.id.newPassword).text
            val confNewPassword = view.findViewById<EditText>(R.id.comfirmNewPassword).text
            if(newpassword.toString() == confNewPassword.toString()){
                settingViewModel.resetPassword()
            }
            else{
                println("Les mots de passe ne sont pas identique")
            }
        }

        //Switch bouton for dark mode
        val switchBtn = view.findViewById<Switch>(R.id.switchTheme)

        //Spinner for unite de mesure
        val spinner: Spinner = view.findViewById(R.id.uniteMesureSpinner)

        //Créer sauvegarde des settings
        val sharedPreference =  this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val switchTheme: Boolean = sharedPreference.getBoolean("switchTheme", false)
        val uniteMesure: String? = sharedPreference.getString("uniteMesure", "km")
        val editor = sharedPreference.edit()

        //Set les settings déjà sauvegardé
        switchBtn.isChecked = switchTheme

        spinner.setSelection(
            (spinner.adapter as ArrayAdapter<String?>).getPosition(
                uniteMesure
            )
        )

        //Dark mode
        switchBtn.setOnCheckedChangeListener { _, isChecked ->

            if (switchBtn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("switchTheme",true)
                editor.apply()

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("switchTheme",false)
                editor.apply()
            }
        }

        //Spinner unité de mesure
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                MainActivity.uniteMesure = parent?.selectedItem.toString()
                editor.putString("uniteMesure",parent?.selectedItem.toString())
                editor.apply()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

    }

}
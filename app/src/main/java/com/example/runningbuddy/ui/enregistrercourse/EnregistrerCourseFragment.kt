package com.example.runningbuddy.ui.enregistrercourse

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.runningbuddy.MainActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.MainActivity.Companion.ACTION_START_OR_RESUME_SERVICE
import com.example.runningbuddy.MainActivity.Companion.ACTION_PAUSE_SERVICE
import com.example.runningbuddy.MainActivity.Companion.ACTION_STOP_SERVICE
import com.example.runningbuddy.R
import com.example.runningbuddy.TrackingUtility
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class EnregistrerCourseFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var enregistrerCourseViewModel: EnregistrerCourseViewModel
    private lateinit var mapView: MapView

    //Create an object google and then we see this object with mapview
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_enregistrer_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        enregistrerCourseViewModel =
            ViewModelProvider(this).get(EnregistrerCourseViewModel::class.java)

        mapView = requireView().findViewById(R.id.mapView)
        //requireView().findViewById<Button>(R.id.btnStartRun).setOnClickListener {
        //    sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        //}

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
        }
    }

    private fun sendCommandToService(action: String) =
        //it == intent
        Intent(requireContext(), TrackingUtility::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the location permission to use the app",
                MainActivity.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the location permission to use the app",
                MainActivity.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        // if il a denied pour tjr le forcer a accepter haha
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
package com.example.runningbuddy.ui.enregistrercourse

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.runningbuddy.Constants.ACTION_PAUSE_SERVICE
import com.example.runningbuddy.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.runningbuddy.Constants.ACTION_STOP_SERVICE
import com.example.runningbuddy.Constants.MAP_ZOOM
import com.example.runningbuddy.Constants.POLYLINE_COLOR
import com.example.runningbuddy.Constants.POLYLINE_WIDTH
import com.example.runningbuddy.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.MainActivity.Companion.userId
import com.example.runningbuddy.R
import com.example.runningbuddy.TrackingUtility
import com.example.runningbuddy.models.RunPost
import com.example.runningbuddy.services.Polyline
import com.example.runningbuddy.services.TrackingService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
//import kotlinx.android.synthetic.main.fragment_enregistrer_course.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import kotlin.math.round


@SuppressLint("SetTextI18n")
class EnregistrerCourseFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var enregistrerCourseViewModel: EnregistrerCourseViewModel
    private lateinit var mapView: MapView

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    //Create an object google and then we see this object with mapview
    private var map: GoogleMap? = null

    // declare a global variable of FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var curTimeMillis = 0L

    private lateinit var imgView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_enregistrer_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.imgView = requireActivity().findViewById(R.id.imageTest)
        requestPermissions()
        enregistrerCourseViewModel =
            ViewModelProvider(this)[EnregistrerCourseViewModel::class.java]

        this.imgView.setImageBitmap(enregistrerCourseViewModel.imgCourseTest.value)

        enregistrerCourseViewModel.imgCourseTest.observe(viewLifecycleOwner) {
            this.imgView.setImageBitmap(enregistrerCourseViewModel.imgCourseTest.value)
        }

        val btnStartRun = requireView().findViewById<Button>(R.id.btnStartRun)
        val btnFinishRun = requireView().findViewById<Button>(R.id.btnFinishRun)

        mapView = requireView().findViewById(R.id.mapView)
        btnStartRun.setOnClickListener {
            toggleRun()
        }

        btnFinishRun.setOnClickListener {
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            addAllPolylines()
            getLastKnownLocation()
        }

        subscribeToObservers()

        // in onCreate() initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        //Initialiser les unités de mesure
        requireView().findViewById<TextView>(R.id.uniteMesureEnregistree).text = MainActivity.uniteMesure
        requireView().findViewById<TextView>(R.id.tvAvgSpeedEnregistrer).text = "0"
    }



    // function to check if tracking then update le button pour start ou stop
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if(!isTracking && curTimeMillis > 0L) {
            requireView().findViewById<Button>(R.id.btnStartRun).text = "Start"
            requireView().findViewById<Button>(R.id.btnFinishRun).visibility = View.VISIBLE
        } else if(isTracking) {
            requireView().findViewById<Button>(R.id.btnStartRun).text = "Stop"
            requireView().findViewById<Button>(R.id.btnFinishRun).visibility = View.GONE
        }
    }



    private fun subscribeToObservers() {
        // function permettant de d'observer
        // le state de isTracking et d'update le tracking en fonction
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it)
        }

        // observe aussi les pathpoints pour les rajouter et
        // bouger la camera en fonction de la postion
        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            pathPoints = it
            addAllPolylines()
            moveCameraToUser()
            requireView().findViewById<TextView>(R.id.tvDistanceEnregistrer).text = "${calculateDistanceInMeters()} m"
            requireView().findViewById<TextView>(R.id.tvAvgSpeedEnregistrer).text = calculateAvgSpeed().toString()
            requireView().findViewById<TextView>(R.id.tvCaloriesBurnedEnregistrer).text = "${calculateCaloriesBurned()} calories"
        }

        // observe le temps pour pouvoir la formet en fonction de si l'utilisateur cours
        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            curTimeMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeMillis, true)
            requireView().findViewById<TextView>(R.id.tvtimer).text = formattedTime
        }

        // Creation objet et POST les donnees de la course
        enregistrerCourseViewModel.id_course.observe(viewLifecycleOwner) {
            println("id observer: $it")
        }
    }



    // function pour vérifier si isTracking est égal à true si oui pause le service
    // sinon commence le
    private fun toggleRun() {
        if(isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }



    private fun stopRun() {
        requireView().findViewById<TextView>(R.id.tvtimer).text = "00:00:00:00"
        requireView().findViewById<TextView>(R.id.tvDistanceEnregistrer).text = "0 m"
        requireView().findViewById<TextView>(R.id.tvAvgSpeedEnregistrer).text = "0"
        requireView().findViewById<TextView>(R.id.tvCaloriesBurnedEnregistrer).text = "0 calories"
        sendCommandToService(ACTION_STOP_SERVICE)
    }



    /*
     * call this method for receive location
     * get location and give callback when successfully retrieve
     * function itself check location permission before access related methods
     */
    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    //println(location.longitude, location.latitude)
                    map?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(location.latitude, location.longitude),
                            MAP_ZOOM
                        )
                    )
                }
            }
    }



    // si 'utilisateur est situer sur la map bouger la camera sur lui (on sait qu'il est sur la map
    // si les pathpoints sont pas vide)
    private fun moveCameraToUser() {
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }



    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints) {
            for(pos in polyline) {
                bounds.include(pos)
            }
        }

        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.05f).toInt()
            )
        )
    }



    // Function calculate pour avoir les stats sur la course
    private fun calculateDistanceInMeters(): Int {
        var distance = 0
        for (polyline in pathPoints) {
            distance += TrackingUtility.calculatePolylineLength(polyline).toInt()
        }
        return distance
    }

    // TODO : changer en minute par km
    private fun calculateAvgSpeed(): Float {
        return round(
            (calculateDistanceInMeters() / 1000f) / (curTimeMillis / 1000f / 60 / 60) * 10
        ) / 10f
    }

    private fun calculateCaloriesBurned(): Int {
        return if (calculateDistanceInMeters() != 0) {
            ((calculateDistanceInMeters() / 1000f) * 80f).toInt()
        } else 0
    }



    private fun endRunAndSaveToDb() {
        map?.snapshot { bmp ->
            // Calcul des donnees de la course
            val distanceInMeters = calculateDistanceInMeters()
            val avgSpeed = calculateAvgSpeed()
            val calendar = Calendar.getInstance()
            val dateTimeStamp =              //Plus 1 pcq les mois commence a 0
                "${calendar[Calendar.YEAR]}-${calendar[Calendar.MONTH] + 1}-${calendar[Calendar.DATE]}"
            println(dateTimeStamp)
            val caloriesBurned = calculateCaloriesBurned()

            val runPost = RunPost(userId, bmp, dateTimeStamp, avgSpeed, distanceInMeters, curTimeMillis, caloriesBurned)
            if (bmp != null) {
                println(this.imgView)
                println(bmp)
                enregistrerCourseViewModel.imgCourseTest.value = bmp
                this.imgView.setImageBitmap(bmp)
                enregistrerCourseViewModel.insertRun(runPost, bmp)
            }

            println("Main course print: ${enregistrerCourseViewModel.id_course.value}")


            // Arrete le trajet
            stopRun()
            requireView().findNavController().navigate(
                R.id.navigation_home
            )
        }
    }



    // Add all polyline when the device is rotated
    private fun addAllPolylines() {
        for(polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }



    // TODO : pt enelever cet fonction la
    /** rajoute le dernier polyline (FONCTIONNE PAS!!!)
    private fun addLatestPolyline() {
        if(pathPoints.isEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }
    **/



    // petite fonction permettant d'envoyer une action au service
    private fun sendCommandToService(action: String) =
        //it == intent
        Intent(requireContext(), TrackingService::class.java).also {
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

    // demande les permisson en fonction de la réponse de la fonction hasLocationPermissions
    // de TrackingUtility
    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the location permission to use the app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the location permission to use the app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }


    // override la fonction permissionGranted puisqu'on utilise EasyPErmission et pas les permission
    // de base
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
        // TODO : changer deprecated pt si ca work
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
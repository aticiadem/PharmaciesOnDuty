package com.adematici.pharmaciesonduty.view.fragment

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.adematici.pharmaciesonduty.R
import com.adematici.pharmaciesonduty.databinding.FragmentMapsBinding
import com.adematici.pharmaciesonduty.model.Result
import com.adematici.pharmaciesonduty.util.SharedPref
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment : SupportMapFragment

    private lateinit var mySharedPref: SharedPref
    private val args: MapsFragmentArgs by navArgs()
    private lateinit var currentResult: Result
    private lateinit var locationListener: LocationListener
    private lateinit var locationManager: LocationManager
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater,container,false)
        val view = binding.root
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)
        registerLauncher()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.toolbar.inflateMenu(R.menu.map_menu)

        currentResult = args.currentItem
        mySharedPref = SharedPref(requireContext())
        locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager

        val province = mySharedPref.getLocationProvince()
        val district = mySharedPref.getLocationDistrict()
        val title = "$province - $district"
        binding.toolbar.title = title

        locationListener = LocationListener { locationX ->
            mMap.clear()
            val userLocation = LatLng(locationX.latitude,locationX.longitude)
            mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
            val total = currentResult.loc
            val delim = ","
            val result = total.split(delim)
            val location = LatLng(result[0].toDouble(),result[1].toDouble())
            mMap.addMarker(MarkerOptions().position(location).title("${currentResult.name} Eczanesi"))
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_location -> {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(
                                requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                            Snackbar.make(binding.root,R.string.permission_request,Snackbar.LENGTH_LONG)
                                .setAction(R.string.give_permission){
                                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }.show()
                        } else {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    } else {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 2000, 0f, locationListener)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            } else {
                Toast.makeText(requireContext(),R.string.permission_needed,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val total = currentResult.loc
        val delim = ","
        val result = total.split(delim)
        val location = LatLng(result[0].toDouble(),result[1].toDouble())
        mMap.addMarker(MarkerOptions().position(location).title("${currentResult.name} Eczanesi"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu,menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
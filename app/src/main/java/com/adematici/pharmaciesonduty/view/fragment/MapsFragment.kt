package com.adematici.pharmaciesonduty.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mySharedPref: SharedPref
    private val args: MapsFragmentArgs by navArgs()
    private lateinit var currentResult: Result
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentResult = args.currentItem
        mySharedPref = SharedPref(requireContext())

        val province = mySharedPref.getLocationProvince()
        val district = mySharedPref.getLocationDistrict()
        val title = "$province - $district"
        binding.toolbar.title = title

        val total = currentResult.loc
        val delim = ","
        val result = total.split(delim)
        val location = LatLng(result[0].toDouble(),result[1].toDouble())

        val callback = OnMapReadyCallback { googleMap ->
            googleMap.addMarker(MarkerOptions().position(location).title("${currentResult.name} Eczanesi"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.adematici.pharmaciesonduty.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.adematici.pharmaciesonduty.R
import com.adematici.pharmaciesonduty.databinding.FragmentSplashBinding
import com.adematici.pharmaciesonduty.util.SharedPref

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val SPLASH_TIME = 2500L
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.getSplashState()){
                findNavController().navigate(R.id.action_splashFragment_to_showPharmacyFragment)
            } else {
                sharedPref.setSplashState(true)
                findNavController().navigate(R.id.action_splashFragment_to_selectLocationFragment)
            }
        },SPLASH_TIME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.adematici.pharmaciesonduty.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.adematici.pharmaciesonduty.R
import com.adematici.pharmaciesonduty.databinding.FragmentSelectLocationBinding
import com.adematici.pharmaciesonduty.util.SharedPref

class SelectLocationFragment : Fragment() {

    private var _binding: FragmentSelectLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectLocationBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySharedPref = SharedPref(requireContext())

        binding.buttonConfirm.setOnClickListener {
            if (isValidInput()){
                val province = binding.textInputEditTextProvince.text.toString()
                val district = binding.textInputEditTextDistrict.text.toString()
                mySharedPref.setLocation(province,district)
                findNavController().navigate(R.id.action_selectLocationFragment_to_showPharmacyFragment)
            } else{
                Toast.makeText(requireContext(), R.string.do_not_blank,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidInput(): Boolean = binding.textInputEditTextProvince.text!!.isNotEmpty()
            && binding.textInputEditTextDistrict.text!!.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
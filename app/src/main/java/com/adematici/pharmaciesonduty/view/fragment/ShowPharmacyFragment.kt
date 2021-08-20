package com.adematici.pharmaciesonduty.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adematici.pharmaciesonduty.adapter.PharmacyAdapter
import com.adematici.pharmaciesonduty.databinding.FragmentShowPharmacyBinding
import com.adematici.pharmaciesonduty.model.Result

class ShowPharmacyFragment : Fragment() {

    private var _binding: FragmentShowPharmacyBinding? = null
    private val binding get() = _binding!!
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private lateinit var pharmacyList: ArrayList<Result>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowPharmacyBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pharmacyList = ArrayList()

        val x = Result("adres","dist","loc","name","phone")
        val y = Result("adres","dist","loc","name","phone")
        pharmacyList.add(x)
        pharmacyList.add(y)

        pharmacyAdapter = PharmacyAdapter(pharmacyList)

        binding.recyclerView.adapter = pharmacyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
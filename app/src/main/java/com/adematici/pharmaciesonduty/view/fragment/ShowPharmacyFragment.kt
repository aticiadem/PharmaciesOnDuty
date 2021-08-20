package com.adematici.pharmaciesonduty.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adematici.pharmaciesonduty.adapter.PharmacyAdapter
import com.adematici.pharmaciesonduty.databinding.FragmentShowPharmacyBinding
import com.adematici.pharmaciesonduty.model.Result
import com.adematici.pharmaciesonduty.viewmodel.ShowParmacyViewModel

class ShowPharmacyFragment : Fragment() {

    private var _binding: FragmentShowPharmacyBinding? = null
    private val binding get() = _binding!!
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private lateinit var pharmacyList: List<Result>
    private lateinit var viewModel: ShowParmacyViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(ShowParmacyViewModel::class.java)
        viewModel.getPharmacyData("Adana","Seyhan")

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.variables.observe(viewLifecycleOwner,{ data ->
            data?.let {
                pharmacyList = it
                pharmacyAdapter = PharmacyAdapter(pharmacyList)
                binding.recyclerView.adapter = pharmacyAdapter
                binding.textViewError.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
        viewModel.progressBarState.observe(viewLifecycleOwner,{ state ->
            state?.let {
                if (it){
                    binding.textViewError.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.errorMessageState.observe(viewLifecycleOwner,{ state ->
            state?.let {
                if (it){
                    binding.textViewError.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
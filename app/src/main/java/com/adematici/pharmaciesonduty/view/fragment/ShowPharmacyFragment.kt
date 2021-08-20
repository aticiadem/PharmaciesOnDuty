package com.adematici.pharmaciesonduty.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.adematici.pharmaciesonduty.R
import com.adematici.pharmaciesonduty.adapter.PharmacyAdapter
import com.adematici.pharmaciesonduty.databinding.FragmentShowPharmacyBinding
import com.adematici.pharmaciesonduty.model.Result
import com.adematici.pharmaciesonduty.util.SharedPref
import com.adematici.pharmaciesonduty.viewmodel.ShowParmacyViewModel

class ShowPharmacyFragment : Fragment() {

    private var _binding: FragmentShowPharmacyBinding? = null
    private val binding get() = _binding!!
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private lateinit var pharmacyList: List<Result>
    private lateinit var viewModel: ShowParmacyViewModel
    private lateinit var mySharedPref: SharedPref

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
        setHasOptionsMenu(true)
        binding.toolbar.inflateMenu(R.menu.top_bar_menu)
        mySharedPref = SharedPref(requireContext())

        val province = mySharedPref.getLocationProvince()
        val district = mySharedPref.getLocationDistrict()
        val title = "$province - $district"

        binding.toolbar.title = title

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings_button -> {
                    findNavController().navigate(R.id.action_showPharmacyFragment_to_selectLocationFragment)
                    true
                }
                else -> false
            }
        }

        viewModel = ViewModelProvider(requireActivity()).get(ShowParmacyViewModel::class.java)
        viewModel.getPharmacyData(province,district)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu,menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
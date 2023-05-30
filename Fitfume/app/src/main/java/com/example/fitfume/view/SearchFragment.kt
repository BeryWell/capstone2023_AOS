package com.example.fitfume.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentSearchBinding
import com.example.fitfume.viewmodel.GptViewModel
import com.example.fitfume.viewmodel.PerfumeViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: PerfumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.searchVm = viewModel

        with(binding) {
            toggleSelectionOnClick(searchFilterJomalronBtn)
            toggleSelectionOnClick(searchFilterDiptyqueBtn)
            toggleSelectionOnClick(searchFilterChannelBtn)
            toggleSelectionOnClick(searchFilterDiorBtn)
            toggleSelectionOnClick(searchFilterLanvinBtn)
            toggleSelectionOnClick(searchFilterGucciBtn)
            toggleSelectionOnClick(searchFilterSpringBtn)
            toggleSelectionOnClick(searchFilterSummerBtn)
            toggleSelectionOnClick(searchFilterAutumnBtn)
            toggleSelectionOnClick(searchFilterWinterBtn)
            toggleSelectionOnClick(searchFilterManBtn)
            toggleSelectionOnClick(searchFilterNeutralityBtn)
            toggleSelectionOnClick(searchFilterWomanBtn)
        }


        binding.searchGoToResultBtn.setOnClickListener {
            val perfumeName = binding.searchPerfumeWordEt.text.toString()
//            viewModel.findPerfumeByName(perfumeName)

            val intent = Intent(requireActivity(), SearchResultActivity::class.java)
            intent.putExtra("name", perfumeName)
            startActivity(intent)

        }



        return binding.root
    }

    private fun toggleSelectionOnClick(button: View) {
        button.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }

}
package com.example.fitfume.view

import android.os.Bundle
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

        binding.searchFindPerfume.setOnClickListener {
            viewModel.findAllPerfumes()
        }
        return binding.root
    }

}
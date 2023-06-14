package com.example.fitfume.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfume.R
import com.example.fitfume.adapter.BestPerfumetRecyclerViewAdapter
import com.example.fitfume.adapter.PerfumeReviewRecyclerViewAdapter
import com.example.fitfume.databinding.FragmentMyBinding
import com.example.fitfume.model.BestPerfumeEvent
import com.example.fitfume.model.PerfumeReviewEvent
import com.example.fitfume.viewmodel.FitfumeViewModel

class MyFragment : Fragment() {
    private lateinit var binding: FragmentMyBinding
    private val viewModel: FitfumeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        binding.lifecycleOwner = this
        binding.fitfumeVm = viewModel

        binding.myReviewRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val bestPerfumeRecyclerViewAdapter = PerfumeReviewRecyclerViewAdapter()
        binding.myReviewRv.adapter = bestPerfumeRecyclerViewAdapter
//
        var list: MutableList<PerfumeReviewEvent> = mutableListOf()
//        list.add(PerfumeReviewEvent("lh99j", 0.0F))
//        list.add(PerfumeReviewEvent("lh99j", 1.0F))
//        list.add(PerfumeReviewEvent("lh99j", 2.0F))
//        list.add(PerfumeReviewEvent("lh99j", 3.0F))
//        list.add(PerfumeReviewEvent("lh99j", 4.0F))
//        list.add(PerfumeReviewEvent("lh99j", 5.0F))
//
//
        bestPerfumeRecyclerViewAdapter.submitBestPerfumeEventList(list)


        return binding.root
    }

}
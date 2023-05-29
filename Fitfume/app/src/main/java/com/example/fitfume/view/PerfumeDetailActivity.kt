package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfume.R
import com.example.fitfume.adapter.PerfumeReviewRecyclerViewAdapter
import com.example.fitfume.databinding.ActivityPerfumeDetailBinding
import com.example.fitfume.model.PerfumeReviewEvent
import com.example.fitfume.viewmodel.PerfumeViewModel

class PerfumeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfumeDetailBinding
    private val viewModel : PerfumeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfumeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.perfumeDatailVm = viewModel;

        binding.perfumeReviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bestPerfumeRecyclerViewAdapter = PerfumeReviewRecyclerViewAdapter()
        binding.perfumeReviewRv.adapter = bestPerfumeRecyclerViewAdapter

        var list: MutableList<PerfumeReviewEvent> = mutableListOf()
        list.add(PerfumeReviewEvent("lh99j", 0.0F))
        list.add(PerfumeReviewEvent("lh99j", 1.0F))
        list.add(PerfumeReviewEvent("lh99j", 2.0F))
        list.add(PerfumeReviewEvent("lh99j", 3.0F))
        list.add(PerfumeReviewEvent("lh99j", 4.0F))
        list.add(PerfumeReviewEvent("lh99j", 5.0F))
        list.add(PerfumeReviewEvent("lh99j", 5.0F))
        list.add(PerfumeReviewEvent("lh99j", 4.0F))
        list.add(PerfumeReviewEvent("lh99j", 3.0F))
        list.add(PerfumeReviewEvent("lh99j", 2.0F))
        list.add(PerfumeReviewEvent("lh99j", 1.0F))



        bestPerfumeRecyclerViewAdapter.submitBestPerfumeEventList(list)

    }
}
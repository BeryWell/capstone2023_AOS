package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fitfume.R
import com.example.fitfume.adapter.PerfumeReviewRecyclerViewAdapter
import com.example.fitfume.databinding.ActivityPerfumeDetailBinding
import com.example.fitfume.model.PerfumeReviewEvent
import com.example.fitfume.viewmodel.PerfumeViewModel
import kotlin.math.log

class PerfumeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfumeDetailBinding
    private val viewModel : PerfumeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfumeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.perfumeDatailVm = viewModel;

        setSupportActionBar(binding.perfumeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        val intentBrand = intent.getStringExtra("brand")
        val intentTitle = intent.getStringExtra("title")
        val intentCount = intent.getStringExtra("count")
        val topNotes = intent.getStringExtra("topNotes")
        val middleNotes = intent.getStringExtra("middleNotes")
        val baseNotes = intent.getStringExtra("baseNotes")
        val imgUrl = intent.getStringExtra("imgUrl")

        val regexPattern = Regex("http://www.basenotes.net/photos/products/(\\w+|\\d+)/(\\d+(-\\d+)?)\\.jpg")
        val convertedUrl = imgUrl!!.replace(regexPattern, "https://basenotes.com/img/product/$2-j")
        Log.d("lhj", "onCreate: $convertedUrl")
        Glide.with(this)
            .load(convertedUrl)
            .error(R.drawable.recommend_question_four_three)
            .into(binding.perfumeImageIv)

        binding.perfumeBrandTv.text = intentBrand
        binding.perfumeNameTv.text = intentTitle
        binding.perfumeToolbar.title = intentTitle
        binding.perfumeTopNoteInfoTv.text = topNotes
        binding.perfumeMiddleNoteInfoTv.text = middleNotes
        binding.perfumeBaseNoteInfoTv.text = baseNotes

        Log.d("lhj_purfumeDetail", "onCreate: $intentBrand $intentTitle $intentCount")

    }
}
package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfume.R
import com.example.fitfume.adapter.PerfumeReviewRecyclerViewAdapter
import com.example.fitfume.adapter.SearchResultRecyclerViewAdapter
import com.example.fitfume.databinding.ActivitySearchResultBinding
import com.example.fitfume.model.PerfumeReviewEvent
import com.example.fitfume.model.SearchResultEvent
import com.example.fitfume.viewmodel.PerfumeViewModel

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private val viewModel: PerfumeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.searchResultVm = viewModel

        binding.searchResultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val searchResultRecyclerViewAdapter = SearchResultRecyclerViewAdapter()
        binding.searchResultRv.adapter = searchResultRecyclerViewAdapter

        var list: MutableList<SearchResultEvent> = mutableListOf()
        list.add(SearchResultEvent("Dior", "미스 디올 블루밍 부케 오드 뚜왈렛",0.0F, "10+"))
        list.add(SearchResultEvent("Channel", "미스 디올",1.0F, "10+"))
        list.add(SearchResultEvent("Dipaa", "미스 디올 블루밍 부케 오드 뚜왈렛",2.0F, "4"))
        list.add(SearchResultEvent("GuCCI", "미스 디올 블루밍 부케 오드 뚜왈렛",3.0F, "2"))
        list.add(SearchResultEvent("Dior", "미스 디올 블루밍",4.0F, "10+"))
        list.add(SearchResultEvent("abcdefgha", "미스 디올 블루밍 부케 오드 뚜왈렛",5.0F, "2"))


        searchResultRecyclerViewAdapter.submitSearchResultEventList(list)

    }
}
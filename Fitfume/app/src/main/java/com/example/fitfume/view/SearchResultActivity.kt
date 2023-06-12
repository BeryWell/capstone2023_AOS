package com.example.fitfume.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
        var topNotes = ""
        var middleNotes = ""
        var baseNotes = ""
        var imgUrl = ""
        binding.searchResultRv.adapter = searchResultRecyclerViewAdapter

        var list: MutableList<SearchResultEvent> = mutableListOf()
//        list.add(SearchResultEvent("Dior", "미스 디올 블루밍 부케 오드 뚜왈렛",0.0F, "10+"))
//        list.add(SearchResultEvent("Channel", "미스 디올",1.0F, "10+"))
//        list.add(SearchResultEvent("Dipaa", "미스 디올 블루밍 부케 오드 뚜왈렛",2.0F, "4"))
//        list.add(SearchResultEvent("GuCCI", "미스 디올 블루밍 부케 오드 뚜왈렛",3.0F, "2"))
//        list.add(SearchResultEvent("Dior", "미스 디올 블루밍",4.0F, "10+"))
//        list.add(SearchResultEvent("abcdefgha", "미스 디올 블루밍 부케 오드 뚜왈렛",5.0F, "2"))


        val perfumeName = intent.getStringExtra("name")
        viewModel.findPerfumeByName(perfumeName!!)

        setSupportActionBar(binding.perfumeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.perfumeByName.observe(this, Observer {
            list.clear()

            list.add(SearchResultEvent(it.brand, it.name, 0.0F, "10+"))

            binding.perfumeToolbar.title = it.name
            searchResultRecyclerViewAdapter.submitSearchResultEventList(list)

            if(it.topNotes.isNotEmpty()){
                it.topNotes.forEach{
                    topNotes += "${it.korName}  "
                }
            }

            if(it.heartNotes.isNotEmpty()){
                it.heartNotes.forEach {
                    middleNotes += "${it.korName}  "
                }
            }

            if(it.baseNotes.isNotEmpty()){
                it.baseNotes.forEach {
                    baseNotes += "${it.korName}  "
                }
            }

            imgUrl = it.imgUrl
        })

        searchResultRecyclerViewAdapter.submitSearchResultEventList(list)


        searchResultRecyclerViewAdapter.setOnItemClickListener(
            object:SearchResultRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: SearchResultEvent, pos: Int) {
                    val intent = Intent(this@SearchResultActivity, PerfumeDetailActivity::class.java)
                    intent.putExtra("brand", data.brand)
                    intent.putExtra("title", data.title)
//                    intent.putExtra("star", data.star)
                    intent.putExtra("count", data.count)
                    intent.putExtra("topNotes", topNotes)
                    intent.putExtra("middleNotes", middleNotes)
                    intent.putExtra("baseNotes", baseNotes)
                    intent.putExtra("imgUrl", imgUrl)
                    startActivity(intent)
                }
            }
        )

    }

    override fun onPause() {
        super.onPause()
        viewModel.perfumeByName.removeObservers(this)
    }
}
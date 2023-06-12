package com.example.fitfume.view

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.fitfume.R
import com.example.fitfume.adapter.BestPerfumetRecyclerViewAdapter
import com.example.fitfume.adapter.FitfumeEventBannerViewPagerAdapter
import com.example.fitfume.databinding.FragmentHomeBinding
import com.example.fitfume.model.BestPerfumeEvent
import com.example.fitfume.viewmodel.FitfumeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: FitfumeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.fitfumeVm = viewModel

        binding.homeBestPerfumeRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val bestPerfumeRecyclerViewAdapter = BestPerfumetRecyclerViewAdapter()
        binding.homeBestPerfumeRv.adapter = bestPerfumeRecyclerViewAdapter

        var list: MutableList<BestPerfumeEvent> = mutableListOf()
        list.add(BestPerfumeEvent("1", "Dior", "미스 디올 블루밍 부케 오 드 뚜왈렛", R.drawable.home_best_perfume_1))
        list.add(BestPerfumeEvent("2", "JO MALONE", "잉글리쉬 페어 앤 프리지아 코롱", R.drawable.home_best_perfume_2))
        list.add(BestPerfumeEvent("3", "BYREDO", "바이레도 블랑쉬 오 드 퍼퓸", R.drawable.home_best_perfume_3))
        list.add(BestPerfumeEvent("4", "롤리타렘피카", "롤리타렘피카 우먼 오 드 퍼퓸", R.drawable.home_best_perfume_4))
        list.add(BestPerfumeEvent("5", "DIPTYQUE", "도 손 오드퍼퓸", R.drawable.home_best_perfume_5))

        bestPerfumeRecyclerViewAdapter.submitBestPerfumeEventList(list)

        val mbtiOptions = listOf(
            Pair(binding.homeRecommendMbtiICl, binding.homeRecommendMbtiECl),
            Pair(binding.homeRecommendMbtiNCl, binding.homeRecommendMbtiSCl),
            Pair(binding.homeRecommendMbtiFCl, binding.homeRecommendMbtiTCl),
            Pair(binding.homeRecommendMbtiPCl, binding.homeRecommendMbtiJCl)
        )

        val clickListener: (View) -> Unit = { clickedView ->
            mbtiOptions.forEach { (option1, option2) ->
                if (option1 == clickedView) {
                    option1.isSelected = true
                    option2.isSelected = false
                } else if (option2 == clickedView) {
                    option1.isSelected = false
                    option2.isSelected = true
                }
            }

            updateButtonVisibility(mbtiOptions)
        }

        mbtiOptions.forEach { (option1, option2) ->
            option1.setOnClickListener(clickListener)
            option2.setOnClickListener(clickListener)
        }

        binding.homeRecommendMbtiBtn.visibility = View.GONE

        binding.homeRecommendMbtiBtn.setOnClickListener {
            var mbti = ""

            if(binding.homeRecommendMbtiICl.isSelected){
                mbti += "I"
            }else{
                mbti += "E"
            }

            if(binding.homeRecommendMbtiNCl.isSelected){
                mbti += "N"
            }else{
                mbti += "S"
            }

            if(binding.homeRecommendMbtiFCl.isSelected){
                mbti += "F"
            }else{
                mbti += "T"
            }

            if(binding.homeRecommendMbtiPCl.isSelected){
                mbti += "P"
            }else{
                mbti += "J"
            }

            val intent = Intent(requireActivity(), RecommendMbtiResultActivity::class.java)
            intent.putExtra("mbti", mbti)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = FitfumeEventBannerViewPagerAdapter()
        binding.csEventBannerVp.adapter = viewPagerAdapter


        autoScrollViewPager(binding.csEventBannerVp)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCampsiteEventBannerItems()
    }

    private fun autoScrollViewPager(viewPager2: ViewPager2) {
        this.lifecycleScope.launchWhenResumed {
            while (this@HomeFragment.lifecycleScope.isActive) {
                delay(3000)
                viewModel.setCurrentPosition(viewModel.currentPosition.value!!.plus(1) % 7)
                viewPager2.currentItem = viewModel.currentPosition.value!!
            }
        }
    }

    private fun updateButtonVisibility(options: List<Pair<View, View>>) {
        val trueCount = options.count { (option1, option2) ->
            option1.isSelected || option2.isSelected
        }

        if (trueCount == 4) {
            binding.homeRecommendMbtiBtn.visibility = View.VISIBLE
        } else {
            binding.homeRecommendMbtiBtn.visibility = View.GONE
        }
    }

}
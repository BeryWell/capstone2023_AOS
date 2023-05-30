package com.example.fitfume.view

import android.os.Bundle
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
}
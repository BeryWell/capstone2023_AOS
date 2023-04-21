package com.example.fitfume.adapter

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fitfume.viewmodel.FitfumeViewModel

object BindingConversions {
    @JvmStatic
    @BindingAdapter("cs_event_setCount",
        "cs_event_setCVm",
        "cs_event_setLeftBtn",
        "cs_event_setRightBtn",
        requireAll = false)
    fun setCsEventBannerViewPager(
        viewPager: ViewPager2,
        textView: TextView,
        campsiteVm: FitfumeViewModel,
        leftBtn: Button,
        rightBtn: Button,
    ) {
        campsiteVm.getCampsiteEventBannerItems()
        val adapter = viewPager.adapter as? FitfumeEventBannerViewPagerAdapter
        adapter.let {
            it!!.submitCampsiteEventBannerList(campsiteVm.campsiteEventList.value!!)
            it.notifyDataSetChanged()
        }
        viewPager.isUserInputEnabled = false

        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                campsiteVm.setCurrentPosition(position)
                textView.text = (campsiteVm.currentPosition.value!! + 1).toString()

            }
        })
        leftBtn.setOnClickListener {
            viewPager.currentItem--
            if (viewPager.currentItem == 0) {
                viewPager.currentItem = 6
            }
        }
        rightBtn.setOnClickListener {
            viewPager.currentItem++
            if (viewPager.currentItem == 6) {
                viewPager.currentItem = 0
            }
        }
    }
}
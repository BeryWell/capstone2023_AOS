package com.example.fitfume.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfume.R
import com.example.fitfume.model.FitfumeEventBanner

class FitfumeEventBannerViewPagerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_COUNT = 7
    }

    private var fitfumeEventItemList: List<FitfumeEventBanner>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FitfumeEventViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_fitfume_event_banner, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        fitfumeEventItemList?.let { fitfumeEventItemList ->
            (holder as FitfumeEventViewHolder).bind(fitfumeEventItemList[position])
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    fun submitCampsiteEventBannerList(list: List<FitfumeEventBanner>){
        fitfumeEventItemList = list
        notifyDataSetChanged()
    }

    class FitfumeEventViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.cs_item_event_banner_title_tv)!!
        private val subTitle = itemView.findViewById<TextView>(R.id.cs_item_event_banner_sub_title_tv)
        private val backgroundCl = itemView.findViewById<ConstraintLayout>(R.id.cs_item_event_banner_cl)

        fun bind(fitfumeEventItem: FitfumeEventBanner) {
            title.text= fitfumeEventItem.title
            subTitle.text = fitfumeEventItem.subTitle

            backgroundCl.setBackgroundResource(fitfumeEventItem.imgUrl)
        }
    }

}
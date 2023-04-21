package com.example.fitfume.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfume.R
import com.example.fitfume.model.BestPerfumeEvent

class BestPerfumetRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bestPerfumeEvent: List<BestPerfumeEvent>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeBestPerfumeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_best_perfume, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bestPerfumeEvent?.let { bestPerfumeEvent ->
            (holder as HomeBestPerfumeViewHolder).bind(bestPerfumeEvent[position])
        }
    }

    override fun getItemCount(): Int {
        return bestPerfumeEvent!!.size
    }

    fun submitBestPerfumeEventList(list: List<BestPerfumeEvent>) {
        bestPerfumeEvent = list
        notifyDataSetChanged()
    }

    class HomeBestPerfumeViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val count: TextView = itemView.findViewById(R.id.perfume_count_tv)
        var brand: TextView = itemView.findViewById(R.id.perfume_brand_tv)
        var title: TextView = itemView.findViewById(R.id.perfume_title_tv)

        //        val img : ImageView = itemView.findViewById(R.id.item_event_iv)
        fun bind(bestPerfumeEvent: BestPerfumeEvent) {
            count.text = bestPerfumeEvent.count
            brand.text = bestPerfumeEvent.brand
            title.text = bestPerfumeEvent.title
//            img.setImageDrawable(communityEvent.img)
        }
    }
}
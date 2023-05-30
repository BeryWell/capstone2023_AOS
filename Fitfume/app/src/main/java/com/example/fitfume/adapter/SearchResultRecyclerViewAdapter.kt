package com.example.fitfume.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfume.R
import com.example.fitfume.model.BestPerfumeEvent
import com.example.fitfume.model.PerfumeReviewEvent
import com.example.fitfume.model.SearchResultEvent

class SearchResultRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var searchResultEvent: List<SearchResultEvent>? = null

    interface OnItemClickListener{
        fun onItemClick(v: View, data: SearchResultEvent, pos: Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        searchResultEvent?.let { searchResultEvent ->
            (holder as SearchResultViewHolder).bind(searchResultEvent[position])
        }
    }

    override fun getItemCount(): Int {
        return searchResultEvent!!.size
    }

    fun submitSearchResultEventList(list: List<SearchResultEvent>) {
        searchResultEvent = list
        notifyDataSetChanged()
    }

    inner class SearchResultViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val brand: TextView = itemView.findViewById(R.id.search_result_branch_tv)
        val title: TextView = itemView.findViewById(R.id.search_result_title_tv)
        val star: RatingBar = itemView.findViewById(R.id.search_result_star_rb)
        val count: TextView = itemView.findViewById(R.id.search_result_review_count_tv)

        //        val img : ImageView = itemView.findViewById(R.id.item_event_iv)
        fun bind(searchResultEvent: SearchResultEvent) {
            brand.text = searchResultEvent.brand
            title.text = searchResultEvent.title
            star.rating = searchResultEvent.star
            count.text = "( ${searchResultEvent.count} )"
//            img.setImageDrawable(communityEvent.img)

            val pos = adapterPosition

            if(pos!=RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, searchResultEvent, pos)
                }
            }

        }
    }
}
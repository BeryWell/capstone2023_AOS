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
import org.w3c.dom.Text

class PerfumeReviewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var perfumeReviewEvent: List<PerfumeReviewEvent>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PerfumeReviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        perfumeReviewEvent?.let { perfumeReviewEvent ->
            (holder as PerfumeReviewViewHolder).bind(perfumeReviewEvent[position])
        }
    }

    override fun getItemCount(): Int {
        return perfumeReviewEvent!!.size
    }

    fun submitBestPerfumeEventList(list: List<PerfumeReviewEvent>) {
        perfumeReviewEvent = list
        notifyDataSetChanged()
    }

    class PerfumeReviewViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val nickname: TextView = itemView.findViewById(R.id.review_user_nickname_tv)
        val gender: TextView = itemView.findViewById(R.id.review_user_sex_tv)
        val style: TextView = itemView.findViewById(R.id.review_user_style_tv)
        val season: TextView = itemView.findViewById(R.id.review_user_job_tv)
        val date: TextView = itemView.findViewById(R.id.reivew_date_tv)
        val title: TextView = itemView.findViewById(R.id.review_perfume_name_tv)
        val brand: TextView = itemView.findViewById(R.id.review_perfume_brand_tv)
        val description: TextView = itemView.findViewById(R.id.review_user_write_tv)
        val star: RatingBar = itemView.findViewById(R.id.review_user_star_rb)

        //        val img : ImageView = itemView.findViewById(R.id.item_event_iv)
        fun bind(perfumeReviewEvent: PerfumeReviewEvent) {
            nickname.text = perfumeReviewEvent.nickname
            gender.text = perfumeReviewEvent.gender
            season.text = perfumeReviewEvent.season
            date.text = perfumeReviewEvent.date
            style.text = perfumeReviewEvent.style
            title.text = perfumeReviewEvent.perfumeTitle
            brand.text = perfumeReviewEvent.perfumeBrand
            description.text = perfumeReviewEvent.description
            star.rating = perfumeReviewEvent.star
//            img.setImageDrawable(communityEvent.img)
        }
    }
}
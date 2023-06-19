package com.example.fitfume.view

import android.content.Intent
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
import com.example.fitfume.viewmodel.ReviewViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PerfumeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfumeDetailBinding
    private val viewModel: PerfumeViewModel by viewModels()
    private val reviewViewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfumeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.perfumeDatailVm = viewModel;

        setSupportActionBar(binding.perfumeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.perfumeReviewRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bestPerfumeRecyclerViewAdapter = PerfumeReviewRecyclerViewAdapter()
        binding.perfumeReviewRv.adapter = bestPerfumeRecyclerViewAdapter

        var list: MutableList<PerfumeReviewEvent> = mutableListOf()

        val intentBrand = intent.getStringExtra("brand")
        val intentTitle = intent.getStringExtra("title")
        val intentCount = intent.getStringExtra("count")
        val topNotes = intent.getStringExtra("topNotes")
        val middleNotes = intent.getStringExtra("middleNotes")
        val baseNotes = intent.getStringExtra("baseNotes")
        val imgUrl = intent.getStringExtra("imgUrl")
        val id = intent.getStringExtra("id")!!.toInt()
        var reviewStar = intent.getStringExtra("reviewStar")

        val convertedUrl = convertImageUrl(imgUrl)
        loadImage(convertedUrl)

        binding.perfumeBrandTv.text = intentBrand
        binding.perfumeNameTv.text = intentTitle
        binding.perfumeToolbar.title = intentTitle
        binding.perfumeTopNoteInfoTv.text = topNotes
        binding.perfumeMiddleNoteInfoTv.text = middleNotes
        binding.perfumeBaseNoteInfoTv.text = baseNotes
        binding.perfumeReviewGradeTv.text = reviewStar
        binding.perfumeReviewGrade1Tv.text = reviewStar

        Log.d("lhj_purfumeDetail", "onCreate: $intentBrand $intentTitle $intentCount")


        reviewViewModel.getAllReviewByPerfumeId(perfumeId = id)

        reviewViewModel.getAllPerfumeReview.observe(this, Observer {
            Log.d("reviewPerfume", "Review $it")
            var count = 0
            var seasonCount = 0
            var styleCount = 0
            var genderCount = 0
            var vitalityCount = 0


            if (it.isNotEmpty()) {
                for (i in it.indices) {
                    if (it[i].writeUser != null) {
                        count++
                        var season = ""
                        var style = ""
                        var gender = ""

                        val originalDateTimeString = it[i].createdAt
                        val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        val targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                        val originalDateTime =
                            LocalDateTime.parse(originalDateTimeString, originalFormat)
                        val targetDateString = originalDateTime.format(targetFormat)


                        when (it[i].seasons) {
                            "SPRING" -> {
                                season = "봄"
                                seasonCount += 1
                            }
                            "SUMMER" -> {
                                season = "여름"
                                seasonCount += 2
                            }
                            "FALL" -> {
                                season = "가을"
                                seasonCount += 3
                            }
                            else -> {
                                season = "겨울"
                                seasonCount += 4
                            }
                        }

                        when (it[i].fashionStyle) {
                            "CASUAL" -> {
                                style = "캐쥬얼"
                                styleCount += 1
                            }
                            "STREET" -> {
                                style = "스트릿"
                                styleCount += 2
                            }
                            "CLASSIC" -> {
                                style = "클래식"
                                styleCount += 3
                            }
                            "SPORTY" -> {
                                style = "스포티"
                                styleCount += 4
                            }
                            else -> {
                                style = "빈티지"
                                styleCount += 5
                            }
                        }

                        vitalityCount += when(it[i].perfumeVitality){
                            "VERY_WEAK" -> 1
                            "WEAK" -> 2
                            "MODERATE" -> 3
                            "STRONG" -> 4
                            else -> 5
                        }

                        genderCount += when(it[i].genderExpressionScale){
                            "VERY_FEMININE" -> 1
                            "FEMININE" -> 2
                            "NEUTRAL" -> 3
                            "MASCULINE" -> 4
                            else -> 5
                        }


                        gender = when (it[i].writeUser.gender) {
                            "MALE" -> "님성"
                            else -> "여성"
                        }

                        list.add(
                            PerfumeReviewEvent(
                                it[i].writeUser.username,
                                gender,
                                style,
                                season,
                                targetDateString,
                                intentTitle!!,
                                intentBrand!!,
                                it[i].description,
                                it[i].rating
                            )
                        )
                    }
                }
            }

            binding.perfumeReviewCountTv.text = "( ${count.toString()} )"
            binding.perfumeReviewCount1Tv.text = "( ${count.toString()} )"

//            var seasonCount = 0
//            var styleCount = 0
//            var genderCount = 0
//            var vitalityCount = 0

            var avgSeason = Math.round((seasonCount.toDouble() / count)).toInt()
            var avgStyle = Math.round((styleCount.toDouble() / count)).toInt()
            var avgGender = Math.round((genderCount.toDouble() / count)).toInt()
            var avgVitality = Math.round((vitalityCount.toDouble() / count)).toInt()

            Log.d("avglhj", "avg: season : $avgSeason style : $avgStyle gender : $avgGender vita : $avgVitality")

            setWeatherPercentImage(avgSeason)
            setStylePercentImage(avgStyle)
            setSexualPercentImage(avgGender)
            setPersistencePercentImage(avgVitality)

            bestPerfumeRecyclerViewAdapter.submitBestPerfumeEventList(list)

        })

        bestPerfumeRecyclerViewAdapter.submitBestPerfumeEventList(list)

        binding.perfumeGoToReviewBtn.setOnClickListener {
            val intent = Intent(this@PerfumeDetailActivity, CreateReviewActivity::class.java)
            intent.putExtra("title", intentTitle)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun convertImageUrl(url: String?): String {
        val regexPattern =
            Regex("http://www.basenotes.net/photos/products/(\\w+|\\d+)/(\\d+(-\\d+)?)\\.jpg")
        return url?.replace(regexPattern, "https://basenotes.com/img/product/$2-j")
            ?: ""
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.recommend_question_four_three)
            .into(binding.perfumeImageIv)
    }

    private fun convertDateTimeFormat(dateTimeString: String): String {
        val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val originalDateTime = LocalDateTime.parse(dateTimeString, originalFormat)
        return originalDateTime.format(targetFormat)
    }

    private fun setWeatherPercentImage(avgSeason: Int) {
        val resourceId = when (avgSeason) {
            1 -> R.drawable.perfume_weather_1
            2 -> R.drawable.perfume_weather_2
            3 -> R.drawable.perfume_weather_3
            else -> R.drawable.perfume_weather_4
        }
        binding.perfumeWeatherPercentIv.setImageResource(resourceId)
    }

    private fun setStylePercentImage(avgStyle: Int) {
        val resourceId = when (avgStyle) {
            1 -> R.drawable.perfume_style_1
            2 -> R.drawable.perfume_style_2
            3 -> R.drawable.perfume_style_3
            4 -> R.drawable.perfume_style_4
            else -> R.drawable.perfume_style_5
        }
        binding.perfumeStylePercentIv.setImageResource(resourceId)
    }

    private fun setSexualPercentImage(avgGender: Int) {
        val resourceId = when (avgGender) {
            1 -> R.drawable.perfume_sexual_1
            2 -> R.drawable.perfume_sexual_2
            3 -> R.drawable.perfume_sexual_3
            4 -> R.drawable.perfume_sexual_4
            else -> R.drawable.perfume_sexual_5
        }
        binding.perfumeSexualPercentIv.setImageResource(resourceId)
    }

    private fun setPersistencePercentImage(avgVitality: Int) {
        val resourceId = when (avgVitality) {
            1 -> R.drawable.perfume_persistence_1
            2 -> R.drawable.perfume_persistence_2
            3 -> R.drawable.perfume_persistence_3
            4 -> R.drawable.perfume_persistence_4
            else -> R.drawable.perfume_persistence_5
        }
        binding.perfumePersistencePercentIv.setImageResource(resourceId)
    }
}


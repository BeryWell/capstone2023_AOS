package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.fitfume.R
import com.example.fitfume.databinding.ActivityCreateReviewBinding
import com.example.fitfume.network.data.request.CreateReviewRequest
import com.example.fitfume.viewmodel.ReviewViewModel

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateReviewBinding
    private val viewModel: ReviewViewModel by viewModels()

    private var vitality = 0
    private var gender = 0
    private var season = 0
    private var style = 0

    private var vitalityFlag = false
    private var genderFlag = false
    private var seasonFlag = false
    private var styleFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.reviewVm = viewModel

        setSupportActionBar(binding.reviewToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val perfumeTitle = intent.getStringExtra("title")
        Log.d("lhj", "onCreateReview: $perfumeTitle")
        binding.reviewToolbar.title = perfumeTitle


        setupVitalityCheckboxes()
        setupGenderCheckboxes()
        setupSeasonCheckboxes()
        setupStyleCheckboxes()

        val id = intent.getIntExtra("id", 0)

        binding.perfumeGoToReviewBtn.setOnClickListener {
            Log.d("lhjFlag", "flag: $vitalityFlag $genderFlag $seasonFlag $styleFlag")
            val description = binding.perfumeReviewEt.text.toString()

            viewModel.createReview(CreateReviewRequest(description, style, gender, id, vitality, season))
        }
    }

    private fun setupVitalityCheckboxes() {
        val vitalityCheckboxes = listOf(
            binding.perfumeVitality1Cb,
            binding.perfumeVitality2Cb,
            binding.perfumeVitality3Cb,
            binding.perfumeVitality4Cb,
            binding.perfumeVitality5Cb
        )

        vitalityCheckboxes.forEachIndexed { index, checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    vitalityFlag = true
                    vitality = index
                    vitalityCheckboxes.forEachIndexed { i, cb ->
                        cb.isChecked = (i == index)
                    }
                }
            }
        }
    }

    private fun setupGenderCheckboxes() {
        val genderCheckboxes = listOf(
            binding.perfumeGender1Cb,
            binding.perfumeGender2Cb,
            binding.perfumeGender3Cb,
            binding.perfumeGender4Cb,
            binding.perfumeGender5Cb
        )

        genderCheckboxes.forEachIndexed { index, checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    genderFlag = true
                    gender = index
                    genderCheckboxes.forEachIndexed { i, cb ->
                        cb.isChecked = (i == index)
                    }
                }
            }
        }
    }

    private fun setupSeasonCheckboxes() {
        val seasonCheckboxes = listOf(
            binding.perfumeSeason1Cb,
            binding.perfumeSeason2Cb,
            binding.perfumeSeason3Cb,
            binding.perfumeSeason4Cb,
            binding.perfumeSeason5Cb
        )

        seasonCheckboxes.forEachIndexed { index, checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    seasonFlag = true
                    season = index
                    seasonCheckboxes.forEachIndexed { i, cb ->
                        cb.isChecked = (i == index)
                    }
                }
            }
        }
    }

    private fun setupStyleCheckboxes() {
        val styleCheckboxes = listOf(
            binding.perfumeStyle1Cb,
            binding.perfumeStyle2Cb,
            binding.perfumeStyle3Cb,
            binding.perfumeStyle4Cb,
            binding.perfumeStyle5Cb
        )

        styleCheckboxes.forEachIndexed { index, checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    styleFlag = true
                    style = index
                    styleCheckboxes.forEachIndexed { i, cb ->
                        cb.isChecked = (i == index)
                    }
                }
            }
        }
    }
}

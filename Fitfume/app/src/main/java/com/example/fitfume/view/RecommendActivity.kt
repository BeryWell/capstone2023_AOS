package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fitfume.R
import com.example.fitfume.databinding.ActivityRecommendBinding

class RecommendActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecommendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(RecommendQ1Fragment())
    }

    public fun replaceFragment(fragment: Fragment) {
        val transcation = supportFragmentManager.beginTransaction()
            .add(R.id.recommend_fragment_fl, fragment)
        transcation.commit()
    }
}
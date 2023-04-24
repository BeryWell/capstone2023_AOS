package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendQ3Binding

class RecommendQ3Fragment : Fragment() {
    private lateinit var binding: FragmentRecommendQ3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendQ3Binding.inflate(inflater, container, false)

        var result = ""

        setFragmentResultListener("q2Key"){requestKey, bundle ->
            result = bundle.getString("bundleKey")!!
            Log.d("lhj", "Q3: ${result}")
        }

        binding.recommendOneCl.setOnClickListener {
            result = "${result},내향적"
            setFragmentResult("q3Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            result = "${result},외향적"
            setFragmentResult("q3Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            result = "${result},외향적"
            setFragmentResult("q3Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }



        return binding.root
    }

}
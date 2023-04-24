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
import com.example.fitfume.databinding.FragmentRecommendQ4Binding

class RecommendQ4Fragment : Fragment() {
    private lateinit var binding: FragmentRecommendQ4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendQ4Binding.inflate(inflater, container, false)

        var result = ""

        setFragmentResultListener("q3Key"){requestKey, bundle ->
            result = bundle.getString("bundleKey")!!
            Log.d("lhj", "Q4: ${result}")
        }

        binding.recommendOneCl.setOnClickListener {
            result = "${result},10대"
            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            result = "${result},20대"
            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendThreeCl.setOnClickListener {
            result = "${result},30대"
            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendFourCl.setOnClickListener {
            result = "${result},40대"
            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        return binding.root
    }

}
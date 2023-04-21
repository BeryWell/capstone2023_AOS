package com.example.fitfume.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        return binding.root
    }

}
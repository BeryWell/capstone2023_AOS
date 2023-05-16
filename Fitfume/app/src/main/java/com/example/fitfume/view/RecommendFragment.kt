package com.example.fitfume.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scaleMatrix
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        val intent = Intent(requireActivity(), RecommendActivity::class.java)
        startActivity(intent)

        return binding.root
    }

}
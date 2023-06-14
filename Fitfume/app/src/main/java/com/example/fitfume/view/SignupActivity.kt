package com.example.fitfume.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.viewModels
import com.example.fitfume.R
import com.example.fitfume.databinding.ActivitySignupBinding
import com.example.fitfume.network.data.request.SignUpRequest
import com.example.fitfume.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        var gender = ""

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.signupVm = viewModel

        binding.loginSignUpBtn.setOnClickListener {
            val email = binding.loginEmailEt.text.toString()
            val password = binding.loginPwEt.text.toString()
            val username = binding.loginUsernameEt.text.toString()

            if (email != "" && password != "" && username != "" && gender != "") {
                Log.d("Loginlhj", "before signup: email : $email password : $password username : $username gender : $gender")
                viewModel.signup(SignUpRequest(email, gender, password, username))
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.signupGenderManCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "MALE"
                binding.signupGenderWomanCb.isChecked = false
                binding.signupGenderManCb.isChecked = true
            }
        }

        binding.signupGenderWomanCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "FeMALE"
                binding.signupGenderWomanCb.isChecked = true
                binding.signupGenderManCb.isChecked = false
            }
        }

    }
}
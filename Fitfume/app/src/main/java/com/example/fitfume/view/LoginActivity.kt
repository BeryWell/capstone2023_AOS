package com.example.fitfume.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.fitfume.R
import com.example.fitfume.databinding.ActivityLoginBinding
import com.example.fitfume.network.data.request.LoginRequest
import com.example.fitfume.viewmodel.UserViewModel
import com.example.fitfume.viewmodel.UserViewModel.Companion.accessToken

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.loginVm = viewModel

        binding.loginLoginBtn.setOnClickListener {
            val email = binding.loginEmailEt.text.toString().trim()
            val password = binding.loginPwEt.text.toString().trim()

            viewModel.login(LoginRequest(email, password))
        }

        viewModel.isLoginSuccess.observe(this, Observer {
            if(it != ""){
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
//                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }else{
//                Toast.makeText(this, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
            }
        })

        binding.loginSignUpBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.isLoginSuccess.removeObservers(this)
    }
}
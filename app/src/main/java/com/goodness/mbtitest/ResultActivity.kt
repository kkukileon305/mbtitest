package com.goodness.mbtitest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goodness.mbtitest.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
  private val binding: ActivityResultBinding by lazy { ActivityResultBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    val results = intent.getIntegerArrayListExtra("results")!!
    val resultTypes = listOf(
      listOf("E", "I"),
      listOf("N", "S"),
      listOf("T", "F"),
    )

    val result = resultTypes.mapIndexed { index, strings -> strings[results[index] - 1] }.joinToString("")

    val mbtiText = binding.tvMbtiText
    val mbti = binding.tvMbti

    mbtiText.text = when (result) {
      "INT" -> "외향적인 사람"
      else -> "싸이코패스"
    }
    mbti.text = result
    
    val retryBtn = binding.btnRetry
    retryBtn.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
      startActivity(intent)
    }

  }
}
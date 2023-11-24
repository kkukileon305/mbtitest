package com.goodness.mbtitest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.goodness.mbtitest.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

  private val binding: ActivityTestBinding by lazy { ActivityTestBinding.inflate(layoutInflater) }
  private val viewPager: ViewPager2 by lazy { binding.viewPager }

  val questionResults = QuestionResults()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    viewPager.adapter = ViewPageAdapter(this)
    viewPager.isUserInputEnabled = false
  }

  fun moveToQuestion() {
    if (viewPager.currentItem == 2) {
      val intent = Intent(this, ResultActivity::class.java)

      intent.putIntegerArrayListExtra("results", ArrayList(questionResults.results))
      startActivity(intent)
    } else {
      val nextItem = viewPager.currentItem + 1
      if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
        viewPager.setCurrentItem(nextItem, true)
      }
    }
  }
}

class QuestionResults {
  val results = mutableListOf<Int>()

  fun addResponse(response: List<Int>) {
    val mostValue = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    mostValue?.let { results.add(it) }
  }

}
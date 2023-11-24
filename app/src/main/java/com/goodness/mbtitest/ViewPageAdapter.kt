package com.goodness.mbtitest


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

  override fun createFragment(position: Int): Fragment {
    return QuestionFragment.newInstance(position)
  }

  override fun getItemCount(): Int {
    return 3
  }
}
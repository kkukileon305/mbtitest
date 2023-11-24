package com.goodness.mbtitest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.goodness.mbtitest.databinding.FragmentQuestionsBinding

class QuestionFragment : Fragment() {
  private var _binding: FragmentQuestionsBinding? = null
  private val binding get() = _binding!!

  private var questionType: Int = 0

  private val questionTitles = listOf(
    "외향형 - 내향형 (E-I)",
    "감각형 - 직관형 (N-S)",
    "사고형 - 감정형 (T-F)",
  )

  private val questionTexts = listOf(
    listOf("Q1. 데이트가 없는 주말에 나는", "Q2. 친구의 소개로 소개팅에 나온 나는", "Q3. 데이트 중 길에서 연인의 친구를 만난다면 나는"),
    listOf("Q1. 데이트 중 맛있어 보이는 밥집을 발견한 나는", "Q2. 오늘 본 영화를 궁금해하는 연인에게 나는", "Q3. 연인에게 줄 선물을 고르게 된 나는"),
    listOf("Q1. 연인과 사소한 일로 다퉜을 때 나는", "Q2. 축 처진 연인이 우울하다고 말했을 때 나는", "Q3. 힘들게 이벤트를 준비한 나를 신나게 할 연인의 칭찬은?"),
  )

  private val questionAnswers = listOf(
    listOf(
      listOf("단톡에 연락해서 친구들과 약속을 잡는다", "침대랑 하루 종일 물아일체가 된다"),
      listOf("먼저 말 걸면서 분위기를 띄운다", "말을 걸어올 때까지 기다리고 본다"),
      listOf("자연스럽게 웃으며 대한다", "무생물이 되어 조용히 있는다")
    ),
    listOf(
      listOf("간판에서 맛집의 기운이 느껴진다 맛집 각이야", "유명하고 리뷰도 많으니까 맛은 보장되어 있겠군"),
      listOf("좀비랑 싸우는데 주인공이 완전 멋져 보는 내내 소름 돋았어", "주인공이 좀비 바이러스가 퍼져서 치료하기 위한 영화야"),
      listOf("실용성은 없어도 예쁘고 기억에 남을 선물", "연인에게 요즘 가장 필요할 것 같은 선물")
    ),
    listOf(
      listOf("ㅇㅇ점은 꼭 고쳐줬으면 좋겠어 이렇게 하면 되잖아", "나!! 진짜!! 너무!!! 화났어!!!!!"),
      listOf("왜 우울해? 뭐 때문에 우울한 거야?", "5초 만에 감정이입 완료. 같이 글썽거린다"),
      listOf("고마워 요즘 바쁠 텐데 언제 이런 걸 생각했어?", "사랑해 최고야 나 완전 감동했어")
    ),
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // non-null 체크
    arguments?.let {
      questionType = it.getInt(ARG_QUESTION_VALUE)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
    val view = binding.root

    val questionTitle = binding.tvQTitle


    questionTitle.text = questionTitles[questionType]

    val containerLayout = binding.qContainer
    val qTitles = containerLayout.children.filterIsInstance<TextView>().toList().slice(1..3)
    val radioGroups = containerLayout.children.filterIsInstance<RadioGroup>().toList()

    qTitles.forEachIndexed { index, title ->
      title.text = questionTexts[questionType][index]
    }

    radioGroups.forEachIndexed { qIndex, radios ->
      radios.forEachIndexed { rIndex, radio ->
        (radio as RadioButton).text = questionAnswers[questionType][qIndex][rIndex]
      }
    }

    binding.btnNext.setOnClickListener {
      val isAllChecked = radioGroups.all { it.checkedRadioButtonId != -1 }

      if (isAllChecked) {
        val response = radioGroups.map { group ->
          val firstRadioBtn = group.children.filterIsInstance<RadioButton>().toList()[0]
          if (firstRadioBtn.isChecked) 1 else 2
        }

        (activity as? TestActivity)?.questionResults?.addResponse(response)
        (activity as? TestActivity)?.moveToQuestion()

      } else {
        Toast.makeText(context, "체크다해라", Toast.LENGTH_SHORT).show()
      }
    }

    return view
  }


  companion object {
    private const val ARG_QUESTION_VALUE = "questionType"

    fun newInstance(questionType: Int): QuestionFragment {
      val fragment = QuestionFragment()
      val args = Bundle()
      args.putInt(ARG_QUESTION_VALUE, questionType)
      fragment.arguments = args

      return fragment
    }
  }
}
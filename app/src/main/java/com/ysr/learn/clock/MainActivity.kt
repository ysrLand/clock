package com.ysr.learn.clock

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_keypad.view.*
import kotlin.random.Random

private val TAG = MainActivity::class.simpleName

class MainActivity : AppCompatActivity() {
    private var hour: Int = 0
    private var minute: Int = 0
    private var hourDegree: Float = 0f
    private var minuteDegree: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else {
            setContentView(R.layout.activity_main)
        }

        val hourContents = mutableListOf<String>().apply {
            addAll((1..9).map { i -> i.toString() }.toMutableList())
            add(0.toString())
            add("\u232b")
        }
        val hourListener = View.OnClickListener {
            if (it is TextView) {
                when (it.text) {
                    "\u232b" -> {
                        hourKeypadView.inputTextView.text =
                            hourKeypadView.inputTextView.text.dropLast(1)
                    }
                    else -> {
                        hourKeypadView.inputTextView.text =
                            StringBuilder(hourKeypadView.inputTextView.text).append(it.text)
                    }
                }
            }
        }
        hourKeypadView.setContent(hourContents, hourListener, 3)

        val minuteContents = mutableListOf<String>().apply {
            addAll((1..9).map { i -> i.toString() }.toMutableList())
            add(0.toString())
            add("\u232b")
            add(getString(android.R.string.ok))
        }
        val minuteListener = View.OnClickListener {
            if (it is TextView) {
                when (it.text) {
                    getString(android.R.string.ok) -> {
                        if (match()) {
                            hourKeypadView.clearInput()
                            minuteKeypadView.clearInput()
                            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show()
                            generate()
                        } else {
                            hourKeypadView.clearInput()
                            minuteKeypadView.clearInput()
                            Toast.makeText(this, R.string.wrong, Toast.LENGTH_SHORT).show()
                        }
                    }
                    "\u232b" -> {
                        minuteKeypadView.inputTextView.text =
                            minuteKeypadView.inputTextView.text.dropLast(1)
                    }
                    else -> {
                        minuteKeypadView.inputTextView.text =
                            StringBuilder(minuteKeypadView.inputTextView.text).append(it.text)
                    }
                }
            }
        }
        minuteKeypadView.setContent(minuteContents, minuteListener, 3)

        generate()
    }

    private fun generate() {
        val random = Random(System.currentTimeMillis())
        hour = random.nextInt(1, 13)
        minute = random.nextInt(0, 60)
        animateHour(hour, minute)
        animateMinute(minute)
        Log.e(TAG, "$hour : $minute")
    }

    private fun animateHour(h: Int, m: Int) {
        val toDegree = h * 30f + m * 0.5f
        val animation = RotateAnimation(
            hourDegree,
            toDegree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            1f
        )
        animation.duration = 1000
        animation.fillAfter = true
        hourView.startAnimation(animation)
        hourDegree = toDegree
    }

    private fun animateMinute(m: Int) {
        val toDegree = m * 6f
        val animation = RotateAnimation(
            minuteDegree,
            toDegree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            1f
        )
        animation.duration = 1000
        animation.fillAfter = true
        minuteView.startAnimation(animation)
        minuteDegree = toDegree
    }

    private fun match(): Boolean {
        val inputHour = hourKeypadView.inputText.toIntOrNull() ?: -1
        val inputMinute = minuteKeypadView.inputText.toIntOrNull() ?: -1
        return (hour == inputHour) && (minute == inputMinute)
    }
}
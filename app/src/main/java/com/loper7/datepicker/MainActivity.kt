package com.loper7.datepicker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.number_picker.NumberPicker
import com.loper7.datepicker.databinding.ActivityMainBinding
import java.util.Calendar

/**
 *@Author loper7
 *@Date 2021/9/15 16:20
 *@Description
 **/
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        picker.setTextBold(false)
//        picker.setSelectedTextBold(false)
        binding.picker.setOnDateTimeChangedListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
        }

        binding.btnCardDialog.setOnClickListener {
            startActivity(Intent(this, DatePickerExampleActivity::class.java))
        }
        binding.btnCustomLayout.setOnClickListener {
            startActivity(Intent(this, CustomLayoutActivity::class.java))
        }
        binding.btnGlobalization.setOnClickListener {
            startActivity(Intent(this, GlobalizationActivity::class.java))
        }

        binding.btnWeekDialog.setOnClickListener {
            startActivity(Intent(this, WeekPickerExampleActivity::class.java))
        }

        //picker is DateTimePicker
        binding.picker.setDisplayType(intArrayOf(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY,DateTimeConfig.HOUR,DateTimeConfig.MIN))
        binding.picker.getPicker(DateTimeConfig.MIN)?.apply {
            value=1
            maxValue=12
            minValue=1
            formatter = NumberPicker.Formatter { value: Int ->
                "${value*5}"
            }
        }
    }

}



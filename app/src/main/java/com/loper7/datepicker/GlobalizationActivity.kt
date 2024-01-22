package com.loper7.datepicker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.number_picker.NumberPicker
import com.loper7.datepicker.databinding.ActivityGlobalizationBinding

/**
 *
 * @CreateDate:     2021/1/12 11:10
 * @Description:
 * @Author:         LOPER7
 * @Email:          loper7@163.com
 */
class GlobalizationActivity: AppCompatActivity() {

    private lateinit var binding:ActivityGlobalizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.picker.setGlobal(DateTimeConfig.GLOBAL_US)
        binding.picker1.setGlobal(DateTimeConfig.GLOBAL_CHINA)
        binding.picker2.setGlobal(DateTimeConfig.GLOBAL_LOCAL)
        binding.picker2.setDisplayType(intArrayOf(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY))

        binding.picker1.setOnDateTimeChangedListener {
            Log.e("loper7",
                StringUtils.conversionTime(binding.picker1.getMillisecond(),"yyyy-MM-dd HH:mm:ss")
            )
        }

        var numberPicker:NumberPicker? = binding.picker.getPicker(DateTimeConfig.YEAR)


    }
}
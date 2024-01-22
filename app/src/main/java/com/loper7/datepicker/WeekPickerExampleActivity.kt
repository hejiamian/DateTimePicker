package com.loper7.datepicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.loper7.date_time_picker.dialog.CardWeekPickerDialog
import com.loper7.date_time_picker.number_picker.NumberPicker
import com.loper7.datepicker.databinding.ActivityWeekPickerExampleBinding

class WeekPickerExampleActivity : AppCompatActivity() {


    private var maxDate: Long = 0
    private var minDate: Long = 0
    private var defaultDate: Long = 0

    private lateinit var context: Context

    private lateinit var binding:ActivityWeekPickerExampleBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekPickerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this


        binding.tvMaxDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MAX DATE")
                .setDisplayType(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY)
                .setDefaultTime(maxDate)
                .setOnChoose {
                    maxDate = it
                    binding.tvMaxDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd")
                }.build().show()

        }


        binding.btnClearMax.setOnClickListener {
            maxDate = 0L
            binding.tvMaxDate.text = ""
        }

        binding.tvMinDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MIN DATE")
                .setDisplayType(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY)
                .setDefaultTime(minDate)
                .setOnChoose {
                    minDate = it
                    binding.tvMinDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd")
                }.build().show()
        }

        binding.btnClearMin.setOnClickListener {
            minDate = 0L
            binding.tvMinDate.text = ""
        }

        binding.tvDefaultDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET DEFAULT DATE")
                .setDefaultTime(defaultDate)
                .setDisplayType(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY)
                .setOnChoose {
                    defaultDate = it
                    binding.tvDefaultDate.text =
                        StringUtils.conversionTime(it, "yyyy-MM-dd")
                }.build().show()
        }

        binding.btnClearDefault.setOnClickListener {
            defaultDate = 0L
            binding.tvDefaultDate.text = ""
        }

        binding.btnCardDialogShow.setOnClickListener {

            var model = CardDatePickerDialog.CARD
            if (binding.radioModelCard.isChecked)
                model = CardDatePickerDialog.CARD
            if (binding.radioModelCube.isChecked)
                model = CardDatePickerDialog.CUBE
            if (binding.radioModelStack.isChecked)
                model = CardDatePickerDialog.STACK
            if (binding.radioModelCustom.isChecked)
                model = R.drawable.shape_bg_dialog_custom


            CardWeekPickerDialog.builder(context)
                .setTitle("WEEK PICKER")
                .setBackGroundModel(model)
                .setWrapSelectorWheel(false)
                .setDefaultMillisecond(defaultDate)
                .setStartMillisecond(minDate)
                .setEndMillisecond(maxDate)
                .setThemeColor(if (model == R.drawable.shape_bg_dialog_custom) Color.parseColor("#FF8000") else 0)
//                .setBackGroundModel(R.drawable.shape_bg_dialog_dark)
//                .setAssistColor(Color.parseColor("#DDFFFFFF"))
//                .setDividerColor(Color.parseColor("#222222"))
                .setFormatter {
                    NumberPicker.Formatter { value: Int ->
                        val weekData = it[value - 1].toFormatList("MM月dd日")
                        val str = "从${weekData.first()}  开始到  ${weekData.last()}结束"
                        str
                    }
                }
                .setOnChoose("选择") {weekData,formatValue ->
                    binding.btnCardDialogShow.text = formatValue
                }
                .setOnCancel("关闭") {
                }.build().show()
        }
    }
}

/**
 * 将时间戳集合格式化为指定日期格式的集合
 * @return MutableList<String> [2021-09-09,2021--09-10,...]
 */
internal fun MutableList<Long>.toFormatList(format: String = "yyyy-MM-dd"): MutableList<String> {
    val formatList = mutableListOf<String>()
    for (i in this) {
        formatList.add(StringUtils.conversionTime(i, format))
    }
    return formatList
}

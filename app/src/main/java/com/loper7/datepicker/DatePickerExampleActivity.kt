package com.loper7.datepicker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.loper7.datepicker.databinding.ActivityDatePickerExampleBinding

class DatePickerExampleActivity : AppCompatActivity() {


    private var maxDate: Long = 0
    private var minDate: Long = 0
    private var defaultDate: Long = 0

    private lateinit var context: Context
    private lateinit var binding:ActivityDatePickerExampleBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatePickerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this


        binding.tvMaxDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MAX DATE")
                .setDefaultTime(maxDate)
                .setOnChoose {
                    maxDate = it
                    binding.tvMaxDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
                }.build().show()
        }


        binding.btnClearMax.setOnClickListener {
            maxDate = 0L
            binding.tvMaxDate.text = ""
        }

        binding.tvMinDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MIN DATE")
                .setDefaultTime(minDate)
                .setOnChoose {
                    minDate = it
                    binding.tvMinDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
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
                .setOnChoose {
                    defaultDate = it
                    binding.tvDefaultDate.text =
                        StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
                }.build().show()
        }

        binding.btnClearDefault.setOnClickListener {
            defaultDate = 0L
            binding.tvDefaultDate.text = ""
        }

        binding.rgPickerLayout.setOnCheckedChangeListener { group, checkedId ->
            //自定义布局下防止页面出现不必要显示异常，禁止变更显示选择项
            binding.checkYear.isEnabled = checkedId == R.id.radioPickerDefault
            binding.checkMonth.isEnabled = checkedId == R.id.radioPickerDefault
            binding.checkDay.isEnabled = checkedId == R.id.radioPickerDefault
            binding.checkHour.isEnabled = checkedId == R.id.radioPickerDefault
            binding.checkMin.isEnabled = checkedId == R.id.radioPickerDefault
            binding.checkSecond.isEnabled = checkedId == R.id.radioPickerDefault
        }

        binding.btnCardDialogShow.setOnClickListener {
            var displayList: MutableList<Int>? = mutableListOf()
            if (binding.checkYear.isChecked)
                displayList?.add(DateTimeConfig.YEAR)
            if (binding.checkMonth.isChecked)
                displayList?.add(DateTimeConfig.MONTH)
            if (binding.checkDay.isChecked)
                displayList?.add(DateTimeConfig.DAY)
            if (binding.checkHour.isChecked)
                displayList?.add(DateTimeConfig.HOUR)
            if (binding.checkMin.isChecked)
                displayList?.add(DateTimeConfig.MIN)
            if (binding.checkSecond.isChecked)
                displayList?.add(DateTimeConfig.SECOND)


            var model = CardDatePickerDialog.CARD
            if (binding.radioModelCard.isChecked)
                model = CardDatePickerDialog.CARD
            if (binding.radioModelCube.isChecked)
                model = CardDatePickerDialog.CUBE
            if (binding.radioModelStack.isChecked)
                model = CardDatePickerDialog.STACK
            if (binding.radioModelCustom.isChecked)
                model = R.drawable.shape_bg_dialog_custom

            var pickerLayout = 0
            if (binding.radioPickerDefault.isChecked)
                pickerLayout = 0
            if (binding.radioPickerSegmentation.isChecked) {
                displayList = null
                pickerLayout = R.layout.layout_date_picker_segmentation
            }
            if (binding.radioPickerGrid.isChecked) {
                displayList = null
                pickerLayout = R.layout.layout_date_picker_grid
            }


            val dialog = CardDatePickerDialog.builder(context)
                .setTitle("DATE&TIME PICKER")
                .setDisplayType(displayList)
                .setBackGroundModel(model)
//                .setBackGroundModel(if(isDark) R.drawable.shape_bg_dialog_dark else R.drawable.shape_bg_dialog_light)
                .showBackNow(binding.checkBackNow.isChecked)
                .setMaxTime(maxDate)
                .setPickerLayout(pickerLayout)
                .setMinTime(minDate)
                .setDefaultTime(defaultDate)
                .setTouchHideable(true)
                .setChooseDateModel(DateTimeConfig.DATE_LUNAR)
                .setWrapSelectorWheel(false)
                .setThemeColor(if (model == R.drawable.shape_bg_dialog_custom) Color.parseColor("#FF8000") else 0)
//                .setAssistColor(Color.parseColor("#DDFFFFFF"))
//                .setDividerColor(Color.parseColor("#222222"))
                .showDateLabel(binding.checkUnitLabel.isChecked)
                .showFocusDateInfo(binding.checkDateInfo.isChecked)
                .setOnChoose("选择") {
                    binding.btnCardDialogShow.text = "${
                        StringUtils.conversionTime(
                            it,
                            "yyyy-MM-dd HH:mm:ss"
                        )
                    }    ${StringUtils.getWeek(it)}"
                }
                .setOnCancel("关闭") {
                }.build()
            dialog.show()
            //重点 需要在dialog show 方法后
            //得到 BottomSheetDialog 实体，设置其 isHideable 为 fasle
            (dialog as BottomSheetDialog).behavior.isHideable = false
        }
    }
}

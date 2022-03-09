package com.example.meteoauthentication.ui.home

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.meteoauthentication.R
import com.example.meteoauthentication.databinding.FragmentGrafBinding
import com.example.meteoauthentication.model.MeasuredValue
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "GrafFragment"

@AndroidEntryPoint
class GrafFragment : Fragment(R.layout.fragment_graf) {
    private lateinit var binding: FragmentGrafBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var arrayList: ArrayList<MeasuredValue> = arrayListOf()
    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_graf, container, false)
        binding = FragmentGrafBinding.bind(view)
        lineChart = binding.chart
        initLineChart()
        return view
    }

    fun setStationMeasuredValues(measuredValues: ArrayList<MeasuredValue>) {
        arrayList.clear()
        arrayList = measuredValues
        // setDataToLineChart(arrayList)//todo
        onSpinnerSelected(arrayList)
    }

    private fun initLineChart() {
        //   hide grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        //remove right y-axis
        lineChart.axisRight.isEnabled = false
        //remove legend
        lineChart.legend.isEnabled = false
        //remove description label
        lineChart.description.isEnabled = false
        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)
        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        //xAxis.labelRotationAngle = +90f
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        @RequiresApi(Build.VERSION_CODES.N)
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < arrayList.size) {
                formatDate(arrayList[index].measurement_time)
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart(arrayList: ArrayList<MeasuredValue>) {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in arrayList.indices) {
            val measuredValue = arrayList[i]
            entries.add(Entry(i.toFloat(), measuredValue.humidity.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.invalidate()
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun formatDate(time: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val date = dateFormat.parse(time)
        val formatter = SimpleDateFormat("hh:mm")
        val dateStr = formatter.format(date)
        return dateStr
    }

    private fun onSpinnerSelected(arrayList: ArrayList<MeasuredValue>) {
        val spinner: Spinner = binding.grafSpinner
        val nameOfMeasuredValue = ArrayList<String>()
        nameOfMeasuredValue.add("humidity")
        nameOfMeasuredValue.add("temperature")
        nameOfMeasuredValue.add("air quality")
        nameOfMeasuredValue.add("wind speed")
        nameOfMeasuredValue.add("wind gusts")
        nameOfMeasuredValue.add("wind direction")
        nameOfMeasuredValue.add("rainfall")

        val arrayAdapter = context?.let { it1 ->
            ArrayAdapter(
                it1,
                android.R.layout.simple_spinner_item,
                nameOfMeasuredValue
            )
        }
        arrayAdapter?.setDropDownViewResource(R.layout.spinner_item)

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //now draw bar chart with dynamic data
                    val entries: ArrayList<Entry> = ArrayList()

                    //you can replace this data object with  your custom object
                    when (position) {
                        0 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(Entry(i.toFloat(), measuredValue.humidity.toFloat()))
                            }
                        }
                        2 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(Entry(i.toFloat(), measuredValue.air_quality.toFloat()))
                            }
                        }
                        3 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(Entry(i.toFloat(), measuredValue.wind_speed.toFloat()))
                            }
                        }
                        4 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(Entry(i.toFloat(), measuredValue.wind_gusts.toFloat()))
                            }
                        }
                        5 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(
                                    Entry(i.toFloat(), measuredValue.wind_direction.toFloat())
                                )
                            }
                        }
                        6 -> {
                            for (i in arrayList.indices) {
                                val measuredValue = arrayList[i]
                                entries.add(Entry(i.toFloat(), measuredValue.rainfall.toFloat()))
                            }
                        }
                        else -> {}
                    }


                    val lineDataSet = LineDataSet(entries, "")
                    val data = LineData(lineDataSet)
                    lineChart.data = data
                    lineChart.invalidate()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

}
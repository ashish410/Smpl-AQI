package com.ash.smplaqi.ui.fragments.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ash.smplaqi.calculateTimeDifference
import com.ash.smplaqi.data.model.CityAqi
import com.ash.smplaqi.databinding.FragmentGraphBinding
import com.ash.smplaqi.roundTo
import com.ash.smplaqi.setAqiColor
import com.ash.smplaqi.ui.MainViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GraphFragment : Fragment() {

    private var binding: FragmentGraphBinding? = null
    private var lineData: BarData? = null
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.barChart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        binding?.barChart?.description?.isEnabled = false
        binding?.barChart?.legend?.isEnabled = false
        lifecycleScope.launch {
            mainViewModel.getSelectedCityData()?.collect {
                setLineChartData(it)
            }
        }
    }

    private fun setLineChartData(list: List<CityAqi>) {
        val entryList: ArrayList<BarEntry> = ArrayList()
        ArrayList(list).sortBy { it.seconds }
        val colorList = arrayListOf<Int>()
        list.forEach {
            colorList.add(ContextCompat.getColor(requireContext(), setAqiColor(it.aqi)))
            val seconds = it.seconds
            if (seconds != null) {
                entryList.add(
                    BarEntry(
                        calculateTimeDifference(seconds).toFloat(),
                        it.aqi.roundTo(2).toFloat()
                    )
                )
            }
        }
        val lineDataSet = BarDataSet(entryList, "city")
        lineDataSet.colors = colorList
        lineData = BarData(lineDataSet)
        binding?.barChart?.data = lineData
        binding?.barChart?.setVisibleXRangeMaximum(10f)
        binding?.barChart?.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}
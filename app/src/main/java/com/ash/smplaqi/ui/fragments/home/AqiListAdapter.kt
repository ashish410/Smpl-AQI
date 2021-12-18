package com.ash.smplaqi.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ash.smplaqi.data.model.CityAqi
import com.ash.smplaqi.databinding.ItemCityAqiBinding
import com.ash.smplaqi.mergeNewAndOldList
import com.ash.smplaqi.roundTo
import java.util.ArrayList

class AqiListAdapter(private val onCityClicked: (CityAqi) -> Unit) :
    RecyclerView.Adapter<AqiListAdapter.AqiListViewHolder>() {

    private val cityAqiList: ArrayList<CityAqi> = arrayListOf()

    fun updateList(dataList: List<CityAqi>) {
        mergeNewAndOldList(dataList, cityAqiList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AqiListViewHolder {
        val itemBinding =
            ItemCityAqiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AqiListViewHolder(itemBinding, onCityClicked)
    }

    override fun onBindViewHolder(holder: AqiListViewHolder, position: Int) {
        val aqiBean = cityAqiList[position]
        holder.bind(aqiBean)
    }

    override fun getItemCount() = cityAqiList.size

    inner class AqiListViewHolder(
        private val itemBinding: ItemCityAqiBinding,
        private val onCityClicked: (CityAqi) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cityAqi: CityAqi) {
            itemBinding.txtCity.text = cityAqi.city
            itemBinding.txtAqi.text = cityAqi.aqi.roundTo(2)
            itemBinding.txtLastUpdated.text = cityAqi.lastUpdated
            val aqiColor = cityAqi.aqiColor
            if (aqiColor != null)
                itemBinding.view2.setBackgroundColor(
                    ContextCompat.getColor(
                        itemBinding.root.context,
                        aqiColor
                    )
                )
            itemView.setOnClickListener { onCityClicked(cityAqi) }
        }
    }
}


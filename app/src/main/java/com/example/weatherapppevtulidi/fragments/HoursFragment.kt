package com.example.weatherapppevtulidi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapppevtulidi.MainViewModel
import com.example.weatherapppevtulidi.adapters.WeatherAdapter
import com.example.weatherapppevtulidi.adapters.WeatherModel
import com.example.weatherapppevtulidi.databinding.FragmentHoursBinding
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner){
            adapter.submitList(getHoursList(it))
        }
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter(listener = null)
        rcView.adapter = adapter

    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel>{
        val hoursArray = JSONArray(wItem.hours)
        val list = ArrayList<WeatherModel>()
        for(i in 0 until hoursArray.length()){
            val item = WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time").removeRange(0, 10),
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c")+"°C",
                "",
                "",
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}

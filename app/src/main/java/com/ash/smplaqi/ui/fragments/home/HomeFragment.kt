package com.ash.smplaqi.ui.fragments.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ash.smplaqi.R
import com.ash.smplaqi.data.model.CityAqi
import com.ash.smplaqi.databinding.FragmentHomeBinding
import com.ash.smplaqi.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mConnectivityManager: ConnectivityManager
    private val mAqiListAdapter = AqiListAdapter { onCityClicked(it) }

    private fun onCityClicked(cityAqi: CityAqi) {
        mainViewModel.selectedCityLiveData.value = cityAqi.city
        findNavController().navigate(R.id.action_homeFragment_to_graphFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mConnectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.connect()

        setUpRecyclerView()

        loadData()
    }

    private fun loadData() {
        mBinding?.progressBar?.visibility = View.VISIBLE
        lifecycleScope.launch {
            mainViewModel.getLatestData().collect {
                mBinding?.progressBar?.visibility = View.GONE
                mBinding?.aqiRecyclerView?.visibility = View.VISIBLE
                mAqiListAdapter.updateList(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        mBinding?.aqiRecyclerView?.let {
            it.adapter = mAqiListAdapter
            it.layoutManager = linearLayoutManager
            (it.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mBinding = null
    }


    override fun onResume() {
        super.onResume()
        mConnectivityManager.registerDefaultNetworkCallback(networkListener)
    }

    override fun onPause() {
        super.onPause()
        mConnectivityManager.unregisterNetworkCallback(networkListener)
        mainViewModel.cancel()
    }

    private val networkListener = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            requireActivity().runOnUiThread {
                mainViewModel.connect()
                loadData()
            }
        }

        override fun onLost(network: Network) {
            requireActivity().runOnUiThread {

            }
        }
    }
}
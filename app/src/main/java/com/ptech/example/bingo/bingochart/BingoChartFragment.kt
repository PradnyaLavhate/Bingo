package com.ptech.example.bingo.bingochart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.ptech.example.bingo.R
import com.ptech.example.bingo.databinding.FragmentBingoChartBinding
import com.ptech.example.bingo.di.DaggerBingoChartComponent
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [BingoChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BingoChartFragment : Fragment() {


    @Inject
    lateinit var viewModel: BingoChartViewModel

    init {
        DaggerBingoChartComponent
            .builder()
            .build()
            .injectFor(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding: FragmentBingoChartBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_bingo_chart,
                container,
                false
            )
        dataBinding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
        dataBinding.bingoChartViewModel = viewModel
        return dataBinding.root
    }

}
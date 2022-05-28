package com.ptech.example.bingo.header

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ptech.example.bingo.R
import com.ptech.example.bingo.databinding.FragmentHeaderBinding
import com.ptech.example.bingo.di.DaggerBingoChartComponent
import javax.inject.Inject

class HeaderFragment : Fragment() {

    @Inject
    lateinit var viewModel: HeaderViewModel

    init {
        DaggerBingoChartComponent
            .builder()
            .build()
            .injectDataFor(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_header, container, false)
        dataBinding.lifecycleOwner = this
        (dataBinding as FragmentHeaderBinding).viewModel = viewModel
        return dataBinding.root
    }

}
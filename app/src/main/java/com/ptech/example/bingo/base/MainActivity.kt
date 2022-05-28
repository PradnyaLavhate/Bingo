package com.ptech.example.bingo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ptech.example.bingo.bingochart.BingoChartFragment
import com.ptech.example.bingo.R
import com.ptech.example.bingo.header.HeaderFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_header, getHeaderFragment())
            .replace(R.id.layout_bingo_charts, BingoChartFragment())
            .commit() // TODO -> change to nav grapgh
    }

    private fun getHeaderFragment() : HeaderFragment{

        val headerFragment = HeaderFragment()
        // TODO       headerFragment.viewModel
        return headerFragment
    }
}
package com.ptech.example.bingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ptech.example.bingo.bingochart.BingoChartItemVM
import com.ptech.example.bingo.databinding.ItemBingoChartElementBinding
import javax.inject.Inject

class BingoChartAdapter @Inject constructor(): RecyclerView.Adapter<BingoChartViewHolder>() {

    var bingoList = mutableListOf<BingoChartItemVM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BingoChartViewHolder {
       return LayoutInflater
           .from(parent.context)
           .let { ItemBingoChartElementBinding.inflate(it,parent,false) }
           .let(::BingoChartViewHolder)
    }

    override fun onBindViewHolder(holder: BingoChartViewHolder, position: Int) {
        (holder).bind(bingoList.get(position))
    }

    override fun getItemCount(): Int {
        return bingoList.size
    }
}

class BingoChartViewHolder(private val itemBingoChartElementBinding: ItemBingoChartElementBinding) :
    RecyclerView.ViewHolder(itemBingoChartElementBinding.root) {

    fun bind(viewModal :BingoChartItemVM) {
        itemBingoChartElementBinding.viewModal = viewModal
        itemBingoChartElementBinding.executePendingBindings()
    }

}
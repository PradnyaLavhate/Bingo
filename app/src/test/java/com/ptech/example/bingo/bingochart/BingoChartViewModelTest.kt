package com.ptech.example.bingo.bingochart

import com.ptech.example.bingo.adapter.BingoChartAdapter
import com.ptech.example.bingo.util.BingoEventManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class BingoChartViewModelTest {

    lateinit var subject : BingoChartViewModel

    val bingoFactory = mockk<BingoChartData.BingoChartFactory>(){
        every { newInstance() } returns mockk<BingoChartData>()
    }
    val bingoEventManager= mockk<BingoEventManager>()
    val bingoChartAdapter = mockk<BingoChartAdapter>()


    @Before
    fun setUp() {
        subject = BingoChartViewModel(bingoFactory,bingoEventManager,bingoChartAdapter)
    }

    @Test
    fun checkForBingoOnCheckedElements_firstRowHaveBingo_returnsTrue(){
        val list =  Array(25) {
            it < 5
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_secondRowHaveBingo_returnsTrue(){
        val list =  Array(25) {
            it in 5..9
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(true, result)
    }

    @Test
    fun checkForBingoOnCheckedElements_thirdRowHaveBingo_returnsTrue(){
        val list =  Array(25) {
            it in 10..14
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(true, result)
    }

    @Test
    fun checkForBingoOnCheckedElements_fourthRowHaveBingo_returnsTrue(){
        val list =  Array(25) {
            it in 15..19
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(true, result)
    }

    @Test
    fun checkForBingoOnCheckedElements_fifthRowHaveBingo_returnsTrue(){
        val list =  Array(25) {
            it in 20..24
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(true, result)
    }


    @Test
    fun checkForBingoOnCheckedElements_firstRowHaveBingoWithMoreCanceledElements_returnsTrue(){
        val list =  Array(25) {
            it in 0..5 || it in 8..9
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_firstAndSecondRowHaveCanceledElements_returnsFalse(){
        val list =  Array(25) {
            it in 0..1 || it in 8..9
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,false)
    }

    @Test
    fun checkForBingoOnCheckedElements_secondAndThirdRowHaveCanceledElements_returnsFalse(){
        val list =  Array(25) {
            it in 8..9 || it in 11..12
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,false)
    }

    //column tests
    @Test
    fun checkForBingoOnCheckedElements_firstColumnHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(0,5,10,15,20).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_secondColumnHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(1,6,11,16,21).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_thirdColumnHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(2,7,12,17,22).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_fourthColumnHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(3,8,13,18,23).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_fifthColumnHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(4,9,14,19,24).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_firthColumnAndFewMoreHaveCanceledElements_returnsTrue(){
        val list =  Array(25) {
            listOf(0,3,5,10,13,15,20,24).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,true)
    }

    @Test
    fun checkForBingoOnCheckedElements_columnsDoNotHaveBingo_returnsFalse(){
        val list =  Array(25) {
            listOf(0,3,5,13,15,20,24).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(list)

        assertEquals(result,false)
    }

    @Test
    fun checkForBingoOnCheckedElements_leftToRightDiagonalBingo_returnsTrue(){
        val listOfMarkedElements = Array(25){
            listOf(0,6,12,18,24).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(listOfMarkedElements)

        assertEquals(result, true)
    }

    @Test
    fun checkForBingoOnCheckedElements_rightToLeftDiagonalBingo_returnsTrue(){
        val listOfMarkedElements = Array(25){
            listOf(4,8,12,16,20).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(listOfMarkedElements)

        assertEquals(result, true)
    }

    @Test
    fun checkForBingoOnCheckedElements_almostRightToLeftDiagonalBingo_returnsFalse(){
        val listOfMarkedElements = Array(25){
            listOf(4,8,13,16,20).contains(it)
        }.toList()

        val result = subject.checkForBingoOnCheckedElements(listOfMarkedElements)

        assertEquals(result, false)
    }
}
package com.example.tipcalculator
import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.NumberFormat

class TipCalcTests {
    @Test
    fun calculateTip_20percentTest(){
        val amount = 10.0
        val percent = 20.0
        val member = 6
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = tipCalc(amount = amount, tipPercent = percent, false, member = member)
        assertEquals(expectedTip, actualTip)

    }
    @Test
    fun calculateSplit_20percentTest(){
        val amount= 10.0
        val percent = 20.0
        val member = 6
        val expectedTipSplitAmount = NumberFormat.getCurrencyInstance().format(12.0/6)
        val actualSplit = billCalc(amount = amount, tipPercent = percent, roundUp = false, member=member)
        assertEquals(expectedTipSplitAmount, actualSplit)

    }
}
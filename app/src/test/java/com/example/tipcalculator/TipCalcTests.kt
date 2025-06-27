package com.example.tipcalculator
import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.NumberFormat

class TipCalcTests {

    @Test
    fun calculateTip_20percentTest(){
        val amount: Double = 10.0
        val percent: Double = 20.0
        val member: Int = 6
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = tipCalc(amount = amount, tipPercent = percent, false, member = member)
        assertEquals(expectedTip, actualTip)
        val expectedTipSplitAmount = NumberFormat.getCurrencyInstance().format(2);
        val actualSplit = billCalc(amount = amount, tipPercent = percent, roundUp = false, member=member)
        assertEquals(expectedTipSplitAmount, actualSplit)

    }
}
package com.example.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipCalcUITest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip(){
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalTheme()
            }
        }
        composeTestRule.onNodeWithText("Bill Amount").assertExists().performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").assertExists().performTextInput("20")
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText(expectedTip, substring = true)
            .assertExists("Expected tip value not found in UI")
    }

    @Test
    fun calculate_20_percent_tip_split_amount(){
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalTheme()
            }
        }
        composeTestRule.onNodeWithText("Bill Amount").assertExists().performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").assertExists().performTextInput("20")
        composeTestRule.onNodeWithText("Split Member").assertExists().performTextInput("6")
        val expectedTipSplitAmount = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText(expectedTipSplitAmount, substring = true)
            .assertExists("Expected split amount not found in UI")
    }
}
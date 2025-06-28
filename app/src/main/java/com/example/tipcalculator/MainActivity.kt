package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {

                    TipCalTheme()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalTheme() {
    var amountInput by remember {mutableStateOf("")}
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tipInput by remember { mutableStateOf("") }
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    var roundUp by remember { mutableStateOf(false) }
    var memberInput by remember { mutableStateOf("") }
    val member = memberInput.toIntOrNull() ?: 1
    val tip = tipCalc(amount, tipPercent,roundUp, member)
    val splitAmount = billCalc(amount, tipPercent,roundUp, member)

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFFFFD54F) // Soft amber
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF4A148C), // Dark Purple
                    titleContentColor = Color(0xFFFFD54F) // Soft amber
                )
            )
        }
    ) {innerPadding->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFDF6EC), Color(0xFFE0F7FA))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 30.dp)
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .safeDrawingPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EditNumberField(
                    color = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFF6A1B9A),
                        containerColor = Color(0xFFFFF8E1),
                        cursorColor = Color(0xFF6A1B9A),
                        focusedLabelColor = Color(0xFF6A1B9A),
                        unfocusedLabelColor = Color(0xFF4A148C),
                    ),
                    label = R.string.bill_amount,
                    leadingIcon = R.drawable.money,
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditNumberField(
                    color = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFF6A1B9A),
                        containerColor = Color(0xFFFFF8E1),
                        cursorColor = Color(0xFF6A1B9A),
                        focusedLabelColor = Color(0xFF6A1B9A),
                        unfocusedLabelColor = Color(0xFF4A148C),
                        focusedLeadingIconColor = Color(0xFF6A1B9A),
                        unfocusedLeadingIconColor = Color(0xFF4A148C),
                    ),
                    label = R.string.tip_percentage,
                    leadingIcon = R.drawable.percent,
                    value = tipInput,
                    onValueChange = { tipInput = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                EditNumberField(
                    color = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFF6A1B9A),
                        containerColor = Color(0xFFFFF8E1),
                        cursorColor = Color(0xFF6A1B9A),
                        focusedLabelColor = Color(0xFF6A1B9A),
                        unfocusedLabelColor = Color(0xFF4A148C),
                    ),
                    label = R.string.split_Number,
                    leadingIcon = R.drawable.money,
                    value = memberInput,
                    onValueChange = { memberInput = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)

                )
                Spacer(modifier = Modifier.height(5.dp))

                TextField(
                    value = tip,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .padding(20.dp)
                        .height(70.dp),
                    label = { Text(stringResource(R.string.tip_amount)) },
                    textStyle = MaterialTheme.typography.displaySmall.copy(color = Color(0xFF1B5E20)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFE8F5E9),
                        focusedIndicatorColor = Color(0xFF6A1B9A),
                        cursorColor = Color(0xFF6A1B9A),
                        focusedLabelColor = Color(0xFF6A1B9A),
                        unfocusedLabelColor = Color(0xFF4A148C),
                    )
                )


                TextField(
                    value = splitAmount,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .padding(20.dp)
                        .height(70.dp),
                    label = { Text(stringResource(R.string.split_amount)) },
                    textStyle = MaterialTheme.typography.displaySmall.copy(color = Color(0xFF0D47A1)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFF6A1B9A),
                        containerColor = Color(0xFFE3F2FD),
                        cursorColor = Color(0xFF6A1B9A),
                        focusedLabelColor = Color(0xFF6A1B9A),
                        unfocusedLabelColor = Color(0xFF4A148C),
                    )

                )


                RoundTheTip(
                    roundUp = roundUp,
                    onRoundUpChanged = { roundUp = it },
                    modifier = Modifier
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    color: TextFieldColors,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = null) },
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        colors = color,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4A148C))
    )
}

@Composable
fun RoundTheTip(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.round_up_tip),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6A1B9A)),
            modifier = Modifier.weight(1f)
        )
        Switch(
            modifier = Modifier.padding(end = 16.dp),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
        )
    }
}

@VisibleForTesting
internal fun tipCalc(amount: Double, tipPercent: Double, roundUp: Boolean, member: Int): String{
    val tip = tipPercent/100 * amount
    var split = tip/member
    if(roundUp){
        split = kotlin.math.ceil(split)
    }
    return NumberFormat.getCurrencyInstance().format(split)
}

@VisibleForTesting
internal fun billCalc(amount: Double, tipPercent: Double, roundUp: Boolean, member: Int): String {
    val tip = amount * tipPercent / 100
    val totalPerPerson = (amount + tip) / member
    val finalAmount = if (roundUp) kotlin.math.ceil(totalPerPerson) else totalPerPerson
    return NumberFormat.getCurrencyInstance().format(finalAmount)
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {

            TipCalTheme()

    }
}
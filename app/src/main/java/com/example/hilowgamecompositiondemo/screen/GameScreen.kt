@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hilowgamecompositiondemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hilowgamecompositiondemo.R
import com.example.hilowgamecompositiondemo.ui.theme.HiLowGameCompositionDemoTheme
import kotlin.random.Random


@Composable
fun GameScreen() {

    val context = LocalContext.current

    var generatedNum by rememberSaveable { mutableStateOf(Random(System.currentTimeMillis()).nextInt(11)) }

    var numberText by rememberSaveable {
        mutableStateOf("2")
    }
    var textResult by rememberSaveable {
        mutableStateOf("-")
    }

    var inputErrorState by rememberSaveable {
        mutableStateOf(false)
    }

    var showWinDialog by rememberSaveable {
        mutableStateOf(false)
    }

    fun validate(text:String) {
        val allDigit = text.all { char -> char.isDigit() }
        textResult = context.getString(R.string.error_empty)
        inputErrorState = !allDigit
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text= stringResource(R.string.text_enter_guess))},
            value = numberText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal ) ,
            onValueChange = {
                numberText = it
                validate(numberText)
            },
            isError = inputErrorState, trailingIcon = {
                if(inputErrorState) {
                    Icon(Icons.Filled.Warning,"error",tint = MaterialTheme.colorScheme.error)
                }
            })

        Button(onClick = {
            try {
                val myNum = numberText.toInt()

                if (myNum == generatedNum) {
                    textResult = "You have won!"
                    showWinDialog = true
                } else if (myNum < generatedNum) {
                    textResult = "The number is larger"
                } else  {
                    textResult = "The number is smaller"
                }
            } catch(e:Exception) {
                textResult = e.message.toString()
            }

        },
            enabled = !inputErrorState) {
            Text(text = "Guess")
        }

        Text(text=textResult,
            fontSize=28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Blue
        )

        SimpleAlertDialog(
            show = showWinDialog,
            onDismiss = {showWinDialog = false},
            onConfirm = {showWinDialog = false}
        )

        Button(onClick = {
            generatedNum = Random(System.currentTimeMillis()).nextInt(11)
        }) {
            Text(text = "Restart")
        }
    }
}

@Composable fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
)
{
    if(show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text="Congratulatinos!") },
            text = { Text(text="You have fun!") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                Text (text="OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text (text="Cancel")
                }
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HiLowGameCompositionDemoTheme {
        GameScreen()
    }
}

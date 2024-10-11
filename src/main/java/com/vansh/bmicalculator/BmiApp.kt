package com.vansh.bmicalculator

import android.content.Context
import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BmiApp(context: Context){
    var weight by rememberSaveable {
        mutableStateOf("")
    }
    var height by rememberSaveable {
        mutableStateOf("")
    }
    var result by rememberSaveable {
        mutableStateOf("")
    }

    var h = 0.0
    var w = 0.0
    var report by rememberSaveable {
        mutableStateOf("")
    }

    if(height.isEmpty() && weight.isEmpty()){
        height = " "
        weight = " "
    }
    else{
        h = height.toDoubleOrNull() ?: 0.0
        w = weight.toDoubleOrNull() ?: 0.0
        h/=100
    }

    // For navigating through textFields
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    Box(
       modifier = Modifier
           .fillMaxSize()
           .background(Color(139, 124, 167, 255)),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "BMI Calculator" ,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(150.dp))
            OutlinedTextField(
                value = height,
                onValueChange = {
                    height = it
                },
                singleLine = true,
                label = {
                    Text(
                        text = "Enter height in cm",
                        fontSize = 30.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 26.sp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight =it
                },
                singleLine = true,
                label = {
                    Text(
                        text = "Enter weight in kg",
                        fontSize = 30.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 26.sp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        Toast.makeText(
                            context,
                            "Data Submitted",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                result = calculateBMI(w,h).toString()
                report = bmiReport(result.toDouble())
            }) {
                Text(
                    text = "Calculate",
                    fontSize = 25.sp
                )
            }
            Spacer(modifier = Modifier.height(70.dp))
            Row {
                Text(
                    text = "Result : $result",
                    fontSize = 35.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = report,
                    fontSize = 35.sp,
                    color = Color.White,
                )
            }
        }
    }
}

fun Double.toFixed(decimals: Int): String = "%.${decimals}f".format(this)

fun calculateBMI(w: Double, h: Double): Double {
    var res: Double = w/(h*h)
    res = res.toFixed(2).toDouble()
    return res
}

/*
    Underweight: BMI less than 18.5
    Normal weight: BMI between 18.5 and 24.9
    Overweight: BMI between 25 and 29.9
    Obesity Class 1: BMI between 30 and 34.9
    Obesity Class 2: BMI between 35 and 39.9
    Obesity Class 3 (Severe obesity): BMI 40 and above
 */
fun bmiReport(res: Double): String{
    return if(res < 18.5){
        "Underweight"
    }else if(res > 18.5 && res < 24.9){
        "Normal weight"
    }else if(res > 25 && res < 29.9){
        "Overweight"
    }else if (res > 30 && res < 34.9){
        "Obesity Class 1"
    }else if (res > 35 && res < 39.9){
        "Obesity Class 2"
    }else{
        "Obesity Class 3 (Severe obesity)"
    }
}


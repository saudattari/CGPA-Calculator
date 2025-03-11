package com.example.cgpacalculator.Screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cgpacalculator.DataModel.SemesterData
import com.example.cgpacalculator.ui.theme.MainColor
import java.util.Locale
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllUniCGPA(navController: NavController) {
    var sem1Gpa by remember { mutableStateOf("") }
    var sem2Gpa by remember { mutableStateOf("") }
    var sem3Gpa by remember { mutableStateOf("") }
    var sem4Gpa by remember { mutableStateOf("") }
    var sem5Gpa by remember { mutableStateOf("") }
    var sem6Gpa by remember { mutableStateOf("") }
    var sem7Gpa by remember { mutableStateOf("") }
    var sem8Gpa by remember { mutableStateOf("") }
    var cgpaResult by remember { mutableDoubleStateOf(0.0) }
    var sem1CreditHours by remember { mutableStateOf("") }
    var sem2CreditHours by remember { mutableStateOf("") }
    var sem3CreditHours by remember { mutableStateOf("") }
    var sem4CreditHours by remember { mutableStateOf("") }
    var sem5CreditHours by remember { mutableStateOf("") }
    var sem6CreditHours by remember { mutableStateOf("") }
    var sem7CreditHours by remember { mutableStateOf("") }
    var sem8CreditHours by remember { mutableStateOf("") }

    val listData = remember { MutableList(8) {i-> SemesterData((i+1).toString(), 0.0,0) } }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculate CGPA For All Universities", color = Color.Black, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        shareCGPA1(context, cgpaResult, listData)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = MainColor)
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Warning2()
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(listData) { semester ->
                    val gpaValue = when (semester.semester) {
                        "1" -> sem1Gpa
                        "2" -> sem2Gpa
                        "3" -> sem3Gpa
                        "4" -> sem4Gpa
                        "5" -> sem5Gpa
                        "6" -> sem6Gpa
                        "7" -> sem7Gpa
                        "8" -> sem8Gpa
                        else -> ""
                    }
                    val CreditHours = when(semester.semester){
                        "1" -> sem1CreditHours
                        "2" -> sem2CreditHours
                        "3" -> sem3CreditHours
                        "4" -> sem4CreditHours
                        "5" -> sem5CreditHours
                        "6" -> sem6CreditHours
                        "7" -> sem7CreditHours
                        "8" -> sem8CreditHours
                        else -> ""
                    }
                    ChooseSemester(semester, gpaValue,CreditHours,
                        onGpaChanged = { gpa, semesterNo ->
                        when(semesterNo.semester){
                            "1"->{ sem1Gpa = gpa }
                            "2"->{sem2Gpa = gpa }
                            "3"->{sem3Gpa = gpa }
                            "4"->{sem4Gpa = gpa }
                            "5"->{sem5Gpa = gpa }
                            "6"->{sem6Gpa = gpa }
                            "7"->{sem7Gpa = gpa }
                            "8"->{sem8Gpa = gpa }
                        }
                        semester.gpa = gpa.toDoubleOrNull() ?: 0.0
                    },
                        onCreditHourChanged = {credit, semesterNo->
                            when(semesterNo.semester){
                                "1"->{ sem1CreditHours = credit }
                                "2"->{ sem2CreditHours = credit }
                                "3"->{ sem3CreditHours = credit }
                                "4"->{ sem4CreditHours = credit }
                                "5"->{ sem5CreditHours = credit }
                                "6"->{ sem6CreditHours = credit }
                                "7"->{ sem7CreditHours = credit }
                                "8"->{ sem8CreditHours = credit }
                            }
                            semester.creditHours = credit.toIntOrNull() ?: 0
                    })
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CGPA",
                            color = Color(0xFFA2A4A4),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = String.format(Locale.US,"%.2f", cgpaResult),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    ) {}
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(
                        onClick = {
                            cgpaResult = calculateCgpa1(listData)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainColor)
                    ) {
                        Text(
                            text = "Calculate CGPA",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    )
}

fun calculateCgpa1(listData: List<SemesterData>,) : Double{
    var totalPoints = 0.0
    var totalCredits = 0
    listData.forEach {
        if (it.gpa in 0.01..4.0) {
            totalPoints += it.gpa * it.creditHours
            totalCredits += it.creditHours
        }
    }
    return if (totalCredits > 0) totalPoints / totalCredits else 0.0
}

@Preview
@Composable
fun Warning2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3F5F7))
            .padding(12.dp)
    ) {
        Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF9A9C9E))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "You have to enter your gpa and Each Semester Total Credit Hour (Credit Hours not defined by default)",
            color = Color(0xFF9A9C9E),
            fontSize = 12.sp
        )
    }
}

fun shareCGPA1(context: Context, cgpaResult: Double, listData: MutableList<SemesterData>) {
    var finalResultString = StringBuilder()
    finalResultString.append("CGPA Grades Result\n")
    listData.forEach { semester->
        if (semester.gpa > 0.0){
            finalResultString.append("Semester ${semester.semester}:   ${String.format(locale = Locale.US, "%.2f", semester.gpa)}\n")
        }
    }
    finalResultString.append("------------------\n")
//    finalResultString.append("CGPA: $cgpaResult")
    if(cgpaResult > 0){ finalResultString.append("CGPA:   ${String.format(Locale.US, "%.2f", cgpaResult)}") } else{finalResultString.append("CGPA not calculated yet")}

//    share with User
    val sendIntent =Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, finalResultString.toString())
        type = "text/plain"
    }
    try {
        context.startActivity(Intent.createChooser(sendIntent,"Share CGPA"))
    }
    catch(e:Exception) {
        Toast.makeText(context, "Something went wrong! Please try Again", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun ChooseSemester(
    semester: SemesterData,
    gpaValue: String,
    creditHour: String,
    onGpaChanged: (String, SemesterData) -> Unit,
    onCreditHourChanged: (String, SemesterData) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(8.dp))
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Semester ${semester.semester}",
            color = Color(0xFFA2A4A4),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
        OutlinedTextField(
            value = gpaValue,
            onValueChange = { onGpaChanged(it,semester) },
            placeholder = { Text("GPA", color = Color(0xFFA2A4A4), fontSize = 16.sp) },
            maxLines = 1,
            modifier = Modifier
                .width(130.dp)
                .padding(start = 8.dp)
,            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD5D5D5),
                focusedBorderColor = Color(0xFFD5D5D5)
            )
        )
        OutlinedTextField(
            value = creditHour,
            onValueChange = {
                onCreditHourChanged(it,semester)
            },
            placeholder = { Text("Credit Hour", color = Color(0xFFA2A4A4), fontSize = 12.sp) },
            maxLines = 1,
            modifier = Modifier
                .width(180.dp),
                colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD5D5D5),
                focusedBorderColor = Color(0xFFD5D5D5)
            )
        )


    }
}
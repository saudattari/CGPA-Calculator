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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import com.example.cgpacalculator.DataModel.SemesterData
import com.example.cgpacalculator.ui.theme.MainColor
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateCgpa(batch: String, navController: NavHostController) {
    var sem1Gpa by remember { mutableStateOf("") }
    var sem2Gpa by remember { mutableStateOf("") }
    var sem3Gpa by remember { mutableStateOf("") }
    var sem4Gpa by remember { mutableStateOf("") }
    var sem5Gpa by remember { mutableStateOf("") }
    var sem6Gpa by remember { mutableStateOf("") }
    var sem7Gpa by remember { mutableStateOf("") }
    var sem8Gpa by remember { mutableStateOf("") }
    val context = LocalContext.current
    var cgpaResult by remember { mutableDoubleStateOf(0.0) }
    var index = when(batch){
        "2018-22"->{0}
        "2019-23"->{1}
        "2020-24"->{2}
        "2021-25"->{3}
        "2022-26"->{4}
        "2023-27"->{5}
        "2024-28"->{6}
        else -> 0
    }
    val creditHours = listOf(
        listOf(16,17,17,16,16,17,14,17),
        listOf(16,17,17,16,16,17,14,17),
        listOf(17,19,21,17,16,16,15,12),
        listOf(18,19,22,17,17,16,16,12),
        listOf(18,19,21,18,17,16,15,13),
        listOf(16,17,19,17,18,18,17,11),
        listOf(18,19,22,17,17,16,16,12),
    )
    val listData = remember {
        MutableList(8){i->
            SemesterData((i+1).toString(), 0.0,creditHours[index][i])
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculate CGPA - Batch $batch", color = Color.Black, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
//                        Toast.makeText(context, "Feature not available yet (Testing)", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
//                        Toast.makeText(context, "Feature not available yet (Testing)", Toast.LENGTH_SHORT).show()
                        ShareCGPA(context, cgpaResult, listData,batch)
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
                    Warning()
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
                    ChooseSemester(semester, gpaValue) { gpa, semesterNo ->
                        when(semesterNo.semester){
                            "1"->{sem1Gpa = gpa}
                            "2"->{sem2Gpa = gpa}
                            "3"->{sem3Gpa = gpa}
                            "4"->{sem4Gpa = gpa}
                            "5"->{sem5Gpa = gpa}
                            "6"->{sem6Gpa = gpa}
                            "7"->{sem7Gpa = gpa}
                            "8"->{sem8Gpa = gpa}
                        }
                        semester.gpa = gpa.toDoubleOrNull() ?: 0.0
                    }
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
                            cgpaResult = calculateCumulativeGpa(
                                listData,
                                creditHourList = creditHours[index],
//                                index = counts
                            )
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

fun ShareCGPA(context: Context, cgpaResult: Double, listData: MutableList<SemesterData>,batch: String) {
    var finalResultString = StringBuilder()
    finalResultString.append("CGPA Result - Batch $batch\n")
    listData.forEach { semester->
        if (semester.gpa > 0.0){
            finalResultString.append("Semester ${semester.semester}: ${String.format(locale = Locale.US, "%.2f", semester.gpa)}\n")
        }
    }
//    finalResultString.append("CGPA: $cgpaResult")
    if(cgpaResult > 0){ finalResultString.append("CGPA: ${String.format(Locale.US, "%.2f", cgpaResult)}") } else{finalResultString.append("CGPA not calculated yet")}

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

fun calculateCumulativeGpa(data: List<SemesterData>, creditHourList: List<Int>): Double {
    var totalPoints = 0.0
    var totalCredits = 0
        data.forEachIndexed {i,semester->
                    if (semester.gpa in 0.01..4.0) {
                        totalPoints += semester.gpa * creditHourList[i]
                        totalCredits += creditHourList[i]
                    }
        }

    return if (totalCredits > 0) totalPoints / totalCredits else 0.0
}

@Composable
fun ChooseSemester(semester: SemesterData,gpaValue: String, onGpaChanged: (String, sm:SemesterData) -> Unit) {

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
            onValueChange = {
                onGpaChanged(it,semester)
            },
            placeholder = { Text("GPA", color = Color(0xFFA2A4A4), fontSize = 16.sp) },
            modifier = Modifier
                .width(132.dp)
                .padding(horizontal = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD5D5D5),
                focusedBorderColor = Color(0xFFD5D5D5)
            )
        )
        Text(
            text = semester.creditHours.toString(),
            color = Color(0xFFA2A4A4),
            fontSize = 16.sp,
            modifier = Modifier.padding(end = 12.dp)
        )
    }
}

@Composable
fun Warning() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3F5F7))
            .padding(12.dp)
    ) {
        Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF9A9C9E))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "You have to enter at least 2 of your semester ''GPA'' to calculate your CGPA. (ONLY FOR GCUF AFFILIATED COLLEGES)",
            color = Color(0xFF9A9C9E),
            fontSize = 12.sp
        )
    }
}
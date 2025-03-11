package com.example.cgpacalculator.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cgpacalculator.DataModel.CList
import com.example.cgpacalculator.R
import com.example.cgpacalculator.ui.theme.MainColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val list = listOf(
        CList(
            R.drawable.book,
            "Calculate CGPA",
            "Calculate CGPA by choosing Graduation Year",
            bgColor = Color(0xFFC7FFE5),
            icColor = Color(0xFF80DBB4)
        ),
        CList(
            R.drawable.view,
            "View Saved",
            "lorem ipsum and lorem ipsum and ipsum and lorem ipsum and",
            bgColor = Color(0xFFEBFFCB),
            icColor = Color(0xFFA1BC66)
        ),
    )

    // State for showing the bottom sheet
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(containerColor = MainColor) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ActionBars()
                Spacer(modifier = Modifier.height(20.dp))
                ChartGraph()
                Spacer(modifier = Modifier.height(30.dp))
                LazyColumn {
                    items(list) { item ->
                        CheckGpaList(item) { title ->
                            if (title == "Calculate CGPA") {
                                showBottomSheet = true
                            } else {
                                android.widget.Toast.makeText(context, "Available Soon", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.cap),
                    contentDescription = "Illustration",
                    modifier = Modifier
                        .size(260.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            // Bottom Sheet for batch selection
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    BatchSelectionBottomSheet { selectedBatch ->
                        navController.navigate("CalculateCgpa/$selectedBatch")
                        coroutineScope.launch {
                            sheetState.hide() // Hide the bottom sheet
                            showBottomSheet = false
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BatchSelectionBottomSheet(onBatchSelected: (String) -> Unit) {
    val batches = listOf("2018-22","2019-23","2020-24","2021-25", "2022-26", "2023-27","2024-28")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select Batch",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        batches.forEach { batch ->
            Text(
                text = "Batch $batch",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBatchSelected(batch) }
                    .padding(vertical = 12.dp),
                color = MainColor
            )
            Divider(color = Color.LightGray)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CheckGpaList(list:CList, onClick:(String)-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .height(80.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(12.dp))
            .clickable(onClick = { onClick(list.title) }),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(list.image),
                contentDescription = list.title,
                colorFilter = ColorFilter.tint(list.icColor),
                modifier = Modifier
                    .size(50.dp)
                    .background(list.bgColor, shape = RoundedCornerShape(30.dp))
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = list.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = list.description,
                    maxLines = 2,
                    fontWeight = FontWeight.Light,
                    fontSize = 11.sp,
                    color = Color.Black
                )

            }
        }
    }
}
@Preview
@Composable
fun ChartGraph() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(200.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .border(width = 1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Your Progress",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(45.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Feature Not Available", color = Color.LightGray, fontSize = 25.sp, textAlign = TextAlign.Center) }

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Preview
@Composable
fun ActionBars() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
            ) // Shadow
            .background(
                MainColor,
                shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
            )
            .clip(
                RoundedCornerShape(
                    bottomStart = 100.dp,
                    bottomEnd = 100.dp
                )
            ), // Clip ensures the shadow follows the shape
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Text(
            text = "CGPA- Grade Calculator", fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

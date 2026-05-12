
package com.example.kreedaprerana
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kreedaprerana.database.DatabaseProvider
import com.example.kreedaprerana.model.Trail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrialLoggerScreen(navController: NavController) {

    val context = LocalContext.current

    val dao = DatabaseProvider
        .getDatabase(context)
        .trailDao()

    val athleteDao = DatabaseProvider
        .getDatabase(context)
        .athleteDao()

    val athletes by athleteDao
        .getAllAthletes()
        .collectAsState(initial = emptyList())

    var athleteName by remember { mutableStateOf("") }
    var athleticTest by remember { mutableStateOf("") }
    var performance by remember { mutableStateOf("") }

    val unitOptions = listOf(
        "Seconds",
        "Meters",
        "Points"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedUnit by remember {
        mutableStateOf("Seconds")
    }

    //////////////////////////////////////////////////
    // STOPWATCH
    //////////////////////////////////////////////////

    var time by remember {
        mutableLongStateOf(0L)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isRunning) {

        while (isRunning) {
            delay(10L)
            time += 10L
        }
    }

    val minutes = (time / 1000) / 60
    val seconds = (time / 1000) % 60
    val milliseconds = (time % 1000) / 10

    val formattedTime = String.format(
        "%02d:%02d.%02d",
        minutes,
        seconds,
        milliseconds
    )

    //////////////////////////////////////////////////
    // SCORE CALCULATION
    //////////////////////////////////////////////////

    val autoScore = remember(performance, selectedUnit) {

        val value = performance.toDoubleOrNull()

        if (value == null) {

            null

        } else {

            when (selectedUnit) {

                "Seconds" -> {

                    when {
                        value <= 10 -> 100
                        value <= 12 -> 90
                        value <= 14 -> 80
                        value <= 16 -> 70
                        value <= 18 -> 60
                        else -> 40
                    }
                }

                "Meters" -> {

                    when {
                        value >= 8 -> 100
                        value >= 7 -> 90
                        value >= 6 -> 80
                        value >= 5 -> 70
                        value >= 4 -> 60
                        else -> 40
                    }
                }

                else -> 50
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Trial Logger")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFF5F7FF),
                            Color.White
                        )
                    )
                )
                .padding(padding)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            //////////////////////////////////////////////////
            // STOPWATCH CARD
            //////////////////////////////////////////////////

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF6A4FB3)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        Icons.Default.Timer,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = formattedTime,
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Button(
                            onClick = {
                                isRunning = true
                            }
                        ) {
                            Text("Start")
                        }

                        Button(
                            onClick = {
                                isRunning = false
                            }
                        ) {
                            Text("Stop")
                        }

                        Button(
                            onClick = {
                                isRunning = false
                                time = 0L
                            }
                        ) {
                            Text("Reset")
                        }
                    }
                }
            }

            //////////////////////////////////////////////////
            // ATHLETE SELECTION
            //////////////////////////////////////////////////

            var athleteExpanded by remember {
                mutableStateOf(false)
            }

            ExposedDropdownMenuBox(
                expanded = athleteExpanded,
                onExpandedChange = {
                    athleteExpanded = !athleteExpanded
                }
            ) {

                OutlinedTextField(
                    value = athleteName,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Select Athlete")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = athleteExpanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                ExposedDropdownMenu(
                    expanded = athleteExpanded,
                    onDismissRequest = {
                        athleteExpanded = false
                    }
                ) {

                    athletes.forEach { athlete ->

                        DropdownMenuItem(
                            text = {
                                Text(athlete.name)
                            },
                            onClick = {
                                athleteName = athlete.name
                                athleteExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = athleticTest,
                onValueChange = {
                    athleticTest = it
                },
                label = {
                    Text("Athletic Test")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            OutlinedTextField(
                value = performance,
                onValueChange = {
                    performance = it
                },
                label = {
                    Text("Sprint Time / Jump Distance")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            //////////////////////////////////////////////////
            // UNIT DROPDOWN
            //////////////////////////////////////////////////

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(
                    value = selectedUnit,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Measurement Unit")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    unitOptions.forEach { unit ->

                        DropdownMenuItem(
                            text = {
                                Text(unit)
                            },
                            onClick = {
                                selectedUnit = unit
                                expanded = false
                            }
                        )
                    }
                }
            }

            //////////////////////////////////////////////////
            // AUTO SCORE CARD
            //////////////////////////////////////////////////

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEDE7FF)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Performance Score",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = autoScore?.let {
                            "$it / 100"
                        } ?: "-- / 100",
                        fontSize = 28.sp,
                        color = Color(0xFF6A4FB3),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            //////////////////////////////////////////////////
            // SAVE BUTTON
            //////////////////////////////////////////////////

            Button(
                onClick = {

                    if (
                        athleteName.isBlank() ||
                        athleticTest.isBlank() ||
                        performance.isBlank()
                    ) {
                        return@Button
                    }

                    val currentDate = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(Date())

                    val trail = Trail(
                        athleteName = athleteName,
                        testType = athleticTest,
                        result = performance.toDoubleOrNull() ?: 0.0,
                        unit = selectedUnit,
                        score = autoScore ?: 0,
                        date = currentDate
                    )

                    CoroutineScope(Dispatchers.IO).launch {

                        dao.insertTrail(trail)

                        kotlinx.coroutines.withContext(
                            Dispatchers.Main
                        ) {

                            Toast.makeText(
                                context,
                                "Trial Saved Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A4FB3)
                )
            ) {

                Text(
                    text = "Save Trial",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}


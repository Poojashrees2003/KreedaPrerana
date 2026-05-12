package com.example.kreedaprerana

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kreedaprerana.database.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrailListScreen(navController: NavController) {

    val context = LocalContext.current

    val dao = DatabaseProvider
        .getDatabase(context)
        .trailDao()

    val trails by dao
        .getAllTrails()
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("Saved Trails")
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFF5F7FF),
                            Color.White
                        )
                    )
                )
                .padding(padding)
        ) {

            if (trails.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {

                    Text(
                        text = "No Trails Saved",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(trails) { trail ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                //////////////////////////////////////////////////
                                // ATHLETE NAME
                                //////////////////////////////////////////////////

                                Text(
                                    text = trail.athleteName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                //////////////////////////////////////////////////
                                // TRAIL DETAILS
                                //////////////////////////////////////////////////

                                Text(
                                    text = "Test Type: ${trail.testType}",
                                    fontSize = 15.sp
                                )

                                Text(
                                    text = "Performance: ${trail.result} ${trail.unit}",
                                    fontSize = 15.sp
                                )

                                Text(
                                    text = "Score: ${trail.score}/100",
                                    fontSize = 15.sp,
                                    color = Color(0xFF6A4FB3),
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Date: ${trail.date}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )

                                //////////////////////////////////////////////////
                                // BADGE LOGIC
                                //////////////////////////////////////////////////

                                Spacer(modifier = Modifier.height(12.dp))

                                val badge = when {
                                    trail.score >= 90 -> "🥇 Gold"
                                    trail.score >= 75 -> "🥈 Silver"
                                    trail.score >= 60 -> "🥉 Bronze"
                                    else -> "⭐ Participant"
                                }

                                Text(
                                    text = "Badge: $badge",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                //////////////////////////////////////////////////
                                // DELETE BUTTON
                                //////////////////////////////////////////////////

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {

                                    Button(
                                        onClick = {

                                            CoroutineScope(Dispatchers.IO).launch {

                                                dao.deleteTrail(trail)
                                            }

                                            Toast.makeText(
                                                context,
                                                "Trail Deleted",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red
                                        )
                                    ) {

                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.White
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(
                                            text = "Delete",
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}
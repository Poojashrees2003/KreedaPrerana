package com.example.kreedaprerana
import com.example.kreedaprerana.viewmodel.TrailViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.kreedaprerana.database.DatabaseProvider
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.kreedaprerana.model.Athlete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    composable("home") {
                        HomeScreen(navController)
                    }

                    composable("dashboard") {
                        DashboardScreen(navController)
                    }

                    composable("trail_list") {   // ✅ ADDED
                        TrailListScreen(navController)
                    }

                    composable("athlete_profile") {
                        AthleteProfileScreen(navController)
                    }

                    composable("trial_logger") {
                        TrialLoggerScreen(navController)
                    }

                    composable("milestones") {

                        val context = LocalContext.current
                        val dao = DatabaseProvider.getDatabase(context).trailDao()
                        val viewModel = remember { TrailViewModel(dao) }

                        MilestoneScreen(navController, viewModel)
                    }

                    composable("leaderboard") {

                        val context = LocalContext.current
                        val dao = DatabaseProvider.getDatabase(context).trailDao()
                        val viewModel = remember { TrailViewModel(dao) }

                        LeaderboardScreen(navController, viewModel)
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////////
// HOME SCREEN
//////////////////////////////////////////////////////

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "🏆 Kreeda Prerana",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                navController.navigate("dashboard")
            }) {
                Text("Get Started")
            }
        }
    }
}

//////////////////////////////////////////////////////
// DASHBOARD SCREEN (ONLY BUTTONS)
//////////////////////////////////////////////////////

@Composable
fun DashboardScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                )
            )
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Dashboard",
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        DashboardCard("Add Athlete", Icons.Default.Person) {
            navController.navigate("athlete_profile")
        }

        DashboardCard("Trial Logger", Icons.Default.Edit) {
            navController.navigate("trial_logger")
        }

        // ✅ VIEW DATA SCREEN
        DashboardCard("View Trails", Icons.Default.List) {
            navController.navigate("trail_list")
        }

        DashboardCard("Milestone Badges", Icons.Default.EmojiEvents) {
            navController.navigate("milestones")
        }

        DashboardCard("Leaderboard", Icons.Default.Leaderboard) {
            navController.navigate("leaderboard")
        }
    }
}

//////////////////////////////////////////////////////
// TRAIL LIST SCREEN (DATA DISPLAY)
//////////////////////////////////////////////////////



//////////////////////////////////////////////////////
// DASHBOARD CARD
//////////////////////////////////////////////////////

@Composable
fun DashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title)
            Spacer(modifier = Modifier.width(20.dp))
            Text(title, fontSize = 18.sp)
        }
    }
}

//////////////////////////////////////////////////////
// ATHLETE SCREEN
//////////////////////////////////////////////////////

//////////////////////////////////////////////////////
// ATHLETE SCREEN
//////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AthleteProfileScreen(navController: NavController) {

    val context = LocalContext.current

    val athleteDao = DatabaseProvider
        .getDatabase(context)
        .athleteDao()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Athlete 👤") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Athlete Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = sport,
                onValueChange = { sport = it },
                label = { Text("Primary Sport") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    if (
                        name.isNotBlank() &&
                        age.isNotBlank() &&
                        sport.isNotBlank()
                    ) {

                        val athlete = Athlete(
                            name = name,
                            age = age.toInt(),
                            sport = sport
                        )

                        CoroutineScope(Dispatchers.IO).launch {

                            athleteDao.insertAthlete(athlete)
                        }

                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A4FB3)
                )
            ) {

                Text(
                    text = "Save Athlete",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
//////////////////////////////////////////////////////
// OTHER SCREENS
//////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilestoneScreen(
    navController: NavController,
    viewModel: TrailViewModel
) {

    //////////////////////////////////////////////////
    // TRAILS
    //////////////////////////////////////////////////

    val trails by viewModel.allTrails.collectAsState(initial = emptyList())
    val badges = viewModel.badgeState.value

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("Milestone Badges 🏅")
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

        if (trails.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {

                Text("No milestones yet")
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                items(trails) { trail ->

                    //////////////////////////////////////////////////
                    // BADGE
                    //////////////////////////////////////////////////

                    val badge = when {

                        trail.score >= 90 ->
                            "🥇 Gold Athlete"

                        trail.score >= 80 ->
                            "🥈 Silver Athlete"

                        trail.score >= 60 ->
                            "🥉 Bronze Athlete"

                        else ->
                            "💪 Beginner Athlete"
                    }

                    //////////////////////////////////////////////////
                    // LEVEL
                    //////////////////////////////////////////////////

                    val level = when {

                        trail.score >= 90 ->
                            "👑 State Level Ready"

                        trail.score >= 80 ->
                            "🏆 District Level Ready"

                        else ->
                            "Beginner Level"
                    }

                    //////////////////////////////////////////////////
                    // CARD
                    //////////////////////////////////////////////////

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            //////////////////////////////////////////////////
                            // ATHLETE NAME
                            //////////////////////////////////////////////////

                            Text(
                                text = trail.athleteName,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            //////////////////////////////////////////////////
                            // SCORE
                            //////////////////////////////////////////////////

                            Text(
                                text = "Score: ${trail.score}",
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            //////////////////////////////////////////////////
                            // BADGE
                            //////////////////////////////////////////////////

                            Text(
                                text = badge,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            //////////////////////////////////////////////////
                            // LEVEL READY
                            //////////////////////////////////////////////////

                            Text(
                                text = level,
                                fontSize = 16.sp,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: TrailViewModel
) {

    val trails by viewModel.allTrails.collectAsState(initial = emptyList())

    val sortedTrails = trails.sortedByDescending { it.score }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leaderboard 🏆") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            itemsIndexed(sortedTrails) { index, trail ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = "#${index + 1}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            if (index == 0) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = "Winner",
                                    tint = Color(0xFFFFC107)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = trail.athleteName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "⭐ ${trail.score}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CenterScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(title, fontSize = 22.sp)
    }
}
# 🏃 KreedaPrerana

KreedaPrerana Scout is an Android-based sports management and athlete tracking application developed to manage athlete performance, trail activities, milestone achievements, and leaderboard rankings efficiently. The application helps coaches and athletes monitor progress, maintain trail records, and visualize achievements through a modern Android interface.

---

# 📱 Features

## ✅ Athlete Management
- Add athlete details
- Store athlete information locally
- View registered athletes
- Manage athlete performance records

## ✅ Trail Logger
- Log athlete trail activities
- Track distance covered
- Maintain activity history
- Real-time trail updates

## ✅ View Trails
- Display all logged trails
- Card-based trail UI
- Shows:
  - Athlete Name
  - Distance
  - Date
  - Activity Level

## ✅ Milestone Badges
- Achievement badge system
- State Level Ready badge
- Distance milestone tracking
- Visual badge display

## ✅ Leaderboard
- Athlete ranking system
- Performance-based ordering
- Dynamic leaderboard updates
- Modern ranking UI

## ✅ Dashboard
- Centralized overview screen
- Quick athlete statistics
- Performance summary
- Navigation to all modules

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Kotlin | Backend & business logic |
| Jetpack Compose | Modern Android UI |
| Room Database | Local database |
| SQLite | Data storage |
| ViewModel | State management |
| RecyclerView / LazyColumn | Dynamic lists |
| Material Design | UI components |

---

# 🧱 Project Architecture

## Frontend
- Jetpack Compose UI

## Backend
- Kotlin Logic

## Database
- Room Database + SQLite

## Architecture Pattern
- MVVM (Model-View-ViewModel)

---

# 📂 Project Structure

```text
KreedaPrerana
│
├── java/com.example.kreedaprerana
│   ├── MainActivity.kt
│   ├── AthleteScreen.kt
│   ├── TrailLoggerScreen.kt
│   ├── ViewTrailsScreen.kt
│   ├── MilestoneBadgeScreen.kt
│   ├── LeaderboardScreen.kt
│   ├── TrailViewModel.kt
│   ├── TrailDao.kt
│   ├── TrailDatabase.kt
│   └── Athlete.kt
│
├── res
│   ├── drawable
│   └── values
│
├── manifests
│   └── AndroidManifest.xml
│
└── Gradle Scripts
    ├── build.gradle.kts
    └── settings.gradle.kts
```

---

# 📈 Results

The KreedaPrerana application successfully achieved the following outcomes:

✅ Athlete management and tracking  
✅ Trail logging and distance monitoring  
✅ Milestone badge achievement system  
✅ Dynamic leaderboard implementation  
✅ Local data storage using Room Database  
✅ Modern Android UI using Jetpack Compose  
✅ Efficient activity and performance tracking  

The project demonstrates the practical use of Android development concepts including:

- Room Database
- MVVM Architecture
- Jetpack Compose
- State Management
- LazyColumn UI
- Material Design
- Real-time UI updates

---

# 📸 Screenshots

### Home Screen
![Home Screen](screenshots/Screenshot%202026-05-12%20125127.png)

### Dashboard
![Dashboard](screenshots/Screenshot%202026-05-12%20125134.png)

### Trail Logger
![Trail Logger](screenshots/Screenshot%202026-05-12%20125158.png)

### View Trails
![View Trails](screenshots/Screenshot%202026-05-12%20125208.png)

### Milestone Badge
![Milestone Badge](screenshots/Screenshot%202026-05-12%20125217.png)

### Leaderboard
![Leaderboard](screenshots/Screenshot%202026-05-12%20125226.png)

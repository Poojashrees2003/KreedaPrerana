package com.example.kreedaprerana.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kreedaprerana.dao.TrailDao
import com.example.kreedaprerana.model.Badge
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrailViewModel(
    private val dao: TrailDao
) : ViewModel() {

    //////////////////////////////////////////////////
    // ALL TRAILS
    //////////////////////////////////////////////////

    val allTrails = dao.getAllTrails()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    //////////////////////////////////////////////////
    // BADGE STATE
    //////////////////////////////////////////////////

    private val _badgeState =
        mutableStateOf<List<Badge>>(emptyList())

    val badgeState: State<List<Badge>>
        get() = _badgeState

    //////////////////////////////////////////////////
    // AUTO UPDATE BADGES
    //////////////////////////////////////////////////

    init {

        viewModelScope.launch {

            dao.getAllTrails().collectLatest { trails ->

                //////////////////////////////////////////////////
                // HIGHEST SCORE
                //////////////////////////////////////////////////

                val highestScore =
                    trails.maxOfOrNull { it.score } ?: 0

                //////////////////////////////////////////////////
                // BADGE LIST
                //////////////////////////////////////////////////

                val badges = buildList {

                    //////////////////////////////////////////////
                    // BEGINNER
                    //////////////////////////////////////////////

                    if (highestScore < 60) {

                        add(
                            Badge(
                                id = "0",
                                title = "🥉 Beginner Athlete",
                                description = "Beginner Level",
                                unlocked = true
                            )
                        )
                    }

                    //////////////////////////////////////////////
                    // BRONZE
                    //////////////////////////////////////////////

                    if (highestScore >= 60) {

                        add(
                            Badge(
                                id = "1",
                                title = "🥉 Bronze Athlete",
                                description = "Score above 60",
                                unlocked = true
                            )
                        )
                    }

                    //////////////////////////////////////////////
                    // SILVER
                    //////////////////////////////////////////////

                    if (highestScore >= 80) {

                        add(
                            Badge(
                                id = "2",
                                title = "🥈 Silver Athlete",
                                description = "Score above 80",
                                unlocked = true
                            )
                        )

                        add(
                            Badge(
                                id = "3",
                                title = "🏆 District Level Ready",
                                description = "District Level Ready",
                                unlocked = true
                            )
                        )
                    }

                    //////////////////////////////////////////////
                    // GOLD
                    //////////////////////////////////////////////

                    if (highestScore >= 90) {

                        add(
                            Badge(
                                id = "4",
                                title = "🥇 Gold Athlete",
                                description = "Score above 90",
                                unlocked = true
                            )
                        )

                        add(
                            Badge(
                                id = "5",
                                title = "👑 State Level Ready",
                                description = "State Level Ready",
                                unlocked = true
                            )
                        )
                    }
                }

                //////////////////////////////////////////////////
                // UPDATE UI
                //////////////////////////////////////////////////

                _badgeState.value = badges
            }
        }
    }
}
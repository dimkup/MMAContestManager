package com.mma.contestmanager.models

import com.mma.contestmanager.app.AGE_SHIFT
import com.mma.contestmanager.app.MAX_AGE
import com.mma.contestmanager.app.MAX_WEIGHT
import javafx.collections.ObservableList
import tornadofx.*
import java.util.*
import kotlin.collections.ArrayList

class Category(
    val minAge: Int,
    val minWeight: Int,
    private val globalSportsmenMap: NavigableMap<Int, Sportsman>
) {
    val sportsmen: ObservableList<Sportsman> = ArrayList<Sportsman>().observable()
    private var maxAge = 0
    private var maxWeight = 0
    val name: String
        get() {
            return "$minAge/$minWeight"
        }


    init {
        updateUpperLimits(MAX_AGE, MAX_WEIGHT)
    }

    fun updateUpperLimits(age: Int, weight: Int) {
        if (age != maxAge || weight != maxWeight) {
            maxAge = age
            maxWeight = weight

            forceRefresh()
        }
    }

    fun addSportsman(sportsman: Sportsman) {
        sportsmen.add(sportsman)
    }

    fun removeSportsman(sportsman: Sportsman) {
        sportsmen.remove(sportsman)
    }

    private fun forceRefresh() {
        val localSportsmanMap = globalSportsmenMap.subMap(
            minAge * AGE_SHIFT,
            true,
            maxAge * AGE_SHIFT,
            false
        )
        sportsmen.clear()
        val newSportsmen =
            localSportsmanMap.filter { it.value.weightProperty >= minWeight && it.value.weightProperty < maxWeight }
                .map { it.value }
        for (s in newSportsmen) {
            sportsmen.add(s)
            s.category = this
        }
    }

    override fun toString(): String {
        return "Age [$minAge,$maxAge) Weight [$minWeight,$maxWeight) Size ${sportsmen.size}"
    }
}


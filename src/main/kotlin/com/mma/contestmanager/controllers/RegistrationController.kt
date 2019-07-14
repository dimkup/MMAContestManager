package com.mma.contestmanager.controllers

import com.mma.contestmanager.app.MAX_AGE
import com.mma.contestmanager.app.MAX_WEIGHT
import com.mma.contestmanager.app.MIN_AGE
import com.mma.contestmanager.app.MIN_WEIGHT
import com.mma.contestmanager.models.Category
import com.mma.contestmanager.models.Sportsman
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import tornadofx.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class RegistrationController : Controller() {
    val sportsmen: ObservableList<Sportsman> = ArrayList<Sportsman>().observable()
    val categoriesMap: NavigableMap<Int, NavigableMap<Int, Category>> = TreeMap<Int, NavigableMap<Int, Category>>()
    val navSportsmenMap: NavigableMap<Int, Sportsman> = TreeMap<Int, Sportsman>()
    val categoriesUpdatedProperty = SimpleIntegerProperty(0)

    val ageIntervals = mutableListOf(
        SimpleIntegerProperty(MIN_AGE)
    ).observable()

    val weightIntervals = mutableListOf(
        SimpleIntegerProperty(MIN_WEIGHT)
    ).observable()

    init {

        //Sportsmen collection change listener
        sportsmen.addListener { c: ListChangeListener.Change<out Sportsman> ->
            while (c.next()) {
                when {
                    //sportsman removed
                    c.wasRemoved() -> c.removed.forEach {
                        navSportsmenMap.remove(it.ageIdProperty.intValue())
                        categoriesMap.floorEntry(it.ageProperty.intValue())
                            .value.floorEntry(it.weightProperty.intValue()).value.removeSportsman(it)
                    }

                    //sportsman added
                    c.wasAdded() -> c.addedSubList.forEach {
                        navSportsmenMap[it.ageIdProperty.intValue()] = it

                        it.weightProperty.addListener { _, old, new ->
                            val oldCategory = it.category
                            val newCategory = categoriesMap.floorEntry(it.ageProperty.intValue()).value.floorEntry(it.weightProperty.intValue()).value
                            if (oldCategory != newCategory) {
                                oldCategory?.removeSportsman(it)
                                newCategory.addSportsman(it)
                                it.category = newCategory
                                println("weight old id $old new id $new oldcat $oldCategory newcat $newCategory")
                            }
                        }

                        it.ageIdProperty.addListener { _, old, new ->
                            navSportsmenMap.remove(old.toInt())
                            navSportsmenMap[new.toInt()] = it
                            val oldCategory = it.category
                            val newCategory = categoriesMap.floorEntry(it.ageProperty.intValue()).value.floorEntry(it.weightProperty.intValue()).value
                            if (oldCategory != newCategory) {
                                oldCategory?.removeSportsman(it)
                                newCategory.addSportsman(it)
                                it.category = newCategory
                                println("age old id $old new id $new oldcat $oldCategory newcat $newCategory")
                            }
                        }

                        val category = categoriesMap.floorEntry(it.ageProperty.intValue()).value.floorEntry(it.weightProperty.intValue()).value
                        category.addSportsman(it)
                        it.category = category
                    }

                }
            }
        }

        //age intervals change listener
        ageIntervals.addListener { c: ListChangeListener.Change<out SimpleIntegerProperty> ->
            while (c.next()) {
                if (c.wasAdded()) c.addedSubList.forEach { it.addListener { _ -> updateCategories() } }
            }
            updateCategories()
        }

        //weight intervals change listener
        weightIntervals.addListener { c: ListChangeListener.Change<out SimpleIntegerProperty> ->
            while (c.next()) {
                if (c.wasAdded()) c.addedSubList.forEach { it.addListener { _ -> updateCategories() } }
            }
            updateCategories()
        }

        //Initial build categories map
        updateCategories()
    }


    private fun updateCategories() {

        val ageOrigSet = HashSet<Int>(categoriesMap.keys)
        for (age in ageIntervals) {
            if (!ageOrigSet.contains(age.intValue())) {
                println("Add age ${age.intValue()}")
                categoriesMap[age.intValue()] = TreeMap()
            } else
                ageOrigSet.remove(age.intValue())
        }
        ageOrigSet.forEach {
            println("Remove age ${it}")
            categoriesMap.remove(it)
        }

        for ((age, categoriesSubMap) in categoriesMap) {
            val weightOrigSet = HashSet<Int>(categoriesSubMap.keys)
            for (weight in weightIntervals) {
                if (!weightOrigSet.contains(weight.intValue())) {
                    categoriesSubMap[weight.intValue()] = Category(age, weight.intValue(), navSportsmenMap)
                    println("Add weight ${weight.intValue()}")
                } else weightOrigSet.remove(weight.intValue())
            }
            weightOrigSet.forEach {
                println("Remove weight $it")
                categoriesSubMap.remove(it)
            }
        }


        var currentMaxAge = MAX_AGE
        for ((age, currentWeightMap) in categoriesMap.descendingMap()) {
            var currentMaxWeight = MAX_WEIGHT
            for ((weight, currentCategory) in currentWeightMap.descendingMap()) {
                currentCategory.updateUpperLimits(currentMaxAge, currentMaxWeight)
                currentMaxWeight = weight
            }
            currentMaxAge = age
        }

        categoriesUpdatedProperty.set(categoriesUpdatedProperty.intValue() + 1)
        println("-----------------")
        categoriesMap.forEach { it.value.forEach { println(it.value) } }
    }

}



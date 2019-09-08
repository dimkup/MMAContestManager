package com.mma.contestmager.tests.controllers

import com.mma.contestmanager.controllers.RegistrationController
import com.mma.contestmanager.app.MIN_AGE
import com.mma.contestmanager.app.MIN_WEIGHT
import com.mma.contestmanager.models.Sportsman
import javafx.beans.property.SimpleIntegerProperty
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RegistrationControllerTest {

    @Test
    fun registrationControllerSetupTest() {
        val controller = RegistrationController()
        assertEquals(0, controller.sportsmen.size)
        assertEquals(1, controller.ageIntervals.size)
        assertEquals(MIN_AGE, controller.ageIntervals[0].value)
        assertEquals(1, controller.weightIntervals.size)
        assertEquals(MIN_WEIGHT, controller.weightIntervals[0].value)
    }

    @Test
    fun registrationControllerSportsmenCRUDTest() {
        val controller = RegistrationController()
        setupAndAssertCategories(controller, intArrayOf(7, 8, 12, 15), intArrayOf(15, 16, 20, 25, 30))

        val sportsman1 = Sportsman(1, "name1", "lastName1", MIN_AGE, MIN_WEIGHT)
        addAndAssertSportsman(controller, sportsman1, MIN_AGE, MIN_WEIGHT)

        val sportsman2 = Sportsman(2, "name2", "lastName2", MIN_AGE + 1, MIN_WEIGHT + 1)
        addAndAssertSportsman(controller, sportsman2, MIN_AGE, MIN_WEIGHT)

        val sportsman3 = Sportsman(3, "name3", "lastName3", 7, 17)
        addAndAssertSportsman(controller, sportsman3, 7, 16)

        val sportsman4 = Sportsman(4, "name4", "lastName4", 8, 15)
        addAndAssertSportsman(controller, sportsman4, 8, 15)

        assertEquals(2, controller.categoriesMap[MIN_AGE]!![MIN_WEIGHT]!!.sportsmen.size)
        assertEquals(1, controller.categoriesMap[7]!![16]!!.sportsmen.size)
        assertEquals(1, controller.categoriesMap[8]!![15]!!.sportsmen.size)


    }

    private fun addAndAssertSportsman(
        controller: RegistrationController,
        sportsman: Sportsman,
        expectedAge: Int,
        expectedWeight: Int
    ) {
        controller.sportsmen.add(sportsman)
        assertEquals(expectedAge, sportsman.category!!.minAge)
        assertEquals(expectedWeight, sportsman.category!!.minWeight)
        assertTrue(controller.categoriesMap[expectedAge]!![expectedWeight]!!.sportsmen.contains(sportsman))
    }

    private fun setupAndAssertCategories(
        controller: RegistrationController,
        ageIntervals: IntArray,
        weightIntervals: IntArray
    ) {
        //Setup
        for (age in ageIntervals) {
            controller.ageIntervals.add(SimpleIntegerProperty(age))
        }
        for (weight in weightIntervals) {
            controller.weightIntervals.add(SimpleIntegerProperty(weight))
        }

        //Assert
        assertEquals(ageIntervals.size + 1, controller.ageIntervals.size)
        assertEquals(weightIntervals.size + 1, controller.weightIntervals.size)
        assertEquals(ageIntervals.size + 1, controller.categoriesMap.size)
        for ((age, weightMap) in controller.categoriesMap) {
            assertEquals(weightIntervals.size + 1, weightMap.size)
            for ((weight, category) in weightMap) {
                assertEquals(age, category.minAge)
                assertEquals(weight, category.minWeight)
            }
        }
    }
}
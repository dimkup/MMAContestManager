package com.mma.contestmanager.views

import com.mma.contestmanager.app.*
import com.mma.contestmanager.controllers.RegistrationController
import com.mma.contestmanager.views.intervals.IntervalFragment
import tornadofx.*

class CategoryTabView : View() {
    private val controller: RegistrationController by inject()
    override val root = borderpane()


    init {
        with(root) {
            left = hbox {
                add(
                    IntervalFragment(
                        controller.weightIntervals,
                        messages["weight"],
                        MIN_WEIGHT,
                        MAX_WEIGHT,
                        MAX_THUMBS
                    )
                )
                add(IntervalFragment(controller.ageIntervals, messages["age"], MIN_AGE, MAX_AGE, MAX_THUMBS))
                spacing = 20.0

            }
            center  = tableview<Pair<Int,Int>> {
                items = listOf(
                    Pair(3, 33),
                    Pair(8, 29),
                    Pair(10, 41)
                ).observable()

                //column("NAME",Pair<Int,Int>::first)
                //column("AGE",Pair<Int,Int>::second)
            }
        }
    }
}

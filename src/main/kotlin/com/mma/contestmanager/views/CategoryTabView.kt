package com.mma.contestmanager.views

import com.mma.contestmanager.app.*
import com.mma.contestmanager.controllers.RegistrationController
import com.mma.contestmanager.views.intervals.IntervalFragment
import javafx.scene.layout.HBox
import tornadofx.*

class CategoryTabView : View() {
    private val controller: RegistrationController by inject()
    override val root = HBox()


    init {
        with(root) {
            add(IntervalFragment(controller.weightIntervals,messages["weight"], MIN_WEIGHT, MAX_WEIGHT, MAX_THUMBS))
            add(IntervalFragment(controller.ageIntervals,messages["age"], MIN_AGE, MAX_AGE, MAX_THUMBS))
            spacing = 20.0
        }
    }
}

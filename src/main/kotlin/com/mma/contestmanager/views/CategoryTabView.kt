package com.mma.contestmanager.views

import com.mma.contestmanager.app.*
import com.mma.contestmanager.views.intervals.IntervalFragment
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.layout.HBox
import tornadofx.*

class CategoryTabView : View() {
    override val root = HBox()
    private val intList1 = mutableListOf(
        SimpleIntegerProperty(MIN_WEIGHT + 1),
        SimpleIntegerProperty(15),
        SimpleIntegerProperty(20)
    ).observable()
    private val intList2 = mutableListOf(
        SimpleIntegerProperty(MIN_AGE + 1),
        SimpleIntegerProperty(15),
        SimpleIntegerProperty(20)
    ).observable()

    init {
        with(root) {
            add(IntervalFragment(intList1,messages["weight"], MIN_WEIGHT, MAX_WEIGHT, MAX_THUMBS))
            add(IntervalFragment(intList2,messages["age"], MIN_AGE, MAX_AGE, MAX_THUMBS))
            spacing = 20.0
        }
    }
}

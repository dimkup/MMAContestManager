package com.mma.contestmanager.views

import com.mma.contestmanager.views.intervals.IntervalFragment
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.layout.HBox
import tornadofx.*

class CategoryTabView : View("My View") {
    override val root = HBox()
    private val intList1 = mutableListOf(
            SimpleIntegerProperty(5),
            SimpleIntegerProperty(15),
            SimpleIntegerProperty(20)).observable()
    private val intList2 = mutableListOf(
            SimpleIntegerProperty(5),
            SimpleIntegerProperty(15),
            SimpleIntegerProperty(20)).observable()
    init {
        with(root) {
            add(IntervalFragment::class,mapOf(
                    IntervalFragment::intervalList to intList1,
                    IntervalFragment::headText to "Weight"))
            add(IntervalFragment::class,mapOf(
                    IntervalFragment::intervalList to intList2,
                    IntervalFragment::headText to "Age"))
            spacing = 20.0
        }
    }
}

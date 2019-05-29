package com.mma.contestmanager.view

import com.mma.contestmanager.view.intervals.IntervalFragment
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.layout.VBox
import tornadofx.*

class CategoryTabView : View("My View") {
    override val root = VBox()
    private val intList = mutableListOf(
            SimpleIntegerProperty(5),
            SimpleIntegerProperty(15),
            SimpleIntegerProperty(20)).observable()
    init {
        with(root) {
            add(IntervalFragment::class,mapOf(
                    IntervalFragment::intervalList to intList,
                    IntervalFragment::headText to "Weight"))
        }
    }
}

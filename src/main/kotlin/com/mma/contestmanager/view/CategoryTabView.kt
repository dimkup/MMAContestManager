package com.mma.contestmanager.view

import com.mma.contestmanager.view.intervals.IntervalFragment
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.layout.VBox
import tornadofx.*

class CategoryTabView : View("My View") {
    override val root = VBox()
    var counter = 0
    val intList = mutableListOf(SimpleIntegerProperty(5),SimpleIntegerProperty(15), SimpleIntegerProperty(20)).observable()
    init {
        with(root) {
//            button("Add") {
//                action {
//                    root.apply {
//                        val newElem = HBox()
//                        newElem += Label("aaa $counter")
//                        newElem.button("Del") {
//                            action {
//                                newElem.removeFromParent()
//                            }
//                        }
//                        counter++
//                        this += newElem
//                    }
//                }
//            }
            add(IntervalFragment::class,mapOf(
                    IntervalFragment::intervalList to intList,
                    IntervalFragment::headText to "Weight"))
        }
    }
}

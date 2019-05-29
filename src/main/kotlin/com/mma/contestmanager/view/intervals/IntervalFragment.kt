package com.mma.contestmanager.view.intervals

import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ObservableList
import javafx.scene.layout.VBox
import tornadofx.*

class IntervalFragment : Fragment() {
    val intervalList: ObservableList<SimpleIntegerProperty> by param()
    val headText: String? by param()
    private val thumbsVBox = VBox()

    init {
        intervalList.onChange { handleChanges() }
    }

    override val root = vbox {
        headText?.let { label(headText!!) }
        vbox {
            add(thumbsVBox)
            button("Add") {
                action {
                    val maxVal = intervalList.last().get() + 10
                    intervalList.add(SimpleIntegerProperty(maxVal))
                }
            }
        }
        handleChanges()
    }

    private fun handleChanges() {
        thumbsVBox.children.clear()
        // Add the first value
        thumbsVBox.add(IntervalThumbFragment(
                SimpleIntegerProperty(0),
                intervalList[1],
                intervalList[0],
                null))

        // Add all other values
        for (i in 1..intervalList.size - 2) {
            thumbsVBox.add(IntervalThumbFragment(
                    intervalList[i - 1],
                    intervalList[i + 1],
                    intervalList[i])
                    { intervalList.remove(intervalList[i]) })

        }
        // Add the last value
        thumbsVBox.add(IntervalThumbFragment(
                intervalList.reversed().drop(1).first(),
                SimpleIntegerProperty(1000),
                intervalList.last(),
                null))

    }
}

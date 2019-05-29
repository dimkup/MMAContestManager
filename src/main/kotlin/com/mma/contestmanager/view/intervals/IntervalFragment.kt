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
        headText?.let {label(headText!!)}
        vbox {
            add(thumbsVBox)
            button("Add") {
                action {
                    val maxVal =intervalList.last().get()+10
                    intervalList.add(SimpleIntegerProperty(maxVal))
                }
            }
        }
        handleChanges()
    }
    private fun handleChanges() {
        thumbsVBox.children.clear()
        // Add the first value
        thumbsVBox.add(IntervalThumbFragment::class,
                mapOf(
                        IntervalThumbFragment::valueProperty to intervalList[0],
                        IntervalThumbFragment::maxValProperty to intervalList[1],
                        IntervalThumbFragment::minValProperty to SimpleIntegerProperty(0),
                        IntervalThumbFragment::deleteAction to null))
        // Add all other values
        for (i in 1..intervalList.size - 2) {
            thumbsVBox.add(IntervalThumbFragment::class,
                    mapOf(
                            IntervalThumbFragment::valueProperty to intervalList[i],
                            IntervalThumbFragment::maxValProperty to intervalList[i + 1],
                            IntervalThumbFragment::minValProperty to intervalList[i - 1],
                            IntervalThumbFragment::deleteAction to {intervalList.remove(intervalList[i])}))
        }
        // Add the last value
        thumbsVBox.add(IntervalThumbFragment::class,
                mapOf(
                        IntervalThumbFragment::valueProperty to intervalList.last(),
                        IntervalThumbFragment::minValProperty to intervalList.reversed().drop(1).first(),
                        IntervalThumbFragment::maxValProperty to SimpleIntegerProperty(1000),
                        IntervalThumbFragment::deleteAction to null))
    }
}

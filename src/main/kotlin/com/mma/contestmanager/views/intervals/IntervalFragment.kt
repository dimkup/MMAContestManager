package com.mma.contestmanager.views.intervals

import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
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
        headText?.let {
            label(headText!!) {
            }
        }
        add(thumbsVBox)
        button("Add") {
            action {
                val maxVal = intervalList.last().get() + 10
                intervalList.add(SimpleIntegerProperty(maxVal))
            }
            useMaxWidth = true
        }
        alignment = Pos.BASELINE_CENTER
        spacing = 10.0

        handleChanges()
    }

    private fun handleChanges() {
        thumbsVBox.children.clear()

        val tf: (Int) -> (() -> Unit)? = { idx: Int ->
            when {
                intervalList.size > 2 -> ({ intervalList.removeAt(idx) })
                else -> null
            }
        }

        // Add the first value
        thumbsVBox.add(
            IntervalThumbFragment(
                SimpleIntegerProperty(0),
                intervalList[1],
                intervalList[0],
                tf(0)
            )
        )

        // Add all other values
        for (i in 1..intervalList.size - 2) {
            thumbsVBox.add(IntervalThumbFragment(
                intervalList[i - 1],
                intervalList[i + 1],
                intervalList[i]
            )
            { intervalList.removeAt(i) })

        }
        // Add the last value
        thumbsVBox.add(
            IntervalThumbFragment(
                intervalList.reversed().drop(1).first(),
                SimpleIntegerProperty(1000),
                intervalList.last(),
                tf(intervalList.size - 1)
            )
        )

    }
}

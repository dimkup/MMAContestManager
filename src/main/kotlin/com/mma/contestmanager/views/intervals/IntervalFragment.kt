package com.mma.contestmanager.views.intervals

import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import tornadofx.*

class IntervalFragment(
    private val intervalList: ObservableList<SimpleIntegerProperty>,
    private val headText: String?,
    private val minVal: Int,
    private val maxVal: Int,
    private val maxThumbs: Int
) : Fragment() {

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
        button(messages["add"]) {
            enableWhen(intervalList.sizeProperty.lessThan(maxThumbs))
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
                SimpleIntegerProperty(minVal),
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
                SimpleIntegerProperty(maxVal),
                intervalList.last(),
                tf(intervalList.size - 1)
            )
        )

    }
}

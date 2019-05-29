package com.mma.contestmanager.view.intervals

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class IntervalThumbFragment(private val minValProperty: SimpleIntegerProperty,
                            private val maxValProperty: SimpleIntegerProperty,
                            private val valueProperty: SimpleIntegerProperty,
                            private val deleteAction: (() -> Unit)?) : Fragment() {

    var value by valueProperty

    override val root = hbox {
        button("-") {
            enableWhen(valueProperty.add(-1).greaterThan(minValProperty))
            action { value-- }
        }
        label { textProperty().bind(valueProperty.asString()) }
        button("+") {
            enableWhen(valueProperty.add(1).lessThan(maxValProperty))
            action { value++ }
        }
        deleteAction?.let {
            button("Del") {
                action { deleteAction.invoke() }
            }
        }
    }
}

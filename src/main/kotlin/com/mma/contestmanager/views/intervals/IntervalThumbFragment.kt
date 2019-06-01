package com.mma.contestmanager.views.intervals

import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.text.TextAlignment
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
        label {
            textProperty().bind(valueProperty.asString())
            minWidth = 25.0
            alignment = Pos.CENTER
        }
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

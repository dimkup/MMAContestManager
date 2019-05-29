package com.mma.contestmanager.view.intervals

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class IntervalThumbFragment : Fragment() {
    val minValProperty: SimpleIntegerProperty by param()
    val maxValProperty: SimpleIntegerProperty by param()
    val valueProperty: SimpleIntegerProperty by param()
    val deleteAction: (()->Unit)? by param()
    var value by valueProperty

    override val root = hbox {
        button("-") {
            enableWhen(valueProperty.add(-1).greaterThan(minValProperty))
            action {value--}
        }
        label {textProperty().bind(valueProperty.asString())}
        button("+") {
            enableWhen(valueProperty.add(1).lessThan(maxValProperty))
            action { value++ }
        }
        deleteAction?.let{button("Del") {
            action {deleteAction!!()}
        }}
    }
}

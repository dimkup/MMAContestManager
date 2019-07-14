package com.mma.contestmanager.views

import com.mma.contestmanager.app.*
import com.mma.contestmanager.controllers.RegistrationController
import com.mma.contestmanager.views.intervals.CategoriesFragment
import com.mma.contestmanager.views.intervals.IntervalFragment
import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import tornadofx.*

class CategoryTabView : View() {
    private val controller: RegistrationController by inject()
    override val root = BorderPane()
    private val categoriesBox = pane {}

    init {
        with(root) {
            left = hbox {
                add(IntervalFragment(controller.ageIntervals, messages["age"], MIN_AGE, MAX_AGE, MAX_THUMBS))
                add(
                    IntervalFragment(
                        controller.weightIntervals,
                        messages["weight"],
                        MIN_WEIGHT,
                        MAX_WEIGHT,
                        MAX_THUMBS
                    )
                )
                spacing = 20.0
                paddingAll = 10.0
            }
            center = categoriesBox
        }
        controller.categoriesUpdatedProperty.onChange {
            updateCategories()
        }
        updateCategories()
    }

    private fun updateCategories() {
        categoriesBox.children.clear()
        val fragment = CategoriesFragment(controller.categoriesMap)
        fragment.root.prefHeightProperty().bind(categoriesBox.heightProperty())
        fragment.root.prefWidthProperty().bind(categoriesBox.widthProperty())
        categoriesBox.add(fragment)
    }

}


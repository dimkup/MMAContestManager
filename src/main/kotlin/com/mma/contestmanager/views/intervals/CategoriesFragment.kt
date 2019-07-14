package com.mma.contestmanager.views.intervals

import com.mma.contestmanager.models.Category
import javafx.geometry.HPos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import java.util.*


class CategoriesFragment(private val categoriesMap: NavigableMap<Int, NavigableMap<Int, Category>>) : Fragment() {
    private val headerSize = 20.0
    override val root = gridpane {
        row {
            label("A/W")
            constraintsForRow(0).percentHeight = headerSize

            for ((i, weight) in categoriesMap.firstEntry().value.keys.withIndex()) {
                label(weight.toString()) {
                    style {
                        fontWeight = FontWeight.EXTRA_BOLD
                        //backgroundColor += Color.BEIGE
                        //alignment = Pos.CENTER
                    }

                }
                with(constraintsForColumn(i + 1)) {
                    percentWidth = (100 - headerSize) / categoriesMap.firstEntry().value.size
                    halignment = HPos.CENTER
                }
            }
        }
        var i = 1
        for ((age, weightMap) in categoriesMap) {
            row {
                label(age.toString()) { style { fontWeight = FontWeight.BOLD } }
                for ((_, category) in weightMap) {
                    label { bind(category.sportsmen.sizeProperty) }
                }
                constraintsForRow(i++).percentHeight = (100 - headerSize) / categoriesMap.size
            }
        }

        with(constraintsForColumn(0)) {
            percentWidth = headerSize
            halignment = HPos.CENTER
        }

        isGridLinesVisible = true
        style { backgroundColor += Color.WHITE }
    }
}
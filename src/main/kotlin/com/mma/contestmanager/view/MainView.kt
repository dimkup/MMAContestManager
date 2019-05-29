package com.mma.contestmanager.view

import javafx.scene.control.TabPane
import tornadofx.*
import tornadofx.FX.Companion.messages

class MainView : View(messages["windowTitle"]) {
    override val root = tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab(messages["tabSportsmen"]) {
            label("sport")
        }
        tab(messages["tabCategories"]) {
            add(CategoryTabView())
        }
    }

}
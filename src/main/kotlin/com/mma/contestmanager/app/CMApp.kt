package com.mma.contestmanager.app

import com.mma.contestmanager.views.MainView
import javafx.stage.Stage
import tornadofx.App

class CMApp : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.minHeight = 400.0
        stage.minWidth = 400.0
        super.start(stage)
    }
}
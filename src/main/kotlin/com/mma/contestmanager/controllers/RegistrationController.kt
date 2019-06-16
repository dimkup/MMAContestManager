package com.mma.contestmanager.controllers

import com.mma.contestmanager.models.Sportsman
import javafx.collections.ObservableList
import tornadofx.*

class RegistrationController: Controller() {
    val sportsmen: ObservableList<Sportsman> = ArrayList<Sportsman>().observable()

}
package com.mma.contestmanager.controllers

import com.mma.contestmanager.app.MIN_AGE
import com.mma.contestmanager.app.MIN_WEIGHT
import com.mma.contestmanager.models.Sportsman
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ObservableList
import tornadofx.*

class RegistrationController: Controller() {
    val sportsmen: ObservableList<Sportsman> = ArrayList<Sportsman>().observable()

    val ageIntervals = mutableListOf(
        SimpleIntegerProperty(MIN_AGE + 1),
        SimpleIntegerProperty(15),
        SimpleIntegerProperty(20)).observable()

    val weightIntervals = mutableListOf(
        SimpleIntegerProperty(MIN_WEIGHT + 1),
        SimpleIntegerProperty(15),
        SimpleIntegerProperty(20)).observable()


}
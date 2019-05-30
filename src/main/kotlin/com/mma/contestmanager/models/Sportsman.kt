package com.mma.contestmanager.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

class Sportsman(firstName: String, lastName: String, age: Int, weight: Int) {
    val firstNameProperty = SimpleStringProperty(this, "firstName", firstName)
    val lastNameProperty = SimpleStringProperty(this, "lastName", lastName)
    val ageProperty = SimpleIntegerProperty(age)
    val weightProperty = SimpleIntegerProperty(weight)
}
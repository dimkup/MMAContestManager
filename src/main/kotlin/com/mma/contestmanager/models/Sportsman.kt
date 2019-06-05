package com.mma.contestmanager.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

class Sportsman(id: Int = 0, firstName: String = "", lastName: String = "", age: Int = 0, weight: Int = 0) {
    val firstNameProperty = SimpleStringProperty(this, "firstName", firstName)
    val lastNameProperty = SimpleStringProperty(this, "lastName", lastName)
    val ageProperty = SimpleIntegerProperty(age)
    val weightProperty = SimpleIntegerProperty(weight)
    val idProperty = SimpleIntegerProperty(id)
}
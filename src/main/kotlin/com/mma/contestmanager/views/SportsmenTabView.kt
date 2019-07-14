package com.mma.contestmanager.views

import com.mma.contestmanager.app.MAX_AGE
import com.mma.contestmanager.app.MAX_WEIGHT
import com.mma.contestmanager.app.MIN_AGE
import com.mma.contestmanager.app.MIN_WEIGHT
import com.mma.contestmanager.controllers.RegistrationController
import com.mma.contestmanager.models.Sportsman
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.SelectionMode
import javafx.scene.layout.BorderPane
import tornadofx.*

class SportsmenTabView : View() {
    override val root = BorderPane()
    private val controller: RegistrationController by inject()
    private val sportsmen = controller.sportsmen
    private val addingNewProperty = SimpleBooleanProperty(false)
    private val model = SportsmanModel()
    var counter = 1

    private val sportsmenTable = tableview(sportsmen) {
        column("ID", Sportsman::idProperty)
        column(messages["category"], Sportsman::categoryNameProperty)
        column(messages["firstName"], Sportsman::firstNameProperty)
        column(messages["lastName"], Sportsman::lastNameProperty)
        column(messages["age"], Sportsman::ageProperty)
        column(messages["weight"], Sportsman::weightProperty)
        bindSelected(model)
        selectionModel.selectionMode = SelectionMode.SINGLE
    }

    init {
        model.itemProperty.addListener { _, _, newValue ->
            newValue?.let { addingNewProperty.set(newValue.idProperty.get() == 0) }
        }
        with(root) {
            center {
                add(sportsmenTable)
            }

            right {
                vbox {
                    button(messages["add"]) {
                        action {
                            model.item = Sportsman()
                        }
                        useMaxWidth = true
                        shortcut("Ctrl+N")
                    }
                    form {
                        hiddenWhen { model.empty }
                        fieldset(messages["sportsmanEditFiledsetLabel"]) {
                            addingNewProperty.addListener { _, _, newVal ->
                                this.text =
                                    if (newVal) messages["sportsmanNewFiledsetLabel"]
                                    else messages["sportsmanEditFiledsetLabel"]
                            }
                            field(messages["firstName"]) {
                                textfield(model.firstName).required(message = "First name is required")
                            }
                            field(messages["lastName"]) {
                                textfield(model.lastName).required()
                            }

                            field(messages["age"]) {
                                textfield(model.age) {
                                    filterInput { it.controlNewText.isInt() }
                                }.validator {
                                    when {
                                        it.isNullOrBlank() -> error("Age is required")
                                        it.toInt() < MIN_AGE -> error("Age is too small")
                                        it.toInt() > MAX_AGE -> error("Age is too big")
                                        else -> null
                                    }
                                }
                            }

                            field(messages["weight"]) {
                                textfield(model.weight) {
                                    filterInput { it.controlNewText.isInt() }
                                }.validator {
                                    when {
                                        it.isNullOrBlank() -> error("Weight is required")
                                        it.toInt() < MIN_WEIGHT -> error("Weight is too small")
                                        it.toInt() > MAX_WEIGHT -> error("Weight is too big")
                                        else -> null
                                    }
                                }
                            }

                            hbox {
                                button(messages["reset"]) {
                                    enableWhen(model.dirty)
                                    action {
                                        model.rollback()
                                    }
                                }
                                button(messages["save"]) {
                                    enableWhen(model.dirty.and(model.valid))
                                    action {
                                        model.commit()
                                        val m = model.item
                                        if (m.idProperty.get() == 0) {
                                            m.idProperty.set(counter++)
                                            sportsmen.add(m)
                                            model.item = null
                                            assert(counter < 10000)
                                        }
                                    }
                                }
                                button(messages["del"]) {
                                    enableWhen { model.empty.not().and(addingNewProperty.not()) }
                                    action {
                                        sportsmen.remove(model.item)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


class SportsmanModel : ItemViewModel<Sportsman>() {
    val firstName = bind(Sportsman::firstNameProperty)
    val lastName = bind(Sportsman::lastNameProperty)
    val age = bind(Sportsman::ageProperty)
    val weight = bind(Sportsman::weightProperty)
}
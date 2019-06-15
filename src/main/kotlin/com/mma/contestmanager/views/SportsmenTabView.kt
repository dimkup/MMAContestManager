package com.mma.contestmanager.views

import com.mma.contestmanager.models.Sportsman
import javafx.beans.binding.When
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.SelectionMode
import javafx.scene.layout.BorderPane
import tornadofx.*

class SportsmenTabView : View() {
    override val root = BorderPane()
    val sportsmen =
        mutableListOf(Sportsman(1, "John", "Doe", 20, 60), Sportsman(2, "Jay", "McMaster", 30, 80)).observable()
    private val addingNewProperty = SimpleBooleanProperty(false)
    private val model = SportsmanModel()
    var counter = 5

    private val sportsmenTable = tableview(sportsmen) {
        column("ID", Sportsman::idProperty)
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
                                }.required()
                            }
                            field(messages["weight"]) {
                                textfield(model.weight) {
                                    filterInput { it.controlNewText.isInt() }
                                }.required()
                            }
                            hbox {
                                button("Reset") {
                                    enableWhen(model.dirty)
                                    action {
                                        model.rollback()
                                    }
                                }
                                button("Save") {
                                    enableWhen(model.dirty.and(model.valid))
                                    action {
                                        model.commit()
                                        val m = model.item
                                        if (m.idProperty.get() == 0) {
                                            m.idProperty.set(counter++)
                                            sportsmen.add(m)
                                            model.item = null
                                        }
                                    }
                                }
                                button("Del") {
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
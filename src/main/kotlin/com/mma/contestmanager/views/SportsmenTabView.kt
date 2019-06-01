package com.mma.contestmanager.views

import com.mma.contestmanager.models.Sportsman
import javafx.beans.binding.When
import javafx.scene.control.SelectionMode
import javafx.scene.layout.BorderPane
import tornadofx.*

class SportsmenTabView : View() {
    override val root = BorderPane()
    val sportsmen = mutableListOf(Sportsman(1,"John", "Doe",20,60), Sportsman(2,"Jay", "McMaster",30,80)).observable()
    val model = SportsmanModel()
    var counter = 5

    private val sportsmenTable = tableview(sportsmen) {
                column("ID",Sportsman::idProperty)
                column("First name", Sportsman::firstNameProperty)
                column("Last name", Sportsman::lastNameProperty)
                column("Age", Sportsman::ageProperty)
                column("Weight", Sportsman::weightProperty)
                bindSelected(model)
                selectionModel.selectionMode = SelectionMode.SINGLE
            }

    init {
        with(root) {
            center {
                add(sportsmenTable)
            }

            right {
                vbox {
                    button("New") {
                        action { model.item = Sportsman() }
                        useMaxWidth = true
                        shortcut("Ctrl+N")
                    }
                    form {
                        hiddenWhen { model.empty }
                        fieldset("Edit sportsman") {
                            field("First name") {
                                textfield(model.firstName).required(message = "First name is required")
                            }
                            field("Last name") {
                                textfield(model.lastName).required()
                            }

                            field("Age") {
                                textfield(model.age) {
                                    filterInput { it.controlNewText.isInt() }
                                }.required()
                            }
                            field("Weight") {
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
                                    enableWhen { model.empty.not() }
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
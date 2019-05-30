package com.mma.contestmanager.views

import com.mma.contestmanager.models.Sportsman
import javafx.scene.layout.BorderPane
import tornadofx.*

class SportsmenTabView : View() {
    override val root = BorderPane()
    val sportsmen = listOf(Sportsman("John", "Doe",20,60), Sportsman("Jay", "McMaster",30,80)).observable()
    val model = SportsmanModel()

    init {
        with(root) {
            center {
                tableview(sportsmen) {
                    column("First name", Sportsman::firstNameProperty)
                    column("Last name", Sportsman::lastNameProperty)
                    column("Age", Sportsman::ageProperty)
                    column("Weight", Sportsman::weightProperty)

                    model.rebindOnChange(this) { selectedPerson ->
                        item = selectedPerson ?: Sportsman("aa","bb",1,2)
                    }
                }
            }

            right {
                form {
                    fieldset("Edit sportsman") {
                        enableWhen(!model.empty)
                        field("First name") {
                            textfield(model.firstName)
                        }
                        field("Last name") {
                            textfield(model.lastName)
                        }

                        field("Age") {
                            textfield(model.age) {
                                filterInput { it.controlNewText.isInt() }
                            }
                        }
                        field("Weight") {
                            textfield(model.weight) {
                                filterInput { it.controlNewText.isInt() }
                            }
                        }
                        hbox {
                            button("Reset") {
                                enableWhen(model.dirty)
                                action {
                                    model.rollback()
                                }
                            }
                            button("Save") {
                                enableWhen(model.dirty)
                                action {
                                    model.commit()
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
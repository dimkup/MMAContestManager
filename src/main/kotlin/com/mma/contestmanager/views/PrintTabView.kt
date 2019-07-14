package com.mma.contestmanager.views

import com.mma.contestmanager.controllers.RegistrationController
import javafx.print.PrinterJob
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebEngine
import tornadofx.*




class PrintTabView : View() {
    private val controller: RegistrationController by inject()
    override val root = BorderPane()
    lateinit var webEngine: WebEngine

    init {
        with(root) {
            left = vbox {
                button(messages["refresh"]) {
                    maxWidth = Double.MAX_VALUE
                    action {
                        webEngine.loadContent(generateContent())
                    }
                }
                button(messages["print"]) {
                    maxWidth = Double.MAX_VALUE
                    action {
                        printWebView()
                    }
                }
                paddingAll = 10.0
            }
            center = webview {
                webEngine = this.engine
            }
        }
    }

    private fun generateContent(): String {
        val sb = StringBuilder()
        sb.append("<HTML>")
        sb.append("<HEAD>")

        sb.append(
            """
<style type="text/css">
.sportsmanTable { border-collapse:collapse; }
.sportsmanTable th { background-color:#000;color:white;width:50%; }
.sportsmanTable td, .sportsmanTable th { padding:5px;border:1px solid #000; }
</style>
"""
        )
        sb.append("<HEAD>")
        sb.append("<BODY>")

        for (weightGroup in controller.categoriesMap.values) {
            for (category in weightGroup.values) {
                if (category.sportsmen.size > 0) {
                    sb.append("<H2>")
                    sb.append("${messages["category"]} ${category.name}")
                    sb.append("</H2>")
                    sb.append("<TABLE class=\"sportsmanTable\">")
                    for (sportsman in category.sportsmen) {
                        sb.append("<TR>")
                        sb.append("<TD width=\"30%\">")
                        sb.append("${sportsman.lastNameProperty.get()} ${sportsman.firstNameProperty.get()}")
                        sb.append("</TD>")
                        sb.append("<TD width=\"70%\">")
                        sb.append("</TD>")
                        sb.append("</TR>")
                    }
                    sb.append("</TABLE>")
                    //sb.append("<p style=\"page-break-after: always\">&nbsp;</p>")
                    //sb.append("<p style=\"page-break-before: always\">&nbsp;</p>")
                }
            }
        }

        sb.append("</BODY>")
        sb.append("</HTML>")
        return sb.toString()
    }

    private fun printWebView() {
        val job = PrinterJob.createPrinterJob()
        if (job != null && job.showPrintDialog(root.scene.window)) {
            val success = job.printPage(root.center)
            if (success) {
                job.endJob()
            }
        }
    }
}

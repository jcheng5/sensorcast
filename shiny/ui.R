shinyUI(basicPage(
  verbatimTextOutput("rawdata"),
  includeHTML("scene.html"),
  includeScript("sensorcast.js"),
  tags$div(id="info")
))
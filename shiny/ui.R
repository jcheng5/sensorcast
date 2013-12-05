shinyUI(basicPage(
  includeHTML("scene.html"),
  includeScript("sensorcast.js"),
  tags$div(id="info")
))
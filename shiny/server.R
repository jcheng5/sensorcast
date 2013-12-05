data <- fifo("~/workspace/Sensorcast/node/data", "r")
values <- reactiveValues(vector=c(0, 0, 0), rawdata="Waiting...")

shinyServer(function(input, output, session) {
  observe({
    invalidateLater(200, session)

    chrdata <- readLines(data)
    if (length(chrdata) > 0) {
      values$rawdata <- tail(chrdata, 1)
      values$vector <- as.numeric(strsplit(tail(chrdata, 1), ",")[[1]])
    }
  })

  output$rawdata <- renderText({ as.character(values$vector) })

  observe(
    session$sendCustomMessage("vector", values$vector)
  )
})
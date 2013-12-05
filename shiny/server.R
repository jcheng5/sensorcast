library(httpuv)

values <- reactiveValues(vector=c(0, 0, 0))
onMessage <- function(msg) {
  if (length(msg) == 0)
    return()
  values$vector <- readBin(msg, numeric(), n=12, size=4, endian="big")
  shiny:::flushReact()
}
server <- httpuv:::startUdpServer("224.0.0.0", "0.0.0.0", 5678, onMessage)
stopifnot(!is.null(server))

shinyServer(function(input, output, session) {
  obs <- observe(
    session$sendCustomMessage("vector", values$vector)
  )
  session$onSessionEnded(function() {
    obs$suspend()
  })
})
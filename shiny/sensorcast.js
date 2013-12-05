Shiny.addCustomMessageHandler("vector", function(data) {
  mathbox.set("#vector", {
    data: [[0, 0, 0], data]
  });
});
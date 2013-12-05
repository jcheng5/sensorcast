Shiny.addCustomMessageHandler("vector", function(data) {
  mathbox.set("#vector1", {
    data: [[0, 0, 0], data.slice(0, 3)],
  });
  mathbox.set("#vector2", {
    data: [[0, 0, 0], data.slice(4, 7)]
  });
  mathbox.set("#vector3", {
    data: [[0, 0, 0], data.slice(8, 11)]
  });
});
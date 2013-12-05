var dgram = require('dgram');

var s = dgram.createSocket('udp4');
s.bind(5678, function() {
  s.addMembership('224.0.0.0');
});
s.on('message', function(msg, rinfo) {
  var buf = new Buffer(msg);
  var x = buf.readFloatBE(0);
  var y = buf.readFloatBE(4);
  var z = buf.readFloatBE(8);
  var c = buf.readFloatBE(12);
  var acc = buf.readFloatBE(16);

  /*
  x = roundTo(x, 2);
  y = roundTo(y, 2);
  z = roundTo(z, 2);
  */
  
  console.log(x + ', ' + y + ', ' + z);
});


function roundTo(val, decimals) {
  val = val * Math.pow(10, decimals);
  val = Math.round(val);
  return val / Math.pow(10, decimals);
}
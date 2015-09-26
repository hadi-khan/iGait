'use strict';

var
  express = require('express'),
  app = express();

app.get('/', function(req, res){
  res.send('hello world');
});

app.listen(80, function(){
  console.log('Listening for connections on port 80');
});

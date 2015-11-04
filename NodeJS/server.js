'use strict';

var
  express = require('express'),
  config = require('./server/configure'),
  app = express(),
  mongoose = require('mongoose'),
  morgan = require('morgan');

//set port
app.set('port', process.env.PORT || 80);

//load configure
app = config(app);
app.use(morgan('combined'));


//connecting to mongodb with mongoose
mongoose.connect('mongodb://localhost:27017/mongotest');
mongoose.connection.on('open', function() {
  console.log('Mongoose connected.');
});
mongoose.connection.on('error', function(){
  console.log('error logged');
});

// SERVER START---------------------------
var server = app.listen(app.get('port'), function() {
    console.log('Listening for connections on: http://localhost:' + app.get('port'));
});

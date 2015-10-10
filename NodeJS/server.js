'use strict';

var
  express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  routeAPI = require('./routes/api.js');

app.set('port', process.env.PORT || 80);

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());
// BASIC ROUTES---------------------------

// Route for every connection.
app.use(function(req, res, next){
  console.log('someone is using the api!');
  next();
});

app.get('/', function(req, res){
  res.send('iGait API server!');
});

// API ROUTES-----------------------------
app.use(routeAPI);

// SERVER START---------------------------
app.listen(app.get('port'), function(){
  console.log(`Listening for connections on port ${app.get('port')}`);
});

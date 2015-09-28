'use strict';

var
  express = require('express'),
  app = express(),
  bodyParser = require('body-parser');


var port = process.env.PORT || 80;

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

// AUTHENTICATION ROUTES------------------
app.use('/authentication', function(req, res){
  res.send('Here is your token');
});

// API ROUTES-----------------------------
var routes = express.Router();

// Token verification before allowing use of API resources.
routes.use(function(req, res, next){
  console.log('This token looks ok, let that user through.');
  next();
});

app.use('/api', routes);

// SERVER START---------------------------
app.listen(port, function(){
  console.log(`Listening for connections on port ${port}`);
});

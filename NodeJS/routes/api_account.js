'use strict';

var
  express = require('express'),
  app = express();

module.exports = (function(){
  app.route('/authentication')
    .post(function(req, res){
      res.send('Here is your token');
    });

  app.route('/recovery')
    .post(function(req, res){

    });

  app.route('/register')
    .post(function(req, res){

    });

  return app;
})();

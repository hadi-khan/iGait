'use strict';

var
  express = require('express'),
  app = express();

module.exports = (function(){
  let router = express.Router();

  router.route('/authentication')
    .post(function(req, res){
      res.send('Here is your token');
    });

  router.route('/recovery')
    .post(function(req, res){

    });

  router.route('/register')
    .post(function(req, res){

    });

  app.use('/account', router);  

  return app;
})();

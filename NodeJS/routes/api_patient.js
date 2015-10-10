'use strict';

var
  express = require('express'),
  app = express();

module.exports = (function(){
  app.route('/patient')
    .post(function(req, res){
      res.send('All the patients');
    });

  return app;
})();

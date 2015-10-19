'use strict';

var
  express = require('express'),
  app = express(),
  apiAccount = require('./api_account.js'),
  apiPatient = require('./api_patient.js'),
  jwt = require('jsonwebtoken');

module.exports = (function(){
  app.use('/api', apiAccount);

  //Test function for verification of request
  app.use('/api', function(req, res, next){
    let token = req.body.token;
    jwt.verify(token, apiAccount.get('secret'), function(err, decoded){
      if (err){
        return res.json({success: false});
      }
      else{
        res.json({decoded: decoded});
      }
    });
  });

  app.use('/api', apiPatient);

  return app;
})();

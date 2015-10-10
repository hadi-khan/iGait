'use strict';

var
  express = require('express'),
  app = express(),
  apiAccount = require('./api_account.js'),
  apiPatient = require('./api_patient.js');

module.exports = (function(){
  app.use('/api', apiAccount);
  app.use('/api', apiPatient);
  return app;
})();

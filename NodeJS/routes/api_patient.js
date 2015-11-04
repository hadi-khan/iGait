'use strict';

var
  express = require('express'),
  app = express(),
  Patient = require('../models/patients');

module.exports = (function(){
  app.route('/patient')
    .post(function(req, res){
      let patients = Patient.find();

      res.json(patients);
    });

  return app;
})();

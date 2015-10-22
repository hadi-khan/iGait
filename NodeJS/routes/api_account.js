'use strict';

var
  express = require('express'),
  app = express(),
  doctors = require('../controllers/doctorcontroller'),
  jwt = require('jsonwebtoken'),
  Doctor = require('../models/doctors');

app.set('secret', 'secret1234');

module.exports = (function(){
  let router = express.Router();

  router.route('/authentication')
    .post(function(req, res){
      //verify account / password
/*
      var token = jwt.sign({'name': 'Ross'}, app.get('secret'));

      res.json({token: token});*/

      let credentials = req.body;

      Doctor.findOne({email: credentials.email}, function(err, doctor){
        if (err) throw err;

        if (!doctor){
          res.json({message: 'rejected'});          
        }
        else if (doctor){
          if (doctor.password != credentials.password){
            res.json({message: 'wrong password'});
          }
          else{
            let token = jwt.sign(doctor, app.get('secret'));

            res.json({token: token});
          }
        }
      });
    });

  router.route('/recovery')
    .post(function(req, res){

    });

  router.route('/register')
    .post(function(req, res){
      console.log(req.body.doctor);

      let doctor = Doctor(req.body.doctor);
      doctor.save(function(err){
        if (err){
          console.error(err);
          res.send(err);
        }
        else{
          res.send('Doctor saved!');
        }
      });
    });

  app.use('/account', router);

  return app;
})();

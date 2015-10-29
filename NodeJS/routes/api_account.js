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

// Log in with an email / password combination and receive a token.
  router.route('/authentication')
    .post(function(req, res){
      let credentials = req.body;

      Doctor.findOne({email: credentials.email}, function(err, doctor){
        if (err) throw err;

        if (!doctor){
          res.json({success: 'false', message: 'Account does not exist.'});
        }
        else if (doctor){
          if (doctor.password != credentials.password){
            res.json({success: 'false', message: 'Wrong password.'});
          }
          else{
            let token = jwt.sign(doctor, app.get('secret'));

            doctor.accessToken = token;
            doctor.save(function(err){
              if(err){
                console.error(err);
                res.json({success: 'false', message: err});
              }
            });

            res.json({success: 'true', message: token});
          }
        }
      });
    });

// Allows a user to change their password.
  router.route('/recovery')
    .post(function(req, res){

    });
// Register a new user with the system, will be a doctor.
  router.route('/register')
    .post(function(req, res){
      console.log(req.body.doctor);

      let doctor = Doctor(req.body.doctor);
      doctor.save(function(err){
        if (err){
          console.error(err);
          res.json({success: 'false', message: err});
        }
        else{
          res.json({success: 'true', message: 'Account created'});
        }
      });
    });

  app.use('/account', router);

  return app;
})();

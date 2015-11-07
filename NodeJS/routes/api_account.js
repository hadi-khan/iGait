'use strict';

var
  express = require('express'),
  app = express(),
  doctors = require('../controllers/doctorcontroller'),
  dbmgr = require('../controllers/bridge.js'),
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
            doctor.accessToken = null;
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
        var db = dbmgr.bridge(1);
        var result;
        db.getDoctorByEmail( req.body.email, function callback(result){
          //do something here
          if(result.success === 'false_error'){
            //query came back with error
            //console.log("result === false_error");
            res.json(result);
          }
          else if(result.success === 'false'){
            //this is our good case: This doctor does not existed
            //now try to create the doctor
            db.createDoctor(
                req.body.email,
                req.body.password,
                req.body.firstname,
                req.body.lastname,
                req.body.mobilenumber,
                req.body.officenumber,
                req.body.officeaddress,
                function callback2(result){
                  if(result.success === 'false_error'){
                    //query came back with error
                    //console.log("result === false_error");
                    res.json(result);
                  }
                  else if(result.success === 'true'){
                    //successfully created doctor
                    //console.log("result === true");
                    res.json(result);
                  }
                  else{
                    //something went horribly wrong
                    //console.log("result.success === false_wrong");
                    res.json({success: 'false_wrong', function: '/createDoctor-createDoctor', message: 'This shouldnt have happen. Something went very wrong'});
                  }
                }
            );
          }
          else if(result.success === 'true'){
            //this is our bad case: This doctor already existed
            //have to switch success to false and display correct message
            //console.log("result === true");
            res.json({success: 'false', function: '/createDoctor-getDoctorByEmail', message: 'Doctor Already Exists'});
          }
          else{
            //something went horribly wrong
            //console.log("result.success === false_wrong");
            res.json({success: 'false_wrong', function: '/createDoctor-getDoctorByEmail', message: 'This shouldnt have happen. Something went very wrong'});
          }
        });
      });

  //Adds Kevin to doctor db
  //post nothing to make it work
  router.route('/addKevin')
      .post(function(req, res){
        var db = dbmgr.bridge(1);
        var result;
        db.getDoctorByEmail( "kevin.kocian@mavs.uta.edu", function callback(result){
          //do something here
          if(result.success === 'false_error'){
            //query came back with error
            //console.log("result === false_error");
            res.send(result);
          }
          else if(result.success === 'false'){
            //this is our good case: This doctor does not existed
            //now try to create the doctor
            db.addKevin(function callback2(result){
                  //console.log("result in api: " + result.message);
                  if(result.success === 'false_error'){
                    //query came back with error
                    //console.log("result === false_error");
                    res.send(result);
                  }
                  else if(result.success === 'true'){
                    //successfully created doctor
                    //console.log("result === true");
                    res.send(result);
                  }
                  else{
                    //something went horribly wrong
                    //console.log("result.success === false_wrong");
                    res.send({success: 'false_wrong', function: '/addKevin-createDoctor', message: 'This shouldnt have happen. Something went very wrong'});
                  }
                }
            );
          }
          else if(result.success === 'true'){
            //this is our bad case: This doctor already existed
            //have to switch success to false and display correct message
            //console.log("result === true");
            res.send({success: 'false', function: '/addKevin-getDoctorByEmail', message: 'Doctor Already Exists'});
          }
          else{
            //something went horribly wrong
            //console.log("result.success === false_wrong");
            res.send({success: 'false_wrong', function: '/addKevin-getDoctorByEmail', message: 'This shouldnt have happen. Something went very wrong'});
          }
        });
      });

  app.use('/account', router);

  return app;
})();

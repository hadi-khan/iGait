'use strict';

var
    express = require('express'),
    app = express(),
    doctors = require('../controllers/doctorcontroller'),
    dbmgr = require('../controllers/bridge.js'),
    jwt = require('jsonwebtoken'),
    Models = require('../models');

app.set('secret', 'secret1234');

module.exports = (function(){
    let router = express.Router();

// Log in with an email / password combination and receive a token.
    router.route('/authentication')
        .post(function(req, res){
            let credentials = req.body;

            Models.Doctors.findOne({email: credentials.email}, function(err, doctor){
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
            //Doctors refers to the var in /models/index.js that saves the path to the doctors.js file
            var newDoctor = new Models.Doctors({
                email: req.body.email,
                password: req.body.password,
                name: {
                    first: req.body.firstname,
                    last: req.body.lastname
                },
                contact: {
                    mobilenumber: req.body.mobilenumber,
                    officenumber: req.body.officenumber,
                    officeaddress: req.body.officeaddress
                }
            });
            db.createDoctor(newDoctor, function callback(result){
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
                        res.json({success: 'false_wrong', function: '/createDoctor-createDoctor', message: 'This shouldnt have happen. Something went very wrong'});}
                }
            );
        });
    
    router.route('/removeDoctor')
        .post(function(req, res){
            var db = dbmgr.bridge(1);
            var result;
            db.removeDoctor(
                req.body.email,
                function callback(result){
                    //console.log("result in api: " + result.message);
                    if(result.success === 'true'){
                        //successfully removed user
                        //console.log("result.success === false_error");
                        res.send(result);
                    }
                    else if(result.success === 'false'){
                        //Doctor did not exist
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else if(result.success === 'false_error'){
                        //query came back with error
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else if(result.success === 'false_wrong'){
                        //something horrible went wrong in the database
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else{
                        //something went horribly wrong in api
                        res.send({success: 'false_wrong', function: '/removeDoctor-removeDoctor', message: 'This shouldnt have happen. Something went very wrong'});
                    }
                }
            );
        });

app.use('/account', router);

return app;
})();

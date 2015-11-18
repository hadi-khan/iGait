/**
 * Created by spart on 11/18/2015.
 */

'use strict';
let express = require('express');
let Security = require('../class/Security');
let dbmgr = require('../controllers/bridge');

let db = dbmgr.bridge(1);
let router = express.Router();

// Log in with an email / password combination and receive a token.
router.route('/authentication')
    .post(function(req, res){
        let credentials = req.body;

        db.getDoctorByEmail(function(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }

            if(!doctor){
                res.json({success: 'false', message: 'account does not exist'});
            }else if(doctor){
                if(Security.verifyPassword(credentials.password, doctor.password)){
                    let token = Security.sign(doctor);

                    res.json({success: 'true', message: token});
                }else{
                    res.json({success: 'false', message: 'wrong password'});
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
        //Doctors refers to the var in /models/index.js that saves the path to the doctors.js file
        var newDoctor = new Models.Doctors({
            email: req.body.email,
            password: Security.hashPassword(req.body.password),
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

module.exports = router;
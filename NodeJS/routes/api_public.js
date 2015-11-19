/**
 * Created by spart on 11/18/2015.
 */

'use strict';
let express = require('express');
let Security = require('../class/Security');
let dbmgr = require('../controllers/bridge');
let Models = require('../models');

let db = dbmgr.bridge(1);
let router = express.Router();

// Log in with an email / password combination and receive a token.
router.route('/authentication')
    /**
     * POST /api/authentication
     */
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
    /**
     * POST /api/register
     */
    .post(function(req, res){
        //Doctors refers to the var in /models/index.js that saves the path to the doctors.js file
        let newDoctor = Models.Doctors(req.body);
        newDoctor.password = Security.hashPassword(newDoctor.password);

        db.createDoctor(newDoctor, reply);
        function reply(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            } else if(doctor){
                res.json({success: 'true', message: doctor});
            }
        }
    });

module.exports = router;

/**
 * Created by spart on 11/18/2015.
 */

'use strict';
let express = require('express');
let Security = require('../class/Security');
let dbmgr = require('../db/dbms');
let Models = require('../models');

//let db = dbmgr.bridge(1);
let db = new dbmgr();
let router = express.Router();

// Log in with an email / password combination and receive a token.
router.route('/authentication')
    /**
     * @api {post} /api/authentication Login with email and password
     * @apiName PostPublic
     * @apiGroup Public
     *
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} email     user email
     * @apiParam {String} password  user password
     *
     * @apiSuccessExample {json} Success-Response:
     *  {
     *      "success": "true",
     *      "message": {
     *          "_id": "5668431d462d814c6cfa246a",
     *          "email": "jesus.linares@mavs.uta.edu",
     *          "password": "�bE�f\u001aX���.a05937690ee24ea1f22ec961638f5b6a96b61f40dce2ff96ee818c15ce56aed7",
     *          "accessToken": "",
     *          "__v": 0,
     *          "contact": {
     *              "officenumber": 2222222222,
     *              "officeaddress": "701 S Nedderman Dr",
     *              "state": "tx",
     *              "city": "arlington",
     *              "zipcode": 76019
     *          },
     *          "name": {
     *              "first": "jesus",
     *              "last": "linares"
     *          },
     *          "date_created": "2015-12-09T15:05:01.245Z"
     *      }
     *  }
     */
    .post(function(req, res){
        let credentials = req.body;

        db.getDoctorByEmail(credentials.email, function(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }

            if(!doctor){
                res.json({success: 'false', message: 'account does not exist'});
            }else if(doctor){
                if(Security.verifyPassword(credentials.password, doctor.password)){
                    let token = Security.sign(doctor);
                    res.header('Authorization', token);
                    res.json({success: 'true', message: doctor});
                }else{
                    res.json({success: 'false', message: 'wrong password'});
                }
            }
        });
    });

// Allows a user to change their password.
router.route('/reset')
    /**
     * @api {post} /api/reset Reset the users password
     * @apiDescription This should be changed to use email verification asap.
     * @apiName PostPublicReset
     * @apiGroup Public
     *
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} password  password
     * @apiParam {String} email     email
     */
    .post(function(req, res){
        let password = req.body.password;
        let email = req.body.email;

        db.getDoctorByEmail(email, changePassword);
        function changePassword(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }
            doctor.password = Security.hashPassword(password);
            doctor.save(reply);
        }
        function reply(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            } else if(doctor){
                res.json({success: 'true', message: 'password updated'});
            }
        }
    });
// Register a new user with the system, will be a doctor.

router.route('/register')
    /**
     * @api {post} /api/register Register a new user
     * @apiName PostPublicRegister
     * @apiGroup Public
     *
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} email                    user email
     * @apiParam {String} password                 password
     * @apiParam {Object} name                     name info
     * @apiParam {String} name.first               first name
     * @apiParam {String} name.last                last name
     * @apiParam {Object} contact                  contact info
     * @apiParam {Number} contact.officenumber     office number
     * @apiParam {String} contact.officeaddress    address
     * @apiParam {String} contact.state            state
     * @apiParam {String} contact.city             city
     * @apiParam {Number} contact.zipcode          zipcode
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

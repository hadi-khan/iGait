'use strict';

let express = require('express');
let dbmgr = require('../db/dbms.js');
let Security = require('../class/Security');


let db = new dbmgr();
let router = express.Router();

router.route('/account')
    /**
     * @api {put} /api/account Update an existing User
     * @apiName PutAccount
     * @apiGroup Account
     *
     * @apiUse Headers
     *
     * @apiSuccess {String} success                 true
     * @apiSuccess {Object} message                 User data
     * @apiSuccess {String} message.email           User email
     * @apiSuccess {String} message.password        User password, hashed
     * @apiSuccess {Object} message.name            User   name
     * @apiSuccess {String} .name.first             User first name
     * @apiSuccess {String} .name.last              User last name
     * @apiSuccess {Object} message.contact         User contact info
     * @apiSuccess {Number} .contact.officenumber   User office number
     * @apiSuccess {String} .contact.officeaddress  User office address
     * @apiSuccess {String} .contact.state          State
     * @apiSuccess {String} .contact.city           City
     * @apiSuccess {Number} .contact.zipcode        Zipcode
     *
     * @apiSuccessExample {json} Success-Response:
     *      {
     *          "_id": "5668431d462d814c6cfa246a",
     *          "email": "john@mavs.uta.edu",
     *          "password": "apwoijepofiajw.ajwieojfapowijefopiajwioejfoiajwoejfaiwejf",
     *          "name" :{
     *              "first": "John",
     *              "last": "Doe"
     *          },
     *          "contact": {
     *              "officenumber": 123456789,
     *              "officeaddress": "1000 Texas Drive",
     *              "state": "Texas",
     *              "city": "Arlington",
     *              "zipdoce": 76013
     *          }
     *          "date_created": "2015-12-09T15:05:01.245Z"
     *      }
     */
    .put(function (req, res){
        let changes = req.body;
        if(changes.password) {
            changes.password = Security.hashPassword(changes.password);
        }
        let doctorId = req.header('id');

        db.updateDoctor(doctorId, changes, reply);
        function reply(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }else if(doctor){
                res.json({success: 'true', message: doctor});
            }
        }
    });

module.exports = router;


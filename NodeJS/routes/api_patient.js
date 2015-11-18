'use strict';

let express = require('express');
let dbmgr = require('../controllers/bridge');
let Models = require('../models');
let db = dbmgr.bridge(1);
let router = express.Router();

router.route('/patient')
    .get(function(req, res){
        let authorization = req.header('Authorization');
        let username = authorization.username;

        db.getDoctorByEmail(username, searchPatients);

        function searchPatients(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }else if(doctor) {
                db.getDoctorPatients(doctor.objectId, reply);
            }
        }

        function reply(err, patients){
            if(err){
                res.json({success: 'false', message: err});
            } else if(patients){
                res.json({success: 'true', message: patients});
            }
        }
    })
    .post(function(req, res){
        let newPatient = Models.Patients(req.body);

        newPatient.save(reply);

        function reply(err, patient){
            if(err){
                res.json({success: 'false', message: err});
            } else if(patient){
                res.json({success: 'true', message: patient});
            }
        }
    });

router.route('/patient/:ObjectId')
    .put(function(req, res){

    })
    .delete(function(req,res){

    });

module.exports = router;

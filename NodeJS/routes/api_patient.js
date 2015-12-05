'use strict';

let express = require('express');
let dbmgr = require('../db/dbms');
let Models = require('../models');
//let db = dbmgr.bridge(1);
let db = new dbmgr();
let router = express.Router();

const PATIENT_DELETE_MESSAGE = 'patient deleted';
//TODO implement the new db function for finding a patient
router.route('/patient')
    .get(function(req, res){
        let doctorId = req.header('id');
        //TODO may have to wrap id with ObjectId in bridge
        db.getDoctorPatients(doctorId, reply);
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
        //TODO May have to wrap this with ObjectId inside of bridge.
        newPatient.doctor = req.header('id');

        db.createPatient(newPatient, reply);
        function reply(err, patient){
            if(err){
                res.json({success: 'false', message: err});
            } else if(patient){
                res.json({success: 'true', message: patient});
            }
        }
    });

router.route('/patient/:id')
    .put(function(req, res){
        let id = req.params.id;
        let changes = req.body;

        db.updatePatient(id, changes, reply);
        function reply(err, patient){
            if(err){
                res.json({success: 'false', message: err});
            } else if(patient){
                res.json({success: 'true', message: 'patient updated'});
            }
        }
    })
    .delete(function(req, res){
        let id = req.params.id;

        db.removePatient(id, reply);
        function reply(err, pat){
            if(err){
                res.json({success: 'false', message: err});
            } else if(pat){
                res.json({success: 'true', message: PATIENT_DELETE_MESSAGE});
            }
        }
    });

router.route('/patient/health/:id')
    .get(function(req, res){
        let patientID = req.params.id;

        db.getPatientsHealth(patientID, reply);
        function reply(err, health){
            if(err){
                res.json({success: 'false', message: err});
            } else if(health){
                res.json({success: 'true', message: health});
            }
        }
    });

module.exports = router;

'use strict';

let express = require('express');
let dbmgr = require('../controllers/bridge');
let Models = require('../models');
let db = dbmgr.bridge(1);
let router = express.Router();

const PATIENT_DELETE_MESSAGE = 'patient deleted';
//TODO implement the new db function for finding a patient
router.route('/patient')
    .get(function(req, res){
        let email = req.header('email');

        db.getDoctorByEmail(email, searchPatients);

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
        let email = req.header('Authorization').email;

        db.getDoctorByEmail(email, assignPatient);

        function assignPatient(err, doctor){
            if(err){
                res.json({success: 'false', message:err});
            }
            newPatient.doctor = doctor.objectId;

            db.createPatient(newPatient, reply);
            function reply(err, patient){
                if(err){
                    res.json({success: 'false', message: err});
                } else if(patient){
                    res.json({success: 'true', message: patient});
                }
            }
        }
    });

router.route('/patient/:id')
    .put(function(req, res){
        let id = req.params.id;

        db.getPatientByObjectId(id, modify);
        function modify(err, patient){
            if(err){
                res.json({success: 'false', message: err});
            }

            let updates = req.body;
            db.updatePatient(email, updates, reply);
            function reply(err, patient){
                if(err){
                    res.json({success: 'false', message: err});
                }else if(patient){
                    res.json({success: 'true', message: patient});
                }
            }
        }
    })
    .delete(function(req,res){
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

module.exports = router;

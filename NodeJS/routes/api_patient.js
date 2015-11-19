'use strict';

let express = require('express');
let dbmgr = require('../controllers/bridge');
let Models = require('../models');
let db = dbmgr.bridge(1);
let router = express.Router();

const PATIENT_DELETE_MESSAGE = 'patient deleted';

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

router.route('/patient/:email')
    .put(function(req, res){
        let email = req.params.email;

        db.getPatientByEmail(email, modify);
        function modify(err, patient){
            if(err){
                res.json({success: 'false', message: err});
            }
            //
            //if(req.body.email){
            //    patient.email = req.body.email;
            //}
            //if(req.body.password){
            //    patient.password = req.body.password;
            //}
            //if(req.body.name){
            //    patient.name = req.body.name;
            //}
            //if(req.body.dateOfBirth){
            //    patient.dateOfBirth = req.body.dateOfBirth;
            //}
            //if(req.body.admissionsDate){
            //    patient.admissionsDate = req.body.admissionsDate;
            //}
            //if(req.body.address){
            //    patient.address = req.body.address;
            //}
            //if(req.body.priority){
            //    patient.priority = req.body.priority;
            //}
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
        let id = req.params.email;
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

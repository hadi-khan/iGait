'use strict';

let express = require('express');
let dbmgr = require('../db/dbms');
let Models = require('../models');
let db = new dbmgr();
let router = express.Router();

const PATIENT_DELETE_MESSAGE = 'patient deleted';
//TODO implement the new db function for finding a patient
router.route('/patient')
    /**
     * @api {get} /api/patient Get all patients for user
     * @apiName GetPatient
     * @apiGroup Patient
     *
     * @apiUse Headers
     */
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
    /**
     * @api {post} /api/patient Create a new patient
     * @apiName PostPatient
     * @apiGroup Patient
     *
     * @apiUse Headers
     *
     * @apiParam {Object} name                      name info
	 * @apiParam {String} name.first                first name
	 * @apiParam {String} name.last                 last name
     * @apiParam {Date} dateOfBirth                 date of birth
     * @apiParam {String} gender                    male or female
     * @apiParam {String} contact                   contact info
	 * @apiParam {String} contact.email             patient email
	 * @apiParam {String} contact.mobilenumber      patient mobile
	 * @apiParam {String} contact.address           patient address
	 * @apiParam {String} contact.state             state
	 * @apiParam {String} contact.city              city
	 * @apiParam {String} contact.zipcode           zipcode
     * @apiParam {Boolean} priority                 mark patient as priority
     * @apiParam {Object} expectedWalkTime          walking time info
	 * @apiParam {Number} expectedWalkTime.hour     hours of walking time
	 * @apiParam {Number} expectedWalkTime.minute   minutes of walking time
	 * @apiParam {Number} expectedWalkTime.second   seconds of walking time
     */
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
    /**
     * @api {put} /api/patient/patientID Update a current patient
     * @apiName PutPatient
     * @apiGroup Patient
     *
     * @apiUse Headers
     *
     * @apiParam {String} patientID    Patient object id
     *
     * @apiSuccess {String} success     true
     * @apiSuccess {String} message     patient updated
     *
     * @apiSuccessExample {json} Success-Response:
     *   {
     *       "success": "true",
     *       "message": "patient updated"
     *   }
     *
     */
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
    /**
     * @api {delete} /api/patient/patientID Delete a current patient
     * @apiName DeletePatient
     * @apiGroup Patient
     *
     * @apiUse Headers
     *
     * @apiParam {String} patientID     Patient object id
     *
     * @apiSuccess {String} success     true
     * @apiSuccess {String} message     patient deleted
     *
     * @apiSuccessExample {json} Success-Response:
     *   {
     *     "success": "true",
     *     "message": "patient deleted"
     *   }
     */
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
    /**
     * @api {get} /api/patient/health/patientID Get patient's health information
     * @apiName GetPatientHealth
     * @apiGroup Patient
     *
     * @apiUse Headers
     *
     * @apiParam {String} patientID     String representing object id in patient
     *
     * @apiSuccess {Number} health              Number representing health of patient (0 - 3)
     * @apiSuccess {Date} start_time            Start date of the health recording
     * @apiSuccess {Date} end_time              End date of the health recording
     * @apiSuccess {String} patient             String representing the object id of the patient
     * @apiSuccess {String} skeleton_location   File location of skeleton rendering.
     * @apiSuccess {String} video_location      File location of the raw video recording.
     *
     *
     *
     */
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

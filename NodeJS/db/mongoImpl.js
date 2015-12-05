
/**
 * Created by kevin on 11/7/2015.
 */
"use strict";
var mongoose = require('mongoose'),
    path = require('path'),
    Models = require('../models'),
    Schema = mongoose.Schema,
    ObjectId = mongoose.Schema.Types.ObjectId; //used to reference objectID

//let databaseM = require('./dbms');
//import databaseManager from './dbms';

class mongoImpl{
    constructor() {
        //super();
        this._dbAddress = 'mongodb://localhost:27017/mongotest';
        this._dbConnectedMessage = 'Mongoose connected on localhost:27017';
        //return this;
    }

    connectDB() {
    //connecting to mongodb with mongoose
    mongoose.connect(this._dbAddress);
    mongoose.connection.on('open', function() {
        console.log(this._dbConnectedMessage);
    });
    mongoose.connection.on('error', function(){
        console.log('error: connecting to Mongoose failed: ');
    });
    mongoose.connection.on('disconnected', function(){
        console.log('Disconnected from Mongoose');
    });
    process.on('SIGINT', function() {
        mongoose.connection.close(function () {
            console.log('moongose default connection disconnected through app termination');
            process.exit(0);
        });
    });
}
    disconnectDB() {
    process.on('SIGINT', function() {
        mongoose.connection.close(function () {
            console.log('moongose default connection disconnected through app termination');
            process.exit(0);
        });
    });
    mongoose.connection.close();
}
    getDoctorByObjectID(reqObjectID, callback){
        reqObjectID = ObjectId(reqObjectID);
        Models.Doctors.findOne({_id:reqObjectID}, function(err,doc){
            callback(err,doc);
        });
    }
    getDoctorByEmail(reqEmail, callback){
        Models.Doctors.findOne({email:reqEmail}, function(err,doc){
            callback(err,doc);
        });
    }
    //gets patient by objectID and returns the Patient
    getPatientByObjectID(reqObjectID, callback){
    reqObjectID = ObjectId(reqObjectID);
    Models.Patients.findOne({_id:reqObjectID}, function(err,pat){
        callback(err,pat);
    });
}
    //gets health by objectID and returns the Health
    getHealthByObjectID(reqObjectID, callback){
    reqObjectID = ObjectId(reqObjectID);
    Models.Health.findOne({_id:reqObjectID}, function(err,hea){
        callback(err,hea);
    });
}
    //gets all patients that refer to doctor's reqObjectID and returns all patients
    getDoctorPatients(reqObjectID, callback){
    Models.Patients.find({doctor:reqObjectID}, function(err,pat){
        callback(err,pat);
    });
}
    //gets all health that refer to patient's reqObjectID and returns all health
    getPatientsHealth(reqObjectID, callback){
    //may need to use next line
    //reqObjectID = ObjectId(reqObjectID);
    Models.Health.find({patient:reqObjectID}, function(err,hea){
        callback(err,hea);
    });
}
    //creates Patient given the Patient Object
    createPatient(reqPatObj, callback){
    reqPatObj.save(function(err, pat){
        callback(err,pat);
    });
}
    //creates Doctor given the Doctor Object
    createDoctor(reqDocObj, callback){
    reqDocObj.save(function(err, doc){
        callback(err,doc);
    });
}
    //creates Health given the Health Object
    createHealth(reqHeaObj, callback){
    reqHeaObj.save(function(err, hea){
        callback(err,hea);
    });
}
    //updates Doctor given the doctor's ObjectID
    updateDoctor(reqObjectID, reqUpdate, callback){
    //reqObjectID = ObjectId(reqObjectID)
    Models.Doctors.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, doc) {
        callback(err,doc);
    });
}
    //updates Patient given the patient's ObjectID
    updatePatient(reqObjectID, reqUpdate, callback){
    //reqObjectID = ObjectId(reqObjectID)
    Models.Patients.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, pat) {
        callback(err,pat);
    });
}
    //updates Health given the health's ObjectID
    updateHealth(reqObjectID, reqUpdate, callback){
    //reqObjectID = ObjectId(reqObjectID)
    Models.Health.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, hea) {
        callback(err,hea);
    });
}
    //removes Doctor given the Doctor's ObjectID
    removeDoctor(reqObjectID, callback){
    //reqObjectID = ObjectId(reqObjectID)
    Models.Doctors.findOneAndRemove({_id: reqObjectID}, function(err, doc){
        callback(err,doc);
    });
}
    //removes Patient given the Patient's ObjectID
    removePatient(reqObjectID, callback){
    Models.Patients.findOneAndRemove({_id: reqObjectID}, function(err, pat){
        callback(err, pat);
    });
}
    //removes Health given the Health's ObjectID
    removeHealth(reqObjectID, callback){
    //reqObjectID = ObjectId(reqObjectID)
    Models.Health.findOneAndRemove({_id: reqObjectID}, function(err, hea){
        callback(err, hea);
    });
}
}
module.exports = mongoImpl;


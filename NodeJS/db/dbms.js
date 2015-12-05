
/**
 * Created by kevin on 11/7/2015.
 */
"use strict";
var mongoose = require('mongoose'),
    path = require('path'),
    Models = require('../models'),
    Schema = mongoose.Schema,
    ObjectId = mongoose.Schema.Types.ObjectId; //used to reference objectID

/** Class representing the database manager*/
class databaseManager{
    /**
     * Constructs the database. 1 === mongo
     */
    constructor (){
        "use strict";
        this._impl = null;
        this._SetImplementor(this._EstablishImplementor(1));
    }
    /**
     * sets the implementation of a database.
     * @param {number} implementor - The number for which implementation to use.
     */
    _SetImplementor(implementor){
        "use strict";
        this._impl = null;
        if(implementor){
            this._impl = implementor;
        }
    }
    /**
     * sets the implementation of a database.
     * @param {number} implementor - The number for which implementation to use.
     * @return {
     */
    _EstablishImplementor(whichdb){
        "use strict";
        if(whichdb === 1) {
            let mongoImpl = require('./mongoImpl');
            return new mongoImpl();//mongoose / mongodb
        }
        else if(false) { //would change if implemented other database's in future
            return new ImplementationTwo();
        }
        else{
            console.log("A database not implemented was called on");
            return;
        }
    }
    getDoctorByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByObjectID) {
            this._impl.getDoctorByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    getPatientByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientByObjectID){
            this._impl.getPatientByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    getHealthByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getHealthByObjectID){
            this._impl.getHealthByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    getDoctorPatients(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorPatients) {
            this._impl.getDoctorPatients(reqObjectID, callback);     // Forward request to implementor
        }
    }
    getPatientsHealth(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientsHealth) {
            this._impl.getPatientsHealth(reqObjectID, callback);     // Forward request to implementor
        }
    }
    createDoctor(reqDocObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createDoctor){
            this._impl.createDoctor(reqDocObj, callback);     // Forward request to implementor
        }
    }
    createPatient(reqPatObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createPatient){
            this._impl.createPatient(reqPatObj, callback);     // Forward request to implementor
        }
    }
    createHealth(reqHeaObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createHealth){
            this._impl.createHealth(reqHeaObj, callback);     // Forward request to implementor
        }
    }
    updateDoctor(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateDoctor){
            this._impl.updateDoctor(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    updatePatient(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updatePatient){
            this._impl.updatePatient(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    updateHealth(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateHealth){
            this._impl.updateHealth(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    removeDoctor(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeDoctor){
            this._impl.removeDoctor(reqObjectID, callback);     // Forward request to implementor
        }
    }
    removePatient(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removePatient){
            this._impl.removePatient(reqObjectID, callback);     // Forward request to implementor
        }
    }
    removeHealth(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeHealth){
            this._impl.removeHealth(reqObjectID, callback);     // Forward request to implementor
        }
    }
    connectDB() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.connectDB){
            this._impl.connectDB();     // Forward request to implementor
        }
    }
    disconnectDB() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.disconnectDB){
            this._impl.disconnectDB();     // Forward request to implementor
        }
    }
}

//export default new databaseManager();

module.exports = databaseManager;



/**
 * Created by kevin on 11/7/2015.
 */
"use strict";
let mongoose = require('mongoose');
let path = require('path');
//let Models = require('../models');
//let Schema = mongoose.Schema;
//let ObjectId = mongoose.Schema.Types.ObjectId; //used to reference objectID

/** @classdesc Abstract class representing a database manager
 * @class*/
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
     * The current database implementations:
     * 1 === monogdb / mongoose
     * @param {number} whichdb - The number to determine which database implementation to use.
     * @return {class} implementation of the database
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
            return undefined;
        }
    }
    /**
     * Interface method to get doctor by mongo's randomly generated hash Object ID.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getDoctorByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByObjectID) {
            this._impl.getDoctorByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to get doctor by his email. The email he uses is
     * unique, and there will not be another doctor with the same email.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqEmail - The string of the doctor's email.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getDoctorByEmail(reqEmail, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByEmail) {
            this._impl.getDoctorByEmail(reqEmail, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to get patient by mongo's randomly generated hash Object ID.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getPatientByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientByObjectID){
            this._impl.getPatientByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to get a health record by mongo's randomly generated hash Object ID.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getHealthByObjectID(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getHealthByObjectID){
            this._impl.getHealthByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to get doctor patients by mongo's randomly generated hash Object ID.
     * This method will get all the patients that have a reference to the doctor's object id.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid. This object id reference
     * the doctors object id.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getDoctorPatients(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorPatients) {
            this._impl.getDoctorPatients(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to get patient's health by mongo's randomly generated hash Object ID.
     * This method will get all the health records that have a reference to the patient's object id.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid. This object id reference
     * the patients object id.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    getPatientsHealth(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientsHealth) {
            this._impl.getPatientsHealth(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to create/save a doctor object into the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqDocObj - The object representing a doctor.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    createDoctor(reqDocObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createDoctor){
            this._impl.createDoctor(reqDocObj, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to create/save a patient object into the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqPatObj - The object representing a patient.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    createPatient(reqPatObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createPatient){
            this._impl.createPatient(reqPatObj, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to create/save a health record object into the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqHeaObj - The object representing a health record.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    createHealth(reqHeaObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createHealth){
            this._impl.createHealth(reqHeaObj, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to update a doctor object in the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid. This object id reference
     * the doctor's object id.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    updateDoctor(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateDoctor){
            this._impl.updateDoctor(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to update a patient object in the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid. This object id reference
     * the patient's object id.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    updatePatient(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updatePatient){
            this._impl.updatePatient(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to update a health record object in the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {string} reqObjectID - The string of the objectid. This object id reference
     * the health object id.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    updateHealth(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateHealth){
            this._impl.updateHealth(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to remove a doctor object from the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqObjectID - The object representing a doctor.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    removeDoctor(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeDoctor){
            this._impl.removeDoctor(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to remove a patient object from the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqObjectID - The object representing a patient.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    removePatient(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removePatient){
            this._impl.removePatient(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to remove a health record object from the database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     * @param {object} reqObjectID - The object representing a doctor.
     * @param {function} callback - The callback function. This is the function used
     * in the api implementation so database code can return to the api after
     * completing it's database command.
     */
    removeHealth(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeHealth){
            this._impl.removeHealth(reqObjectID, callback);     // Forward request to implementor
        }
    }
    /**
     * Interface method to connect to the implemented database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     *
     * Note:
     *  Normally, the developer would want to connect to the database, execute
     *  the database command, disconnect from the database, then return the result.
     *
     *  If using mongodb, the developer shall connect to the database once, and not
     *  for every executed database command.
     *  This connection shall happen at the start of the application.
     *  This is highly recommend from online sources.
     *  The reason being why is how mongo is implemented. It uses a pool of
     *  connections.
     *  Reference: http://blog.mongolab.com/2013/11/deep-dive-into-connection-pooling/
     */
    connectDB() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.connectDB){
            this._impl.connectDB();     // Forward request to implementor
        }
    }
    /**
     * Interface method to disconnect from the implemented database.
     * The interface method determines if the implementation is real or not.
     * If the implementation is real, then it will execute the database command.
     */
    disconnectDB() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.disconnectDB){
            this._impl.disconnectDB();     // Forward request to implementor
        }
    }
}

//export default new databaseManager();
module.exports = databaseManager;


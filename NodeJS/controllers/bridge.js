
/**
 * Created by kevin on 11/7/2015.
 */

var mongoose = require('mongoose'),
    path = require('path'),
    Models = require('../models'),
    Schema = mongoose.Schema,
    ObjectId = mongoose.Schema.Types.ObjectId;

function dbMgrBridge(whichdb) {
// Implementation reference:
    this._impl = this._EstablishImplementor(whichdb);
    //console.log(this._impl);
    return this;
}

dbMgrBridge.prototype = {
    //Private Method:
    _SetImplementation: function(implementor) {
        this._impl = null;
        if(implementor) this._impl = implementor;
    },
    // EstablishImplementor - function that creates
    // the Concrete Implementor and binds it to Abstraction.
    // This is the very method to place your
    // browser/feature/object detection code.
    _EstablishImplementor: function(whichdb) {
        if(whichdb === 1) {
            return new ImplementationMongoose();//mongoose / mongodb
        }
        else if(false) {
            return new ImplementationTwo();
        }
        // ...
        return null;
    },
    // Function "exported" by the Abstraction:
    // for each one of these, add to implmentation 1 and implementation 2
    // Public Methods
    getDoctorByEmail: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByEmail) {
            this._impl.getDoctorByEmail(reqObjectID, callback);     // Forward request to implementor
        }
    },
    getDoctorByObjectID: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByObjectID) {
            this._impl.getDoctorByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    },
    getPatientByObjectID: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientByObjectID){
            this._impl.getPatientByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    },
    getHealthByObjectID: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getHealthByObjectID){
            this._impl.getHealthByObjectID(reqObjectID, callback);     // Forward request to implementor
        }
    },
    getDoctorPatients: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorPatients) {
            this._impl.getDoctorPatients(reqObjectID, callback);     // Forward request to implementor
        }
    },
    getPatientsHealth: function(reqObjectID, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getPatientsHealth) {
            this._impl.getPatientsHealth(reqObjectID, callback);     // Forward request to implementor
        }
    },
    createDoctor: function(reqDocObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createDoctor){
            this._impl.createDoctor(reqDocObj, callback);     // Forward request to implementor
        }
    },
    createPatient: function(reqPatObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createPatient){
            this._impl.createPatient(reqPatObj, callback);     // Forward request to implementor
        }
    },
    createHealth: function(reqHeaObj, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createHealth){
            this._impl.createHealth(reqHeaObj, callback);     // Forward request to implementor
        }
    },
    updateDoctor: function(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateDoctor){
            this._impl.updateDoctor(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    },
    updatePatient: function(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updatePatient){
            this._impl.updatePatient(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    },
    updateHealth: function(reqObjectID, reqUpdate, callback){
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.updateHealth){
            this._impl.updateHealth(reqObjectID, reqUpdate, callback);     // Forward request to implementor
        }
    },
    removeDoctor: function(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeDoctor){
            this._impl.removeDoctor(reqObjectID, callback);     // Forward request to implementor
        }
    },
    removePatient: function(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removePatient){
            this._impl.removePatient(reqObjectID, callback);     // Forward request to implementor
        }
    },
    removeHealth: function(reqObjectID, callback) {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeHealth){
            this._impl.removeHealth(reqObjectID, callback);     // Forward request to implementor
        }
    },
    connectDB: function() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.connectDB){
            this._impl.connectDB();     // Forward request to implementor
        }
    },
    disconnectDB: function() {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.disconnectDB){
            this._impl.disconnectDB();     // Forward request to implementor
        }
    }
};

//Concrete Implementation
// This is the first in the set of concrete implementors:
function ImplementationMongoose() {
    //implement private methods
    //need to implement command pattern to this
    this._dbAddress = 'mongodb://localhost:27017/mongotest';
    this._dbConnectedMessage = "Mongoose connected on localhost:27017";
    return this;
}
ImplementationMongoose.prototype = {
    connectDB: function() {
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
    },
    disconnectDB: function() {
        process.on('SIGINT', function() {
            mongoose.connection.close(function () {
                console.log('moongose default connection disconnected through app termination');
                process.exit(0);
            });
        });
        mongoose.connection.close();
    },
    getDoctorByEmail: function(reqObjectEmail, callback){
        Models.Doctors.findOne({email:reqObjectEmail}, function(err,doc){
            callback(err,doc);
        });
    },
    getDoctorByObjectID: function(reqObjectID, callback){
        reqObjectID = ObjectId(reqObjectID);
        Models.Doctors.findOne({_id:reqObjectID}, function(err,doc){
            callback(err,doc);
        });
    },
    getPatientByObjectID: function(reqObjectID, callback){
        reqObjectID = ObjectId(reqObjectID);
        Models.Patients.findOne({_id:reqObjectID}, function(err,pat){
            callback(err,pat);
        });
    },
    getHealthByObjectID: function(reqObjectID, callback){
        reqObjectID = ObjectId(reqObjectID);
        Models.Health.findOne({_id:reqObjectID}, function(err,hea){
            callback(err,hea);
        });
    },
    getDoctorPatients: function(reqObjectID, callback){
        //TODO Kevin I had to make a change to this.
        //may need to use next line
        //reqObjectID = ObjectId(reqObjectID);
        Models.Patients.find({doctor:reqObjectID}, function(err,pat){
            callback(err,pat);
        });
    },
    getPatientsHealth: function(reqObjectID, callback){
        //may need to use next line
        //reqObjectID = ObjectId(reqObjectID);
        Models.Health.find({patient:reqObjectID}, function(err,hea){
            callback(err,hea);
        });
    },
    createPatient: function(reqPatObj, callback){
        reqPatObj.save(function(err, pat){
            callback(err,pat);
        });
    },
    createDoctor: function(reqDocObj, callback){
        reqDocObj.save(function(err, doc){
            callback(err,doc);
        });
    },
    createHealth: function(reqHeaObj, callback){
        reqHeaObj.save(function(err, hea){
            callback(err,hea);
        });
    },
    updateDoctor: function(reqObjectID, reqUpdate, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Doctors.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, doc) {
            callback(err,doc);
        });
    },
    updatePatient: function(reqObjectID, reqUpdate, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Patients.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, pat) {
            callback(err,pat);
        });
    },
    updateHealth: function(reqObjectID, reqUpdate, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Health.findOneAndUpdate({_id: reqObjectID}, reqUpdate, function(err, hea) {
            callback(err,hea);
        });
    },
    removeDoctor: function(reqObjectID, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Doctors.findOneAndRemove({_id: reqObjectID}, function(err, doc){
            callback(err,doc);
        });
    },
    removePatient: function(reqObjectID, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Patients.findOneAndRemove({_id: reqObjectID}, function(err, pat){
            callback(err, pat);
        });
    },
    removeHealth: function(reqObjectID, callback){
        //reqObjectID = ObjectId(reqObjectID)
        Models.Health.findOneAndRemove({_id: reqObjectID}, function(err, hea){
            callback(err, hea);
        });
    }
};

//Concrete Implementation 2//
// This is the second implementor:
function ImplementationTwo() {
    this._dbAddress = '';
    this._dbConnectedMessage = "";
    return this;
}
ImplementationTwo.prototype = {
    connectDB: function() {
    },
    disconnectDB: function() {
    }
};
module.exports = {
    bridge: function(whichDB){
        return new dbMgrBridge(whichDB);
    }
};

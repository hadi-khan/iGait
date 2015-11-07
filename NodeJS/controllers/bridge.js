
/**
 * Created by kevin on 11/7/2015.
 */

var     mongoose = require('mongoose');
path = require('path'),
    Models = require('../models');


//command = require('command.js');


// Abstraction - the object you usually create
//               in your "end-doctor" scripts:
//
// <SCRIPT type="text/javascript">

//
//    var abstr = new Abstraction();
//    ...
//
// </SCRIPT>
//
function dbMgrBridge(whichdb) //2
{
// Implementation reference:

    this._impl = this._EstablishImplementor(whichdb);
    //console.log(this._impl);
    return this;
}

dbMgrBridge.prototype = {

    //Private Method:
    _IAmAPrivateMethod: function(){
        return true;
    },
    //not quite for sure what this does....
    _SetImplementation: function(implementor)
    {
        this._impl = null;
        if(implementor) this._impl = implementor;
    },

    // EstablishImplementor - function that creates            |||||factory method
    // the Concrete Implementor and binds it to Abstraction.
    // This is the very method to place your
    // browser/feature/object detection code.
    _EstablishImplementor: function(whichdb)
    {
        if(whichdb == 1)//change later!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            return new ImplementationMongoose();//mongoose / mongodb

        else if(false)
            return new ImplementationTwo();
        // ...
        return null;
    },

    // Function "exported" by the Abstraction:
    // for each one of these, add to implmentation 1 and implementation 2
    // Public Methods
    addKevin: function(callback)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.addKevin)
            this._impl.addKevin(callback);     // Forward request to implementor
    },
    removeKevin: function(callback)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.removeKevin)
            this._impl.removeKevin(callback);     // Forward request to implementor
    },
    getDoctorByEmail: function(reqEmail, callback)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.getDoctorByEmail)
            this._impl.getDoctorByEmail(reqEmail, callback);     // Forward request to implementor
    },
    createDoctor: function(
        reqEmail,reqPassword, reqFirstName, reqLastName, reqMobileNumber, reqOfficeNumber, reqOfficeAddress, callback)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.createDoctor)
            this._impl.createDoctor(
                reqEmail,reqPassword, reqFirstName, reqLastName, reqMobileNumber, reqOfficeNumber, reqOfficeAddress, callback);     // Forward request to implementor
    },
    connectDB: function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.connectDB)
            this._impl.connectDB();     // Forward request to implementor

    },
    disconnectDB: function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl && this._impl.disconnectDB)
            this._impl.disconnectDB();     // Forward request to implementor
    }
};

// ...
//Concrete Implementation 1//
// This is the first in the set of concrete implementors:
//4
function ImplementationMongoose()
{
    //implement private methods
    //need to implement command pattern to this
// ...
    this._newvar = null;
    this._dbAddress = 'mongodb://localhost:27017/mongotest';
    this._dbConnectedMessage = "Mongoose connected on localhost:27017";

    return this;
}

ImplementationMongoose.prototype = {
    connectDB: function()
    {
        //connecting to mongodb with mongoose
        mongoose.connect(this._dbAddress);
        mongoose.connection.on('open', function() {
            console.log("Connected to Mongoose");
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
            })
        });
    },

    disconnectDB: function()
    {
        process.on('SIGINT', function() {
            mongoose.connection.close(function () {
                console.log('moongose default connection disconnected through app termination');
                process.exit(0);
            })
        });

        mongoose.connection.close();
    },
    getDoctorByEmail: function(reqEmail, callback){
        var promise = Models.Doctors.find({email:reqEmail}).exec(function(err,doc){
            if(err){
                //console.log("Error in db.getDoctorbyEmail: \n" + err);
                callback({success: 'false_error', function: 'db.getDoctorByEmail', message: err});
            }
            else if (!doc.length){
                //console.log("Doctor account does not exists");
                callback({success: 'false', function: 'db.getDoctorByEmail', message: 'Doctor account does not exists'});
            }
            else{
                //console.log(doc);
                callback({success: 'true', function: 'db.getDoctorByEmail', message: doc[0]});
            }
        });
    },
    createDoctor: function(
        reqEmail,reqPassword, reqFirstName, reqLastName, reqMobileNumber, reqOfficeNumber, reqOfficeAddress, callback){
        //Doctors refers to the var in /models/index.js that saves the path to the doctors.js file
        var newDoctor = new Models.Doctors({
            email: reqEmail,
            password: reqPassword,
            name: {
                first: reqFirstName,
                last: reqLastName
            },
            contact: {
                mobilenumber: reqMobileNumber,
                officenumber: reqOfficeNumber,
                officeaddress: reqOfficeAddress
            }
        });
        //doctors refers to the filename that contains the model for doctors
        newDoctor.save(function(err, doc){
            if(err){
                console.log("Error in db.createDoctor: " + err);
                callback({success: 'false_error', function: 'db.createDoctor', message: err});
            }
            else{
                callback({success: 'true', function: 'db.createDoctor', message: doc});
            }
        });
    },
    addKevin: function(callback){
        //Doctors refers to the var in /models/index.js that saves the path to the doctors.js file
        var newDoctor = new Models.Doctors({
            email: 'kevin.kocian@mavs.uta.edu',
            password: 'password',
            name: {
                first: 'kevin',
                last: 'kocian'
            },
            contact: {
                mobilenumber: '25464449059',
                officenumber: '12345678910',
                officeaddress: '1100greek'
            }
        });
        //doctors refers to the filename that contains the model for doctors
        newDoctor.save(function(err, doc){
            if(err){
                console.log("Error in db.createDoctor: \n" + err);
                callback({success: 'false_error', function: 'db.addKevin', message: err});
            }
            else{
                callback({success: 'true', function: 'db.addKevin', message: doc});
            }
        });
    }
// ...
}

//Concrete Implementation 2//
// This is the second implementor:
function ImplementationTwo()
{
// ...
}

ImplementationTwo.prototype = {

    FuncOne: function()
    {
        // ...
    },

// ...
}


module.exports = {
    bridge: function(whichDB){
        return new dbMgrBridge(whichDB);
    }
}

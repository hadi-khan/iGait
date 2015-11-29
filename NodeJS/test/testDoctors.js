

'use strict';

// import the moongoose helper utilities
var mongoose = require('mongoose');
//var utils = require('./testHelper');
var should = require('should');
// import our security module
let Security = require('../class/Security');
// import our User mongoose model
var Models = require('../models');
let ObjectId = mongoose.Schema.Types;
// import our DBMGR and connect to it.
let dbmgr = require('../controllers/bridge');
let db = dbmgr.bridge(1);
db.connectDB();


describe('Doctors: models', function () {
    describe('#create()', function () {
        it('should create a new Doctor', function (done) {
            // Create a Doctor object to pass to Doctor.create()
            let newDoctor1 = Models.Doctors({email:"hadi.abdul@mavs.uta.edu",password:Security.hashPassword("doc1"),name:{first:"hadi",last:"abdul"},contact:{officenumber:"1111111111",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
            db.createDoctor(newDoctor1, function (err, createdDoctor) {
                // Confirm that that an error does not exist
                should.not.exist(err);
                // verify that the returned user is what we expect

                createdDoctor.email.should.equal('hadi.abdul@mavs.uta.edu');
                //createdDoctor.password.should.equal(Security.hashPassword("doc1"));
                createdDoctor.name.first.should.equal('hadi');
                createdDoctor.name.last.should.equal('abdul');
                createdDoctor.contact.officenumber.should.equal(1111111111);
                createdDoctor.contact.officeaddress.should.equal('701 S Nedderman Dr');
                createdDoctor.contact.state.should.equal('tx');
                createdDoctor.contact.city.should.equal('arlington');
                createdDoctor.contact.zipcode.should.equal(76019);
                createdDoctor.accessToken.should.equal('');

                //createdDoctor.email.should.equal('');
                //createdDoctor.password.should.equal('');
                //createdDoctor.name.first.should.equal('');
                //createdDoctor.name.last.should.equal('');
                //createdDoctor.contact.officenumber.should.equal('');
                //createdDoctor.contact.officeaddress.should.equal('');
                //createdDoctor.contact.state.should.equal('');
                //createdDoctor.contact.city.should.equal('');
                //createdDoctor.contact.zipcode.should.equal('');
                //createdDoctor.accessToken.should.equal('');

                // Call done to tell mocha that we are done with this test
                done();

            });
        });
    });
});


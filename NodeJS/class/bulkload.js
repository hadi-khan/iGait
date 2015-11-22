/**
 * Created by kevin on 11/19/2015.
 */


//To run
//node bulkload_database.js

'use strict';
var mongoose = require('mongoose')
let Security = require('./Security');
let Models = require('../models');
let dbmgr = require('../controllers/bridge');
let ObjectId = mongoose.Schema.Types.ObjectId;

let db = dbmgr.bridge(1);
db.connectDB();

let newDoctor1 = Models.Doctors({email:"hadi.abdul@mavs.uta.edu",password:Security.hashPassword("doc1"),name:{first:"hadi",last:"abdul"},contact:{officenumber:"1111111111",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor2 = Models.Doctors({email:"jesus.linares@mavs.uta.edu",password:Security.hashPassword("doc2"),name:{first:"jesus",last:"linares"},contact:{officenumber:"2222222222",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor3 = Models.Doctors({email:"kevin.kocian@mavs.uta.edu",password:Security.hashPassword("doc3"),name:{first:"kevin",last:"kocian"},contact:{officenumber:"333333333",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});

db.createDoctor(newDoctor1, replydoc);
db.createDoctor(newDoctor2, replydoc);
db.createDoctor(newDoctor3, replydoc);


db.getDoctorByEmail(newDoctor1.email, assignPatient1);
function assignPatient1(err, doctor){
    if(err){
        console.log("cant find doc err: "+ err);
    }else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }

    let newPatient1 = Models.Patients({name:{first:"ross",last:"walker"},dateOfBirth:new Date('1991-01-01').toISOString(),gender:"male",contact:{email: "fakeemail1@gmail.com",mobilenumber:"9725719740",address: "north 1239468 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:23,minute:34,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient2 = Models.Patients({name:{first:"ryan",last:"dawson"},dateOfBirth:new Date('1991-02-01').toISOString(),gender:"male",contact:{email: "fakeemail2@gmail.com",mobilenumber:"1234567890",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:22,minute:33,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient3 = Models.Patients({name:{first:"sara",last:"crocket"},dateOfBirth:new Date('1991-03-01').toISOString(),gender:"female",contact:{email: "fakeemail3@gmail.com",mobilenumber:"0987654321",address: "north 123951 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:21,minute:32,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient4 = Models.Patients({name:{first:"andrea",last:"kolacek"},dateOfBirth:new Date('1991-04-01').toISOString(),gender:"female",contact:{email: "fakeemail4@gmail.com",mobilenumber:"4567891230",address: "north 123753 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:20,minute:31,second:0},doctor:ObjectId(doctor.objectId)});
    db.createPatient(newPatient1, replyPatient1);
    db.createPatient(newPatient2, replyPatient1);
    db.createPatient(newPatient3, replyPatient1);
    db.createPatient(newPatient4, replyPatient1);
    function replyPatient1(err, patient){
        if(err) {
            console.log("err: "+ err);
        }else if(patient){
            console.log("true");
        }
        else{
            console.log("This shouldn't have happend");
        }
    }
}

db.getDoctorByEmail(newDoctor2.email, assignPatient2);
function assignPatient2(err, doctor){
    if(err){
        console.log("cant find doc err: "+ err);
    }else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
    let newPatient5 = Models.Patients({name:{first:"mohammad",last:"kothawala"},dateOfBirth:new Date('1991-05-04').toISOString(),gender:"male",contact:{email: "fakeemail5@gmail.com",mobilenumber:"7412589630",address: "north 123369 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:19,minute:30,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient6 = Models.Patients({name:{first:"kyle",last:"longwell"},dateOfBirth:new Date('1991-06-01').toISOString(),gender:"male",contact:{email: "fakeemail6@gmail.com",mobilenumber:"3698521470",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:18,minute:29,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient7 = Models.Patients({name:{first:"alex",last:"groppe"},dateOfBirth:new Date('1991-07-01').toISOString(),gender:"female",contact:{email: "fakeemail7@gmail.com",mobilenumber:"0654123987",address: "north 123147 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:17,minute:28,second:0},doctor:ObjectId(doctor.objectId)});
    db.createPatient(newPatient5, replyPatient2);
    db.createPatient(newPatient6, replyPatient2);
    db.createPatient(newPatient7, replyPatient2);
    function replyPatient2(err, patient){
        if(err) {
            console.log("err: "+ err);
        }else if(patient){
            console.log("true");
        }
        else{
            console.log("This shouldn't have happend");
        }
    }
}

db.getDoctorByEmail(newDoctor3.email, assignPatient3);
function assignPatient3(err, doctor){
    if(err){
        console.log("cant find doc err: "+ err);
    }else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
    let newPatient8 = Models.Patients({name:{first:"noah",last:"owens"},dateOfBirth:new Date('1991-08-01').toISOString(),gender:"male",contact:{email: "fakeemail8@gmail.com",mobilenumber:"3217412589",address: "north 123321 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:16,minute:27,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient9 = Models.Patients({name:{first:"kristen",last:"glatter"},dateOfBirth:new Date('1991-09-01').toISOString(),gender:"female",contact:{email: "fakeemail9@gmail.com",mobilenumber:"9633214568",address: "north 123654 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:15,minute:26,second:0},doctor:ObjectId(doctor.objectId)});
    let newPatient10 = Models.Patients({name:{first:"sarah",last:"wolf"},dateOfBirth:new Date('1991-10-01').toISOString(),gender:"female",contact:{email: "fakeemail10@gmail.com",mobilenumber:"9877899871",address: "north 123987 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:14,minute:25,second:0},doctor:ObjectId(doctor.objectId)});
    db.createPatient(newPatient8, replyPatient3);
    db.createPatient(newPatient9, replyPatient3);
    db.createPatient(newPatient10, replyPatient3withDisconnect);
    function replyPatient3(err, patient){
        if(err) {
            console.log("err: "+ err);
        }else if(patient){
            console.log("true");
        }
        else{
            console.log("This shouldn't have happend");
        }
    }
    function replyPatient3withDisconnect(err, patient){
        if(err) {
            console.log("err: "+ err);
        } else if(patient){
            console.log("true");
        }
        else{
            console.log("This shouldn't have happend");
        }
        db.disconnectDB();
    }
}

function replydoc(err, doctor){
    if(err){
        console.log("err: "+ err);
    } else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
}

function replydocWithDisconnect(err, doctor){
    if(err){
        console.log("err: "+ err);
    } else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
    db.disconnectDB();
}

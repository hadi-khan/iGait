/**
 * Created by kevin on 11/19/2015.
 */


//To run:
//node bulkload.js

'use strict';
var mongoose = require('mongoose');
let Security = require('./../class/Security');
let Models = require('../models/index');
let dbmgr = require('./dbms');
let ObjectId = mongoose.Schema.Types;
let async = require('async');


let db = new dbmgr();
db.connectDB();

let newDoctor1 = Models.Doctors({email:"hadi.abdul@mavs.uta.edu",password:Security.hashPassword("doc1"),name:{first:"hadi",last:"abdul"},contact:{officenumber:"1111111111",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor2 = Models.Doctors({email:"jesus.linares@mavs.uta.edu",password:Security.hashPassword("doc2"),name:{first:"jesus",last:"linares"},contact:{officenumber:"2222222222",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor3 = Models.Doctors({email:"kevin.kocian@mavs.uta.edu",password:Security.hashPassword("doc3"),name:{first:"kevin",last:"kocian"},contact:{officenumber:"333333333",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});

let newPatient1 = Models.Patients({name:{first:"ross",last:"walker"},dateOfBirth:new Date('1991-01-01').toISOString(),gender:"male",contact:{email: "fakeemail1@gmail.com",mobilenumber:"9725719740",address: "north 1239468 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:23,minute:34,second:0},doctor:newDoctor1._id});
let newPatient2 = Models.Patients({name:{first:"ryan",last:"dawson"},dateOfBirth:new Date('1991-02-01').toISOString(),gender:"male",contact:{email: "fakeemail2@gmail.com",mobilenumber:"1234567890",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:22,minute:33,second:0},doctor:newDoctor1._id});
let newPatient3 = Models.Patients({name:{first:"sara",last:"crocket"},dateOfBirth:new Date('1991-03-01').toISOString(),gender:"female",contact:{email: "fakeemail3@gmail.com",mobilenumber:"0987654321",address: "north 123951 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:21,minute:32,second:0},doctor:newDoctor1._id});
let newPatient4 = Models.Patients({name:{first:"andrea",last:"kolacek"},dateOfBirth:new Date('1991-04-01').toISOString(),gender:"female",contact:{email: "fakeemail4@gmail.com",mobilenumber:"4567891230",address: "north 123753 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:20,minute:31,second:0},doctor:newDoctor1._id});

let newPatient5 = Models.Patients({name:{first:"mohammad",last:"kothawala"},dateOfBirth:new Date('1991-05-04').toISOString(),gender:"male",contact:{email: "fakeemail5@gmail.com",mobilenumber:"7412589630",address: "north 123369 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:19,minute:30,second:0},doctor:newDoctor2._id});
let newPatient6 = Models.Patients({name:{first:"kyle",last:"longwell"},dateOfBirth:new Date('1991-06-01').toISOString(),gender:"male",contact:{email: "fakeemail6@gmail.com",mobilenumber:"3698521470",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:18,minute:29,second:0},doctor:newDoctor2._id});
let newPatient7 = Models.Patients({name:{first:"alex",last:"groppe"},dateOfBirth:new Date('1991-07-01').toISOString(),gender:"female",contact:{email: "fakeemail7@gmail.com",mobilenumber:"0654123987",address: "north 123147 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:17,minute:28,second:0},doctor:newDoctor2._id});

let newPatient8 = Models.Patients({name:{first:"noah",last:"owens"},dateOfBirth:new Date('1991-08-01').toISOString(),gender:"male",contact:{email: "fakeemail8@gmail.com",mobilenumber:"3217412589",address: "north 123321 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:16,minute:27,second:0},doctor:newDoctor3._id});
let newPatient9 = Models.Patients({name:{first:"kristen",last:"glatter"},dateOfBirth:new Date('1991-09-01').toISOString(),gender:"female",contact:{email: "fakeemail9@gmail.com",mobilenumber:"9633214568",address: "north 123654 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:15,minute:26,second:0},doctor:newDoctor3._id});
let newPatient10 = Models.Patients({name:{first:"sarah",last:"wolf"},dateOfBirth:new Date('1991-10-01').toISOString(),gender:"female",contact:{email: "fakeemail10@gmail.com",mobilenumber:"9877899871",address: "north 123987 st",state:"tx",city:"arlington",zipcode:"76019"},priority:false,expectedWalkTime:{hour:14,minute:25,second:0},doctor:newDoctor3._id});

let newHealth1_1 = Models.Health({health:3,start_time:new Date(2015,11,18,23,34,0,0).toISOString(),end_time:new Date(2015,11,18,23,35,0,0).toISOString(),patient:newPatient1._id});
let newHealth1_2 = Models.Health({health:3,start_time:new Date(2015,11,19,23,34,0,0).toISOString(),end_time:new Date(2015,11,19,23,35,0,0).toISOString(),patient:newPatient1._id});
let newHealth1_3 = Models.Health({health:2,start_time:new Date(2015,11,20,23,34,0,0).toISOString(),end_time:new Date(2015,11,20,23,35,0,0).toISOString(),patient:newPatient1._id});
let newHealth1_4 = Models.Health({health:1,start_time:new Date(2015,11,21,23,34,0,0).toISOString(),end_time:new Date(2015,11,21,23,35,0,0).toISOString(),patient:newPatient1._id});
let newHealth1_5 = Models.Health({health:3,start_time:new Date(2015,11,22,23,34,0,0).toISOString(),end_time:new Date(2015,11,22,23,35,0,0).toISOString(),patient:newPatient1._id});

let newHealth2_1 = Models.Health({health:3,start_time:new Date(2015,11,18,22,23,0,0).toISOString(),end_time:new Date(2015,11,18,22,24,0,0).toISOString(),patient:newPatient2._id});
let newHealth2_2 = Models.Health({health:3,start_time:new Date(2015,11,19,22,23,0,0).toISOString(),end_time:new Date(2015,11,19,22,24,0,0).toISOString(),patient:newPatient2._id});
let newHealth2_3 = Models.Health({health:1,start_time:new Date(2015,11,20,22,23,0,0).toISOString(),end_time:new Date(2015,11,20,22,24,0,0).toISOString(),patient:newPatient2._id});
let newHealth2_4 = Models.Health({health:3,start_time:new Date(2015,11,21,22,23,0,0).toISOString(),end_time:new Date(2015,11,21,22,24,0,0).toISOString(),patient:newPatient2._id});
let newHealth2_5 = Models.Health({health:3,start_time:new Date(2015,11,22,22,23,0,0).toISOString(),end_time:new Date(2015,11,22,22,24,0,0).toISOString(),patient:newPatient2._id});

let newHealth3_1 = Models.Health({health:3,start_time:new Date(2015,11,18,21,32,0,0).toISOString(),end_time:new Date(2015,11,18,21,33,0,0).toISOString(),patient:newPatient3._id});
let newHealth3_2 = Models.Health({health:3,start_time:new Date(2015,11,19,21,32,0,0).toISOString(),end_time:new Date(2015,11,19,21,33,0,0).toISOString(),patient:newPatient3._id});
let newHealth3_3 = Models.Health({health:0,start_time:new Date(2015,11,20,21,32,0,0).toISOString(),end_time:new Date(2015,11,20,21,33,0,0).toISOString(),patient:newPatient3._id});
let newHealth3_4 = Models.Health({health:1,start_time:new Date(2015,11,21,21,32,0,0).toISOString(),end_time:new Date(2015,11,21,21,33,0,0).toISOString(),patient:newPatient3._id});
let newHealth3_5 = Models.Health({health:2,start_time:new Date(2015,11,22,21,32,0,0).toISOString(),end_time:new Date(2015,11,22,21,33,0,0).toISOString(),patient:newPatient3._id});

let newHealth4_1 = Models.Health({health:3,start_time:new Date(2015,11,18,20,31,0,0).toISOString(),end_time:new Date(2015,11,18,20,32,0,0).toISOString(),patient:newPatient4._id});
let newHealth4_2 = Models.Health({health:3,start_time:new Date(2015,11,19,20,31,0,0).toISOString(),end_time:new Date(2015,11,19,20,32,0,0).toISOString(),patient:newPatient4._id});
let newHealth4_3 = Models.Health({health:3,start_time:new Date(2015,11,20,20,31,0,0).toISOString(),end_time:new Date(2015,11,20,20,32,0,0).toISOString(),patient:newPatient4._id});
let newHealth4_4 = Models.Health({health:3,start_time:new Date(2015,11,21,20,31,0,0).toISOString(),end_time:new Date(2015,11,21,20,32,0,0).toISOString(),patient:newPatient4._id});
let newHealth4_5 = Models.Health({health:3,start_time:new Date(2015,11,22,20,31,0,0).toISOString(),end_time:new Date(2015,11,22,20,32,0,0).toISOString(),patient:newPatient4._id});

let newHealth5_1 = Models.Health({health:3,start_time:new Date(2015,11,18,19,30,0,0).toISOString(),end_time:new Date(2015,11,18,19,31,0,0).toISOString(),patient:newPatient5._id});
let newHealth5_2 = Models.Health({health:3,start_time:new Date(2015,11,19,19,30,0,0).toISOString(),end_time:new Date(2015,11,19,19,31,0,0).toISOString(),patient:newPatient5._id});
let newHealth5_3 = Models.Health({health:3,start_time:new Date(2015,11,20,19,30,0,0).toISOString(),end_time:new Date(2015,11,20,19,31,0,0).toISOString(),patient:newPatient5._id});
let newHealth5_4 = Models.Health({health:3,start_time:new Date(2015,11,21,19,30,0,0).toISOString(),end_time:new Date(2015,11,21,19,31,0,0).toISOString(),patient:newPatient5._id});
let newHealth5_5 = Models.Health({health:3,start_time:new Date(2015,11,22,19,30,0,0).toISOString(),end_time:new Date(2015,11,22,19,31,0,0).toISOString(),patient:newPatient5._id});

let newHealth6_1 = Models.Health({health:3,start_time:new Date(2015,11,18,18,29,0,0).toISOString(),end_time:new Date(2015,11,18,18,30,0,0).toISOString(),patient:newPatient6._id});
let newHealth6_2 = Models.Health({health:3,start_time:new Date(2015,11,19,18,29,0,0).toISOString(),end_time:new Date(2015,11,19,18,30,0,0).toISOString(),patient:newPatient6._id});
let newHealth6_3 = Models.Health({health:2,start_time:new Date(2015,11,20,18,29,0,0).toISOString(),end_time:new Date(2015,11,20,18,30,0,0).toISOString(),patient:newPatient6._id});
let newHealth6_4 = Models.Health({health:3,start_time:new Date(2015,11,21,18,29,0,0).toISOString(),end_time:new Date(2015,11,21,18,30,0,0).toISOString(),patient:newPatient6._id});
let newHealth6_5 = Models.Health({health:2,start_time:new Date(2015,11,22,18,29,0,0).toISOString(),end_time:new Date(2015,11,22,18,30,0,0).toISOString(),patient:newPatient6._id});

let newHealth7_1 = Models.Health({health:3,start_time:new Date(2015,11,18,17,28,0,0).toISOString(),end_time:new Date(2015,11,18,17,29,0,0).toISOString(),patient:newPatient7._id});
let newHealth7_2 = Models.Health({health:3,start_time:new Date(2015,11,19,17,28,0,0).toISOString(),end_time:new Date(2015,11,19,17,29,0,0).toISOString(),patient:newPatient7._id});
let newHealth7_3 = Models.Health({health:3,start_time:new Date(2015,11,20,17,28,0,0).toISOString(),end_time:new Date(2015,11,20,17,29,0,0).toISOString(),patient:newPatient7._id});
let newHealth7_4 = Models.Health({health:2,start_time:new Date(2015,11,21,17,28,0,0).toISOString(),end_time:new Date(2015,11,21,17,29,0,0).toISOString(),patient:newPatient7._id});
let newHealth7_5 = Models.Health({health:3,start_time:new Date(2015,11,22,17,28,0,0).toISOString(),end_time:new Date(2015,11,22,17,29,0,0).toISOString(),patient:newPatient7._id});

let newHealth8_1 = Models.Health({health:3,start_time:new Date(2015,11,18,16,27,0,0).toISOString(),end_time:new Date(2015,11,18,16,28,0,0).toISOString(),patient:newPatient8._id});
let newHealth8_2 = Models.Health({health:1,start_time:new Date(2015,11,19,16,27,0,0).toISOString(),end_time:new Date(2015,11,19,16,28,0,0).toISOString(),patient:newPatient8._id});
let newHealth8_3 = Models.Health({health:1,start_time:new Date(2015,11,20,16,27,0,0).toISOString(),end_time:new Date(2015,11,20,16,28,0,0).toISOString(),patient:newPatient8._id});
let newHealth8_4 = Models.Health({health:3,start_time:new Date(2015,11,21,16,27,0,0).toISOString(),end_time:new Date(2015,11,21,16,28,0,0).toISOString(),patient:newPatient8._id});
let newHealth8_5 = Models.Health({health:3,start_time:new Date(2015,11,22,16,27,0,0).toISOString(),end_time:new Date(2015,11,22,16,28,0,0).toISOString(),patient:newPatient8._id});

let newHealth9_1 = Models.Health({health:1,start_time:new Date(2015,11,18,15,26,0,0).toISOString(),end_time:new Date(2015,11,18,15,27,0,0).toISOString(),patient:newPatient9._id});
let newHealth9_2 = Models.Health({health:1,start_time:new Date(2015,11,19,15,26,0,0).toISOString(),end_time:new Date(2015,11,19,15,27,0,0).toISOString(),patient:newPatient9._id});
let newHealth9_3 = Models.Health({health:1,start_time:new Date(2015,11,20,15,26,0,0).toISOString(),end_time:new Date(2015,11,20,15,27,0,0).toISOString(),patient:newPatient9._id});
let newHealth9_4 = Models.Health({health:1,start_time:new Date(2015,11,21,15,26,0,0).toISOString(),end_time:new Date(2015,11,21,15,27,0,0).toISOString(),patient:newPatient9._id});
let newHealth9_5 = Models.Health({health:1,start_time:new Date(2015,11,22,15,26,0,0).toISOString(),end_time:new Date(2015,11,22,15,27,0,0).toISOString(),patient:newPatient9._id});

let newHealth10_1 = Models.Health({health:2,start_time:new Date(2015,11,18,14,25,0,0).toISOString(),end_time:new Date(2015,11,18,14,26,0,0).toISOString(),patient:newPatient10._id});
let newHealth10_2 = Models.Health({health:2,start_time:new Date(2015,11,19,14,25,0,0).toISOString(),end_time:new Date(2015,11,19,14,26,0,0).toISOString(),patient:newPatient10._id});
let newHealth10_3 = Models.Health({health:2,start_time:new Date(2015,11,20,14,25,0,0).toISOString(),end_time:new Date(2015,11,20,14,26,0,0).toISOString(),patient:newPatient10._id});
let newHealth10_4 = Models.Health({health:2,start_time:new Date(2015,11,21,14,25,0,0).toISOString(),end_time:new Date(2015,11,21,14,26,0,0).toISOString(),patient:newPatient10._id});
let newHealth10_5 = Models.Health({health:3,start_time:new Date(2015,11,22,14,25,0,0).toISOString(),end_time:new Date(2015,11,22,14,26,0,0).toISOString(),patient:newPatient10._id});


//This is can be improved by taking out setTimeout.
//Need to do callback properly.
async.series([
    function(callback){
        callback(null,
            newDoctor1,newDoctor2,newDoctor3,
            newPatient1,newPatient2,newPatient3,newPatient4,newPatient5,newPatient6,newPatient7,newPatient8,newPatient9,newPatient10,
            newHealth1_1,newHealth1_2,newHealth1_3,newHealth1_4,newHealth1_5,
            newHealth2_1,newHealth2_2,newHealth2_3,newHealth2_4,newHealth2_5,
            newHealth3_1,newHealth3_2,newHealth3_3,newHealth3_4,newHealth3_5,
            newHealth4_1,newHealth4_2,newHealth4_3,newHealth4_4,newHealth4_5,
            newHealth5_1,newHealth5_2,newHealth5_3,newHealth5_4,newHealth5_5,
            newHealth6_1,newHealth6_2,newHealth6_3,newHealth6_4,newHealth6_5,
            newHealth7_1,newHealth7_2,newHealth7_3,newHealth7_4,newHealth7_5,
            newHealth8_1,newHealth8_2,newHealth8_3,newHealth8_4,newHealth8_5,
            newHealth9_1,newHealth9_2,newHealth9_3,newHealth9_4,newHealth9_5,
            newHealth10_1,newHealth10_2,newHealth10_3,newHealth10_4,newHealth10_5
        )
    },
    //create doctors
    function(){
        console.log("Preparing for db commands");
    },
    setTimeout(function(){ db.createDoctor(newDoctor1, replyDoc); }, 3000),
    setTimeout(function(){ db.createDoctor(newDoctor2, replyDoc); }, 3000),
    setTimeout(function(){ db.createDoctor(newDoctor3, replyDoc); }, 3000),
    ////create patients
    setTimeout(function(){ db.createPatient(newPatient1, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient2, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient3, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient4, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient5, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient6, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient7, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient8, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient9, replyPatient); }, 4000),
    setTimeout(function(){ db.createPatient(newPatient10, replyPatient); }, 4000),
    //create health records
    setTimeout(function(){ db.createHealth(newHealth1_1, replyHealth); }, 5000),
    setTimeout(function(){ db.createHealth(newHealth1_2, replyHealth); }, 5100),
    setTimeout(function(){ db.createHealth(newHealth1_3, replyHealth); }, 5200),
    setTimeout(function(){ db.createHealth(newHealth1_4, replyHealth); }, 5300),
    setTimeout(function(){ db.createHealth(newHealth1_5, replyHealth); }, 5400),
    setTimeout(function(){ db.createHealth(newHealth2_1, replyHealth); }, 5500),
    setTimeout(function(){ db.createHealth(newHealth2_2, replyHealth); }, 5600),
    setTimeout(function(){ db.createHealth(newHealth2_3, replyHealth); }, 5700),
    setTimeout(function(){ db.createHealth(newHealth2_4, replyHealth); }, 5800),
    setTimeout(function(){ db.createHealth(newHealth2_5, replyHealth); }, 5900),
    setTimeout(function(){ db.createHealth(newHealth3_1, replyHealth); }, 6000),
    setTimeout(function(){ db.createHealth(newHealth3_2, replyHealth); }, 6100),
    setTimeout(function(){ db.createHealth(newHealth3_3, replyHealth); }, 6200),
    setTimeout(function(){ db.createHealth(newHealth3_4, replyHealth); }, 6300),
    setTimeout(function(){ db.createHealth(newHealth3_5, replyHealth); }, 6400),
    setTimeout(function(){ db.createHealth(newHealth4_1, replyHealth); }, 6500),
    setTimeout(function(){ db.createHealth(newHealth4_2, replyHealth); }, 6600),
    setTimeout(function(){ db.createHealth(newHealth4_3, replyHealth); }, 6700),
    setTimeout(function(){ db.createHealth(newHealth4_4, replyHealth); }, 6800),
    setTimeout(function(){ db.createHealth(newHealth4_5, replyHealth); }, 6900),
    setTimeout(function(){ db.createHealth(newHealth5_1, replyHealth); }, 7000),
    setTimeout(function(){ db.createHealth(newHealth5_2, replyHealth); }, 7100),
    setTimeout(function(){ db.createHealth(newHealth5_3, replyHealth); }, 7200),
    setTimeout(function(){ db.createHealth(newHealth5_4, replyHealth); }, 7300),
    setTimeout(function(){ db.createHealth(newHealth5_5, replyHealth); }, 7400),
    setTimeout(function(){ db.createHealth(newHealth6_1, replyHealth); }, 7500),
    setTimeout(function(){ db.createHealth(newHealth6_2, replyHealth); }, 7600),
    setTimeout(function(){ db.createHealth(newHealth6_3, replyHealth); }, 7700),
    setTimeout(function(){ db.createHealth(newHealth6_4, replyHealth); }, 7800),
    setTimeout(function(){ db.createHealth(newHealth6_5, replyHealth); }, 7900),
    setTimeout(function(){ db.createHealth(newHealth7_1, replyHealth); }, 8000),
    setTimeout(function(){ db.createHealth(newHealth7_2, replyHealth); }, 8100),
    setTimeout(function(){ db.createHealth(newHealth7_3, replyHealth); }, 8200),
    setTimeout(function(){ db.createHealth(newHealth7_4, replyHealth); }, 8200),
    setTimeout(function(){ db.createHealth(newHealth7_5, replyHealth); }, 8400),
    setTimeout(function(){ db.createHealth(newHealth8_1, replyHealth); }, 8500),
    setTimeout(function(){ db.createHealth(newHealth8_2, replyHealth); }, 8600),
    setTimeout(function(){ db.createHealth(newHealth8_3, replyHealth); }, 8700),
    setTimeout(function(){ db.createHealth(newHealth8_4, replyHealth); }, 8800),
    setTimeout(function(){ db.createHealth(newHealth8_5, replyHealth); }, 8900),
    setTimeout(function(){ db.createHealth(newHealth9_1, replyHealth); }, 9000),
    setTimeout(function(){ db.createHealth(newHealth9_2, replyHealth); }, 9100),
    setTimeout(function(){ db.createHealth(newHealth9_3, replyHealth); }, 9200),
    setTimeout(function(){ db.createHealth(newHealth9_4, replyHealth); }, 9300),
    setTimeout(function(){ db.createHealth(newHealth9_5, replyHealth); }, 9400),
    setTimeout(function(){ db.createHealth(newHealth10_1, replyHealth); }, 9500),
    setTimeout(function(){ db.createHealth(newHealth10_2, replyHealth); }, 9600),
    setTimeout(function(){ db.createHealth(newHealth10_3, replyHealth); }, 9700),
    setTimeout(function(){ db.createHealth(newHealth10_4, replyHealth); }, 9800),
    setTimeout(function(){ db.createHealth(newHealth10_5, replyHealthWithDisconnect); }, 9900),
], function (err, result) {
    // result now equals 'done'
    if(err){
        console.log("err: ");
    }else{
        console.log("Finish");
    }
});
function replyDoc(err, doctor){
    if(err){
        console.log("doctor err: "+ err);
    } else if(doctor){
        console.log("Success: Doctor");
    }
    else{
        console.log("This shouldn't have happend");
    }
}
var k = 0;
function replyPatient(err, patient){
    if(err) {
        console.log("patient err: "+ err);
    }else if(patient){
        console.log("Success: Patient");
        k = k+1;
    }
    else{
        console.log("This shouldn't have happend");
    }
}
function replyHealth(err, health){
    if(err) {
        console.log("health err: "+ err);
    }else if(health){
        console.log("Success: Health");
    }
    else{
        console.log("This shouldn't have happend");
    }
}
function replyHealthWithDisconnect(err, health){
    if(err) {
        console.log("health err: "+ err);
    }else if(health){
        console.log("Success: Health and Disconnect");
        //setTimeout(db.disconnectDB(), 10000);
    }
    else {
        console.log("This shouldn't have happend");
    }
}




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

let newPatient1 = Models.Patients({name:{first:"ross",last:"walker"},dateOfBirth:new Date('1991-01-01').toISOString(),gender:"male",contact:{email: "fakeemail1@gmail.com",mobilenumber:"9725719740",address: "north 1239468 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:23,minute:34,second:0},doctor:ObjectId(newDoctor1._id)});
let newPatient2 = Models.Patients({name:{first:"ryan",last:"dawson"},dateOfBirth:new Date('1991-02-01').toISOString(),gender:"male",contact:{email: "fakeemail2@gmail.com",mobilenumber:"1234567890",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:22,minute:33,second:0},doctor:ObjectId(newDoctor1._id)});
let newPatient3 = Models.Patients({name:{first:"sara",last:"crocket"},dateOfBirth:new Date('1991-03-01').toISOString(),gender:"female",contact:{email: "fakeemail3@gmail.com",mobilenumber:"0987654321",address: "north 123951 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:21,minute:32,second:0},doctor:ObjectId(newDoctor1._id)});
let newPatient4 = Models.Patients({name:{first:"andrea",last:"kolacek"},dateOfBirth:new Date('1991-04-01').toISOString(),gender:"female",contact:{email: "fakeemail4@gmail.com",mobilenumber:"4567891230",address: "north 123753 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:20,minute:31,second:0},doctor:ObjectId(newDoctor1._id)});

let newPatient5 = Models.Patients({name:{first:"mohammad",last:"kothawala"},dateOfBirth:new Date('1991-05-04').toISOString(),gender:"male",contact:{email: "fakeemail5@gmail.com",mobilenumber:"7412589630",address: "north 123369 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:19,minute:30,second:0},doctor:ObjectId(newDoctor2._id)});
let newPatient6 = Models.Patients({name:{first:"kyle",last:"longwell"},dateOfBirth:new Date('1991-06-01').toISOString(),gender:"male",contact:{email: "fakeemail6@gmail.com",mobilenumber:"3698521470",address: "north 123258 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:18,minute:29,second:0},doctor:ObjectId(newDoctor2._id)});
let newPatient7 = Models.Patients({name:{first:"alex",last:"groppe"},dateOfBirth:new Date('1991-07-01').toISOString(),gender:"female",contact:{email: "fakeemail7@gmail.com",mobilenumber:"0654123987",address: "north 123147 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:17,minute:28,second:0},doctor:ObjectId(newDoctor2._id)});

let newPatient8 = Models.Patients({name:{first:"noah",last:"owens"},dateOfBirth:new Date('1991-08-01').toISOString(),gender:"male",contact:{email: "fakeemail8@gmail.com",mobilenumber:"3217412589",address: "north 123321 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:16,minute:27,second:0},doctor:ObjectId(newDoctor3._id)});
let newPatient9 = Models.Patients({name:{first:"kristen",last:"glatter"},dateOfBirth:new Date('1991-09-01').toISOString(),gender:"female",contact:{email: "fakeemail9@gmail.com",mobilenumber:"9633214568",address: "north 123654 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:15,minute:26,second:0},doctor:ObjectId(newDoctor3._id)});
let newPatient10 = Models.Patients({name:{first:"sarah",last:"wolf"},dateOfBirth:new Date('1991-10-01').toISOString(),gender:"female",contact:{email: "fakeemail10@gmail.com",mobilenumber:"9877899871",address: "north 123987 st",state:"tx",city:"arlington",zipcode:"76019"},admissionsDate:new Date('2015-11-19').toISOString(),priority:false,expectedWalkTime:{hour:14,minute:25,second:0},doctor:ObjectId(newDoctor3._id)});

let newHealth1_1 = Models.Health({health:0,start_time:new Date(2015,11,18,23,34,0,0).toISOString(),end_time:new Date(2015,11,18,23,35,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth1_2 = Models.Health({health:0,start_time:new Date(2015,11,19,23,34,0,0).toISOString(),end_time:new Date(2015,11,19,23,35,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth1_3 = Models.Health({health:0,start_time:new Date(2015,11,20,23,34,0,0).toISOString(),end_time:new Date(2015,11,20,23,35,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth1_4 = Models.Health({health:0,start_time:new Date(2015,11,21,23,34,0,0).toISOString(),end_time:new Date(2015,11,21,23,35,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth1_5 = Models.Health({health:0,start_time:new Date(2015,11,22,23,34,0,0).toISOString(),end_time:new Date(2015,11,22,23,35,0,0).toISOString(),patient:ObjectId(newPatient1._id)});

let newHealth2_1 = Models.Health({health:0,start_time:new Date(2015,11,18,22,23,0,0).toISOString(),end_time:new Date(2015,11,18,22,24,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth2_2 = Models.Health({health:0,start_time:new Date(2015,11,19,22,23,0,0).toISOString(),end_time:new Date(2015,11,19,22,24,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth2_3 = Models.Health({health:0,start_time:new Date(2015,11,20,22,23,0,0).toISOString(),end_time:new Date(2015,11,20,22,24,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth2_4 = Models.Health({health:0,start_time:new Date(2015,11,21,22,23,0,0).toISOString(),end_time:new Date(2015,11,21,22,24,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth2_5 = Models.Health({health:0,start_time:new Date(2015,11,22,22,23,0,0).toISOString(),end_time:new Date(2015,11,22,22,24,0,0).toISOString(),patient:ObjectId(newPatient1._id)});

let newHealth3_1 = Models.Health({health:0,start_time:new Date(2015,11,18,21,32,0,0).toISOString(),end_time:new Date(2015,11,18,21,33,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth3_2 = Models.Health({health:0,start_time:new Date(2015,11,19,21,32,0,0).toISOString(),end_time:new Date(2015,11,19,21,33,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth3_3 = Models.Health({health:0,start_time:new Date(2015,11,20,21,32,0,0).toISOString(),end_time:new Date(2015,11,20,21,33,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth3_4 = Models.Health({health:0,start_time:new Date(2015,11,21,21,32,0,0).toISOString(),end_time:new Date(2015,11,21,21,33,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth3_5 = Models.Health({health:0,start_time:new Date(2015,11,22,21,32,0,0).toISOString(),end_time:new Date(2015,11,22,21,33,0,0).toISOString(),patient:ObjectId(newPatient1._id)});

let newHealth4_1 = Models.Health({health:0,start_time:new Date(2015,11,18,20,31,0,0).toISOString(),end_time:new Date(2015,11,18,20,32,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth4_2 = Models.Health({health:0,start_time:new Date(2015,11,19,20,31,0,0).toISOString(),end_time:new Date(2015,11,19,20,32,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth4_3 = Models.Health({health:0,start_time:new Date(2015,11,20,20,31,0,0).toISOString(),end_time:new Date(2015,11,20,20,32,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth4_4 = Models.Health({health:0,start_time:new Date(2015,11,21,20,31,0,0).toISOString(),end_time:new Date(2015,11,21,20,32,0,0).toISOString(),patient:ObjectId(newPatient1._id)});
let newHealth4_5 = Models.Health({health:0,start_time:new Date(2015,11,22,20,31,0,0).toISOString(),end_time:new Date(2015,11,22,20,32,0,0).toISOString(),patient:ObjectId(newPatient1._id)});

let newHealth5_1 = Models.Health({health:0,start_time:new Date(2015,11,18,19,30,0,0).toISOString(),end_time:new Date(2015,11,18,19,31,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth5_2 = Models.Health({health:0,start_time:new Date(2015,11,19,19,30,0,0).toISOString(),end_time:new Date(2015,11,19,19,31,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth5_3 = Models.Health({health:0,start_time:new Date(2015,11,20,19,30,0,0).toISOString(),end_time:new Date(2015,11,20,19,31,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth5_4 = Models.Health({health:0,start_time:new Date(2015,11,21,19,30,0,0).toISOString(),end_time:new Date(2015,11,21,19,31,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth5_5 = Models.Health({health:0,start_time:new Date(2015,11,22,19,30,0,0).toISOString(),end_time:new Date(2015,11,22,19,31,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});

let newHealth6_1 = Models.Health({health:0,start_time:new Date(2015,11,18,18,29,0,0).toISOString(),end_time:new Date(2015,11,18,18,30,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth6_2 = Models.Health({health:0,start_time:new Date(2015,11,19,18,29,0,0).toISOString(),end_time:new Date(2015,11,19,18,30,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth6_3 = Models.Health({health:0,start_time:new Date(2015,11,20,18,29,0,0).toISOString(),end_time:new Date(2015,11,20,18,30,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth6_4 = Models.Health({health:0,start_time:new Date(2015,11,21,18,29,0,0).toISOString(),end_time:new Date(2015,11,21,18,30,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth6_5 = Models.Health({health:0,start_time:new Date(2015,11,22,18,29,0,0).toISOString(),end_time:new Date(2015,11,22,18,30,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});

let newHealth7_1 = Models.Health({health:0,start_time:new Date(2015,11,18,17,28,0,0).toISOString(),end_time:new Date(2015,11,18,17,29,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth7_2 = Models.Health({health:0,start_time:new Date(2015,11,19,17,28,0,0).toISOString(),end_time:new Date(2015,11,19,17,29,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth7_3 = Models.Health({health:0,start_time:new Date(2015,11,20,17,28,0,0).toISOString(),end_time:new Date(2015,11,20,17,29,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth7_4 = Models.Health({health:0,start_time:new Date(2015,11,21,17,28,0,0).toISOString(),end_time:new Date(2015,11,21,17,29,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});
let newHealth7_5 = Models.Health({health:0,start_time:new Date(2015,11,22,17,28,0,0).toISOString(),end_time:new Date(2015,11,22,17,29,0,0).toISOString(),patient:ObjectId(newDoctor2._id)});

let newHealth8_1 = Models.Health({health:0,start_time:new Date(2015,11,18,16,27,0,0).toISOString(),end_time:new Date(2015,11,18,16,28,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth8_2 = Models.Health({health:0,start_time:new Date(2015,11,19,16,27,0,0).toISOString(),end_time:new Date(2015,11,19,16,28,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth8_3 = Models.Health({health:0,start_time:new Date(2015,11,20,16,27,0,0).toISOString(),end_time:new Date(2015,11,20,16,28,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth8_4 = Models.Health({health:0,start_time:new Date(2015,11,21,16,27,0,0).toISOString(),end_time:new Date(2015,11,21,16,28,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth8_5 = Models.Health({health:0,start_time:new Date(2015,11,22,16,27,0,0).toISOString(),end_time:new Date(2015,11,22,16,28,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});

let newHealth9_1 = Models.Health({health:0,start_time:new Date(2015,11,18,15,26,0,0).toISOString(),end_time:new Date(2015,11,18,15,27,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth9_2 = Models.Health({health:0,start_time:new Date(2015,11,19,15,26,0,0).toISOString(),end_time:new Date(2015,11,19,15,27,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth9_3 = Models.Health({health:0,start_time:new Date(2015,11,20,15,26,0,0).toISOString(),end_time:new Date(2015,11,20,15,27,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth9_4 = Models.Health({health:0,start_time:new Date(2015,11,21,15,26,0,0).toISOString(),end_time:new Date(2015,11,21,15,27,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth9_5 = Models.Health({health:0,start_time:new Date(2015,11,22,15,26,0,0).toISOString(),end_time:new Date(2015,11,22,15,27,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});

let newHealth10_1 = Models.Health({health:0,start_time:new Date(2015,11,18,14,25,0,0).toISOString(),end_time:new Date(2015,11,18,14,26,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth10_2 = Models.Health({health:0,start_time:new Date(2015,11,19,14,25,0,0).toISOString(),end_time:new Date(2015,11,19,14,26,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth10_3 = Models.Health({health:0,start_time:new Date(2015,11,20,14,25,0,0).toISOString(),end_time:new Date(2015,11,20,14,26,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth10_4 = Models.Health({health:0,start_time:new Date(2015,11,21,14,25,0,0).toISOString(),end_time:new Date(2015,11,21,14,26,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});
let newHealth10_5 = Models.Health({health:0,start_time:new Date(2015,11,22,14,25,0,0).toISOString(),end_time:new Date(2015,11,22,14,26,0,0).toISOString(),patient:ObjectId(newDoctor3._id)});


db.createDoctor(newDoctor1, replyDoc);
db.createDoctor(newDoctor2, replyDoc);
db.createDoctor(newDoctor3, replyDoc);

db.createPatient(newPatient1, replyPatient);
db.createPatient(newPatient2, replyPatient);
db.createPatient(newPatient3, replyPatient);
db.createPatient(newPatient4, replyPatient);
db.createPatient(newPatient5, replyPatient);
db.createPatient(newPatient6, replyPatient);
db.createPatient(newPatient7, replyPatient);
db.createPatient(newPatient8, replyPatient);
db.createPatient(newPatient9, replyPatient);
db.createPatient(newPatient10, replyPatient);

db.createHealth(newHealth1_1, replyHealth);
db.createHealth(newHealth1_2, replyHealth);
db.createHealth(newHealth1_3, replyHealth);
db.createHealth(newHealth1_4, replyHealth);
db.createHealth(newHealth1_5, replyHealth);
db.createHealth(newHealth2_1, replyHealth);
db.createHealth(newHealth2_2, replyHealth);
db.createHealth(newHealth2_3, replyHealth);
db.createHealth(newHealth2_4, replyHealth);
db.createHealth(newHealth2_5, replyHealth);
db.createHealth(newHealth3_1, replyHealth);
db.createHealth(newHealth3_2, replyHealth);
db.createHealth(newHealth3_3, replyHealth);
db.createHealth(newHealth3_4, replyHealth);
db.createHealth(newHealth3_5, replyHealth);
db.createHealth(newHealth4_1, replyHealth);
db.createHealth(newHealth4_2, replyHealth);
db.createHealth(newHealth4_3, replyHealth);
db.createHealth(newHealth4_4, replyHealth);
db.createHealth(newHealth4_5, replyHealth);
db.createHealth(newHealth5_1, replyHealth);
db.createHealth(newHealth5_2, replyHealth);
db.createHealth(newHealth5_3, replyHealth);
db.createHealth(newHealth5_4, replyHealth);
db.createHealth(newHealth5_5, replyHealth);
db.createHealth(newHealth6_1, replyHealth);
db.createHealth(newHealth6_2, replyHealth);
db.createHealth(newHealth6_3, replyHealth);
db.createHealth(newHealth6_4, replyHealth);
db.createHealth(newHealth6_5, replyHealth);
db.createHealth(newHealth7_1, replyHealth);
db.createHealth(newHealth7_2, replyHealth);
db.createHealth(newHealth7_3, replyHealth);
db.createHealth(newHealth7_4, replyHealth);
db.createHealth(newHealth7_5, replyHealth);
db.createHealth(newHealth8_1, replyHealth);
db.createHealth(newHealth8_2, replyHealth);
db.createHealth(newHealth8_3, replyHealth);
db.createHealth(newHealth8_4, replyHealth);
db.createHealth(newHealth8_5, replyHealth);
db.createHealth(newHealth9_1, replyHealth);
db.createHealth(newHealth9_2, replyHealth);
db.createHealth(newHealth9_3, replyHealth);
db.createHealth(newHealth9_4, replyHealth);
db.createHealth(newHealth9_5, replyHealth);
db.createHealth(newHealth10_1, replyHealth);
db.createHealth(newHealth10_2, replyHealth);
db.createHealth(newHealth10_3, replyHealth);
db.createHealth(newHealth10_4, replyHealth);
db.createHealth(newHealth10_5, replyHealthWithDisconnect);

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
function replyPatient(err, patient){
    if(err) {
        console.log("patient err: "+ err);
    }else if(patient){
        console.log("Success: Patient");
    }
    else{
        console.log("This shouldn't have happend");
    }
}
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

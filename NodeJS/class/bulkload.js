/**
 * Created by kevin on 11/19/2015.
 */


//To run
//node bulkload_database.js

'use strict';
let Security = require('./Security');
let Models = require('../models');
let dbmgr = require('../controllers/bridge');


let db = dbmgr.bridge(1);
db.connectDB();

let newDoctor1 = Models.Doctors({email:"hadi.abdul@mavs.uta.edu",password:Security.hashPassword("doc1"),name:{first:"hadi",last:"abdul"},contact:{mobilenumber:"2146494367",officenumber:"1111111111",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor2 = Models.Doctors({email:"jesus.linares@mavs.uta.edu",password:Security.hashPassword("doc2"),name:{first:"jesus",last:"linares"},contact:{mobilenumber:"8172712643",officenumber:"2222222222",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});
let newDoctor3 = Models.Doctors({email:"kevin.kocian@mavs.uta.edu",password:Security.hashPassword("doc3"),name:{first:"kevin",last:"kocian"},contact:{mobilenumber:"2546449059",officenumber:"333333333",officeaddress:"701 S Nedderman Dr",state:"tx",city:"arlington",zipcode:"76019"},accessToken:""});

db.createDoctor(newDoctor1, reply);
db.createDoctor(newDoctor2, reply);
db.createDoctor(newDoctor3, replyWithDisconnect);

function reply(err, doctor){
    if(err){
        console.log("err");
    } else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
}

function replyWithDisconnect(err, doctor){
    if(err){
        console.log("err");
    } else if(doctor){
        console.log("true");
    }
    else{
        console.log("This shouldn't have happend");
    }
    db.disconnectDB();
}

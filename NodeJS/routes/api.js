'use strict';

var
  express = require('express'),
  apiAccount = require('./api_account.js'),
  apiPatient = require('./api_patient.js'),
  jwt = require('jsonwebtoken');

let router = express.Router();

router.use('/api', apiAccount);
router.use('/api', verify);
router.use('/api', apiPatient);

function verify(req, res, next){
    let token = req.body.token;

    jwt.verify(token, apiAccount.get('secret'), function(err, decoded){
        if(err){
            //res.send(err);
            return res.json({success: false, message: err});
        }
        else{
            next();
        }
    });
}

module.exports = router;
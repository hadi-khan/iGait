'use strict';

let express = require('express');
let apiAccount = require('./api_account.js');
let apiPatient = require('./api_patient.js');
let apiPublic = require('./api_public');
let Security = require('../class/Security');

const MISSING_HEADER = 'missing required header';

let router = express.Router();

router.use('/api', apiPublic);
router.use('/api', verify);
router.use('/api', apiAccount);
router.use('/api', apiPatient);

function verify(req, res, next){
    if(!(req.header('authorization') && req.header('id'))){
        res.json({success: 'false', message: MISSING_HEADER})
    }else{
        let token = req.header('Authorization');

        Security.verifyToken(token, function(err, decoded){
            if(err){
                //res.send(err);
                res.json({success: 'false', message: err});
            }
            else{
                next();
            }
        });
    }
}

module.exports = router;
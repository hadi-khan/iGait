'use strict';

let express = require('express');
let dbmgr = require('../db/dbms.js');
let Security = require('../class/Security');


let db = new dbmgr();
let router = express.Router();

router.route('/account')
    .put(function (req, res){
        let changes = req.body;
        if(changes.password) {
            changes.password = Security.hashPassword(changes.password);
        }
        let doctorId = req.header('id');

        db.updateDoctor(doctorId, changes, reply);
        function reply(err, doctor){
            if(err){
                res.json({success: 'false', message: err});
            }else if(doctor){
                res.json({success: 'true', message: doctor});
            }
        }
    });

module.exports = router;


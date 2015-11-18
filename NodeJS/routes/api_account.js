'use strict';

var
    express = require('express'),
    app = express(),
    dbmgr = require('../controllers/bridge.js');

app.set('secret', 'secret1234');
let db = dbmgr.bridge(1);

module.exports = (function(){
    let router = express.Router();
    
    router.route('/removeDoctor')
        .post(function(req, res){
            db.removeDoctor(
                req.body.email,
                function callback(result){
                    //console.log("result in api: " + result.message);
                    if(result.success === 'true'){
                        //successfully removed user
                        //console.log("result.success === false_error");
                        res.send(result);
                    }
                    else if(result.success === 'false'){
                        //Doctor did not exist
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else if(result.success === 'false_error'){
                        //query came back with error
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else if(result.success === 'false_wrong'){
                        //something horrible went wrong in the database
                        //console.log("result.success === true");
                        res.send(result);
                    }
                    else{
                        //something went horribly wrong in api
                        res.send({success: 'false_wrong', function: '/removeDoctor-removeDoctor', message: 'This shouldnt have happen. Something went very wrong'});
                    }
                }
            );
        });

app.use('/account', router);

return app;
})();

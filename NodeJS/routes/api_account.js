'use strict';

var
  express = require('express'),
  app = express(),
  doctors = require('../controllers/doctorcontroller');

module.exports = (function(){
  let router = express.Router();

  router.route('/authentication')
    .post(function(req, res){
      res.send('Here is your token');
    });

  router.route('/recovery')
    .post(function(req, res){

    });

  router.route('/register')
    .post(function(req, res){
		var need = doctors.register(
			req.body.email,
			req.body.password,
			req.body.firstname,
			req.body.lastname,
			req.body.mobilenumber,
			req.body.officenumber,
			req.body.officeaddress);
		res.send(need);
    });
	
	//adds Kevin to the doctor database
	router.route('/adk')
		.get(function(req, res){
			doctors.addkevin();
    });
	//removes Kevin from the doctor database
	router.route('/rmk')
		.get(function(req, res){
			doctors.removekevin();
    });
	//for kevin's testing purposes - dont touch
	router.route('/a')
		.post(function(req, res){
			var need = doctors.c(req.body.email);
			res.send(need);
    });
	//for kevin's testing purposes - dont touch
	router.route('/b')
		.post(function(req, res){
			var need = doctors.c(req.body);
			res.send(need);
    });

  app.use('/account', router);  

  return app;
})();

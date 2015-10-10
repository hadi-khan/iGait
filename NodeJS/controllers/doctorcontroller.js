var path = require('path'),
    Models = require('../models');

module.exports = {

	//registers doctor
	//input: email, password, firstname, lastname, 
	//		 mobile number, office number, office address
    register: function(
			reqEmail,reqPassword,reqFirstname, reqLastname, 
			reqMobilenumber, reqOfficenumber, reqOfficeaddress) {
			
        var saveDoctor = function() {
			var newDoctor = new Models.Doctors({
				email: reqEmail,
				password: reqPassword,
				name: {
				  first: reqFirstname,
				  last: reqLastname
				},
				contact: { 
					mobilenumber: reqMobilenumber,
					officenumber: reqOfficenumber,
					officeaddress: reqOfficeaddress
				},
				patients: []
			});
			
			newDoctor.save(function(err, doctor) {
				console.log('Successfully inserted register doctor: ' + doctor.email);
			});
        };

		Models.Doctors.findOne({email: reqEmail}, function (err, docObj) {
		  if (err) {
			console.log(err);
		  } else if (docObj) {
			console.log('Found:', docObj + '\n');
			console.log('Didnt need to querry anything. Finish');
		  } else {
			console.log('User not found!');
			saveDoctor();
			if(err){
				console.log(err);
			}
			else{
				console.log('Done with register query');
			}
		  }
		});
    },
	
	//adds kevin to the database
	addkevin: function() {
        var saveDoctor = function() {
			var newDoctor = new Models.Doctors({
				email: 'kevin.kocian@mavs.uta.edu',
				password: 'password',
				name: {
				  first: 'kevin',
				  last: 'kocian'
				},
				contact: { 
					mobilenumber: 2546449059,
					officenumber: 2546449999,
					officeaddress: '1100 Greek Row Dr'
				},
				patients: []
			});
			
			newDoctor.save(function(err, doctor) {
				console.log('Successfully inserted doctor: ' + doctor.email);
			});
        };

		Models.Doctors.findOne({email: 'kevin.kocian@mavs.uta.edu'}, function (err, docObj) {
		  if (err) {
			console.log(err);
		  } else if (docObj) {
			console.log('Found:', docObj + '\n');
			console.log('Didnt need to querry anything. Finish');
		  } else {
			console.log('User not found!');
			saveDoctor();
			if(err){
				console.log(err);
			}
			else{
				console.log('Done with create query');
			}
		  }
		});
    },
	
	//removes kevin from database
	removekevin: function() {
        var removeDoctor = function(docObj) {
			docObj.remove();
        };

		Models.Doctors.findOne({email: 'kevin.kocian@mavs.uta.edu'}, function (err, docObj) {
		  if (err) {
			console.log(err);
		  } else if (docObj) {
			console.log('Found:', docObj + '\n');
			removeDoctor(docObj);
			if(err){
				console.log(err);
			}
			else{
				console.log('Done with remove query');
			}
		  } else {
			console.log('User not found!');
			console.log('Didnt need to querry anything. Finish');
		  }
		});
    },
	
	//for kevin testing purpose - dont touch
	c: function(something) {
        console.log(something);
		return "back";
    }
};

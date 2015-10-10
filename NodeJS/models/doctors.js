var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = mongoose.Schema.Types.ObjectId;


//defining the doctors schema
var doctorsSchema = new Schema({
	date_created: { type: Date, default: Date.now },
	email: { type: String, required: true, unique: true},
	password: { type: String, required: true},
	name: {
	  first: { type: String, required: true, trim: true},
	  last: { type: String, required: true, trim: true}
	},
	contact: { 
		mobilenumber: { type: Number },
		officenumber: { type: Number },
		officeaddress: { type: String }
	},
	patients: [{ type : ObjectId, ref: 'Patients' }],
	accessToken: { type: String } // Used for Remember Me
});


module.exports = mongoose.model('DoctorsModel', doctorsSchema);


//var DoctorsModel = mongoose.model('Doctors', doctorsSchema);

//creating a new doctor
//var newDoctor = new DoctorsModel({
//	email: 'kevin.kocian@mavs.uta.edu',
//	password: 'password',
//	name: {
//	  first: 'kevin',
//	  last: 'kocian'
//	},
//	contact: { 
//		phonenumber: 2546449059,
//		officenumber: 2546449999,
//		officeaddress: '1100 Greek Row Dr'
//	},
//	patients: []
//});

//save new doctor into the database
//newDoctor.save();

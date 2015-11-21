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
		officeaddress: { type: String },
		state: {type: String, required: true},
		city: {type: String, required: true},
		zipcode: {type: Number, required: true}
	},
	accessToken: { type: String } // Used for Remember Me
});
module.exports = mongoose.model('DoctorsModel', doctorsSchema); //Database Table is name Doctors

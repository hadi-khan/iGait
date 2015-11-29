var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	ObjectId = mongoose.Schema.Types.ObjectId;


//defining the patient schema
var patientsSchema = new Schema({
	date_created: { type: Date, default: Date.now },
	name: {
		first: { type: String, required: true, trim: true},
		last: { type: String, required: true, trim: true}
	},
	dateOfBirth: {type: Date, required: true},
	gender: {type: String, required: true},
	contact: {
		email: { type: String, required: true, unique: true },
		mobilenumber: { type: Number, require: true },
		address: {type: String, required: true},
		state: {type: String, required: true},
		city: {type: String, required: true},
		zipcode: {type: Number, required: true}
	},
	priority: {type: Boolean, required: true},
	expectedWalkTime: {
		hour: {type: Number, required: true},
		minute: {type: Number, required: true},
		second: {type: Number, required: true}
	},
	doctor: { type : ObjectId, ref: 'Doctors' } //the export name in models/index.js
});

module.exports = mongoose.model('PatientsModels', patientsSchema); //Database Table is name patientsmodels





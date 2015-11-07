var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	ObjectId = mongoose.Schema.Types.ObjectId;


//defining the patient schema
var patientsSchema = new Schema({
	date_created: { type: Date, default: Date.now },
	email: { type: String, required: true, unique: true},
	password: { type: String, required: true},
	name: {
		first: { type: String, required: true, trim: true},
		last: { type: String, required: true, trim: true}
	},
	dateOfBirth: {type: Date},
	admissionsDate: {type: Date, required: true},
	address: {type: String, required: true}
});

module.exports = mongoose.model('Patients', patientsSchema);


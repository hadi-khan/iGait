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
		mobilenumber: { type: Number },
		state: {type: String, required: true},
		city: {type: String, required: true},
		zipcode: {type: Number, required: true}
	},
	admissionsDate: {type: Date, required: true},
	priority: {type: Boolean, required: true},
	doctor: { type : ObjectId, ref: 'Doctors' }
});

module.exports = mongoose.model('Patients', patientsSchema);





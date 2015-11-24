var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	ObjectId = mongoose.Schema.Types.ObjectId;


//defining the patient schema
var healthSchema = new Schema({
	date_created: { type: Date, default: Date.now, required: true},
	health: {type: Number, min: 0, max: 3, required: true},
	start_time: {type: Date, required: true},
	end_time: {type: Date, required: true},
	patient: { type : ObjectId, ref: 'Patients' }//the export name in models/index.js
});

module.exports = mongoose.model('HealthModels', healthSchema);





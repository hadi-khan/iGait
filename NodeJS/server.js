'use strict';

var
  express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  mongoose = require('mongoose'),
  Schema = mongoose.Schema,
  ObjectId = mongoose.Schema.Types.ObjectId;


var port = process.env.PORT || 80;

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());
// BASIC ROUTES---------------------------

// Route for every connection.
app.use(function(req, res, next){
  console.log('someone is using the api!');
  next();
});

app.get('/', function(req, res){
  res.send('iGait API server!');
});

// API ROUTES-----------------------------
var routes = express.Router();

routes.post('/authentication', function(req, res){
  res.send('Here is your token');
});

// Token verification before allowing use of API resources.
routes.use(function(req, res, next){
  console.log('This token looks ok, let that user through.');
  next();
});

app.use('/api', routes);


//connecting to mongodb with mongoose
mongoose.connect('mongodb://localhost:27017/mongotest');
mongoose.connection.on('open', function() {
console.log('Mongoose connected.');
});


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

var DoctorsModel = mongoose.model('Doctors', doctorsSchema);
exports.Doctors = mongoose.model('Doctors', doctorsSchema)

//creating a new doctor
var newDoctor = new DoctorsModel({
	email: 'kevin.kocian@mavs.uta.edu',
	password: 'password',
	name: {
	  first: 'kevin',
	  last: 'kocian'
	},
	contact: { 
		phonenumber: 2546449059,
		officenumber: 2546449999,
		officeaddress: '1100 Greek Row Dr'
	},
	patients: []
});

//save new doctor into the database
//newDoctor.save();

;


// SERVER START---------------------------
app.listen(port, function(){
  console.log(`Listening for connections on port ${port}`);
});

var doctors = require('../controllers/doctorcontroller');



module.exports.initialize = function(app) {
	//////////////////////////////ross code from before
	//////////////////////////////do we need this?
	//////////////////////////////add/change/remove accordingly
	// API ROUTES-----------------------------
	//var routes = express.Router();
	//routes.post('/authentication', function(req, res){
	//  res.send('Here is your token');
	//});
	// Token verification before allowing use of API resources.
	//routes.use(function(req, res, next){
	//  console.log('This token looks ok, let that user through.');
	//  next();
	//});
	//app.use('/api', routes);
	
	
	//Route for every connection.
	app.use(function(req, res, next){
	  console.log('someone is using the api!');
	  next();
	});
	
	//localhost
    app.get('/', function(req, res){
		console.log('API server reached');
		res.send('iGait API server!');
	});
	
	//registers doctor
	//input: email, password, firstname, lastname, 
	//		 mobile number, office number, office address
	app.post('/registerdoctor', function(req, res){
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
	app.get('/adk', doctors.addkevin);
	//removes Kevin from the doctor database
	app.get('/rmk', doctors.removekevin);
	
	//for kevin's testing purposes - dont touch
	app.post('/a', function(req, res){
		var need = doctors.c(req.body.email);
		res.send(need);
	});
	//for kevin's testing purposes - dont touch
	app.post('/b', function(req, res){
		var need = doctors.c(req.body);
		res.send(need);
	});
	
};

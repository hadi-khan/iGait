var bodyParser = require('body-parser'),
    path = require('path'),
	routeAPI = require('../routes/api.js');


module.exports = function(app) {
	app.use(bodyParser.urlencoded({extended: false}));
	app.use(bodyParser.json());
	app.use(routeAPI);
    return app;
};

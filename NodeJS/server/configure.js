var bodyParser = require('body-parser'),
    path = require('path'),
    routes = require('./routes');


module.exports = function(app) {
	app.use(bodyParser.urlencoded({extended: false}));
	app.use(bodyParser.json());

    routes.initialize(app);
    return app;
};

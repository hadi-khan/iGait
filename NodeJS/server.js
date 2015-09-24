"use strict";

var net = require('net');

var server = net.createServer(function(connection){
  connection.on('error', function(err){
    console.error(err);
  });

  connection.on('data', function(data){
    //Authenticate each request in the future
    //Should be receiving an encrypted string.
    //Create seperate module for handling the delgation of requests?

    //Check for errors asap, the faster bad connections end the better.
    try{
      /*
      1) Decrypt string
      2) Parse for object (JSON.parse())
      3) Execute request if possible
      4) Convert result to JSON string (JSON.stringify())
      5) Encrypt the result
      6) Send ecnrypted result back to client
      */
      let request = JSON.parse(data.toString());
      console.log(user);
    }
    catch(error){
      if (error instanceof SyntaxError) {
        console.error(error);
      }
    }
  });

  connection.on('end', function(){
    console.log('client disconnected');
  })

  console.log("client connected");
});

server.on('error', function(err){
  console.error(err);
});

server.listen({
  port: 8080
}, function(){
  console.log('server bound to port');
});

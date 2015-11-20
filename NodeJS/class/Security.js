/**
 * Created by spart on 11/10/2015.
 */
'use strict';

//PRIVATE CONSTANTS
const NUM_BYTES = 10;
const HASH_FUNCT = 'sha256';
const ENCODING = 'utf8';
const BASE = 'hex';

//IMPORTED MODULES
let crypto = require('crypto');
let jwt = require('jsonwebtoken');

//PRIVATE VARIABLES
let secret = 'secret1234';  //Get this from a settings file.
secret = hash(secret);

//PRIVATE FUNCTIONS
function makeSalt(){
    return crypto.randomBytes(NUM_BYTES);
}

function hash(input){
    return crypto.createHash(HASH_FUNCT).update(input, ENCODING).digest(BASE);
}

// PUBLIC CLASS
class Security{

    static hashPassword(password){
        let salt = makeSalt();

        password = salt + password;
        password = hash(password);
        password = salt + '.' + password;
        return password;
    }

    static verifyPassword(password, stored){
        console.log(password);
        console.log(stored);
        // Parsed the stored password like this just in case
        // added random bytes produce a "." (delimiter)
        let index = stored.lastIndexOf('.');
        let salt = stored.slice(0, index);
        let storedPassword = stored.slice(index + 1);

        password = salt + password;
        console.log(password);
        password = hash(password);

        console.log(salt);
        console.log(password);

        let result = (password === storedPassword);
        console.log(result);
        return result;
    }

    static verifyToken(token, callback){
        console.log('Verify: ' + token);
        jwt.verify(token, secret, function(err, decoded){
            if(err)
                console.error(err);
            else
                console.log(decoded);
            callback(err, decoded);
        });
    }

    static sign(user){
        return jwt.sign(user, secret);
    }
}

module.exports = Security;

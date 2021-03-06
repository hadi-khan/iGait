/**
 * Created by spart on 11/10/2015.
 */
'use strict';

//PRIVATE CONSTANTS
/**
 * Number of bytes to salt password with.
 * @memberOf Security
 * @constant
 * @type {number}
 */
const NUM_BYTES = 10;
/**
 * Defines the hashing method to be used.  Method must be available on host system.
 * @memberOf Security
 * @type {string}
 */
const HASH_FUNCT = 'sha256';
/**
 * Defines the text encoding to be used.
 * @constant
 * @memberOf Security
 * @type {string}
 */
const ENCODING = 'utf8';
/**
 * Defines the character base to be used.
 * @memberOf Security
 * @constant
 * @type {string}
 */
const BASE = 'hex';

//IMPORTED MODULES
let crypto = require('crypto');
let jwt = require('jsonwebtoken');

//PRIVATE VARIABLES
let secret = 'secret1234';  //Get this from a settings file.
/**
 * The secret used for hashing
 * @memberOf Security
 * @type {string}
 */
secret = hash(secret);

//PRIVATE FUNCTIONS
/**
 * Generates a NUM_BYTES sized salt, in bytes.
 * @memberOf Security
 * @returns {byte} random bytes
 */
function makeSalt(){
    return crypto.randomBytes(NUM_BYTES);
}
/**
 * Hashes the given input
 * @memberOf Security
 * @param input {string} the password to hash
 * @returns {string} the hashed string
 */
function hash(input){
    return crypto.createHash(HASH_FUNCT).update(input, ENCODING).digest(BASE);
}

// PUBLIC CLASS
/**
 * @classdesc Implements utility class to handle security operations for the application.
 * @class
 */
class Security{
    /**
     * Hashes the password for DATABASE storage only.
     * @param password {string} user given password
     * @returns {string|*} The hashed password with salt appended to it.
     */
    static hashPassword(password){
        let salt = makeSalt();

        password = salt + password;
        password = hash(password);
        password = salt + '.' + password;
        return password;
    }

    /**
     * Checks if password is the is equivalent to stored after password has been hashed with the stored salt.
     * @param password {string} user given password
     * @param stored {string} hashed password stored in database
     * @returns {boolean}
     */
    static verifyPassword(password, stored){
        // Parsed the stored password like this just in case
        // added random bytes produce a "." (delimiter)
        let index = stored.lastIndexOf('.');
        let salt = stored.slice(0, index);
        let storedPassword = stored.slice(index + 1);

        password = salt + password;
        password = hash(password);

        let result = (password === storedPassword);
        return result;
    }

    /**
     * Checks the validity of the given token.
     * @param token {string} authentication token
     * @param callback {function} callback function
     */
    static verifyToken(token, callback){
        jwt.verify(token, secret, function(err, decoded){
            callback(err, decoded);
        });
    }

    /**
     * Generates a new authentication token.
     * @param user {User} the user that signed in
     * @returns {string} Authentication token
     */
    static sign(user){
        return jwt.sign(user, secret);
    }
}

module.exports = Security;

define({ "api": [
  {
    "type": "put",
    "url": "/api/account",
    "title": "Update an existing User",
    "name": "PutAccount",
    "group": "Account",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "success",
            "description": "<p>true</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "message",
            "description": "<p>User data</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "message.email",
            "description": "<p>User email</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "message.password",
            "description": "<p>User password, hashed</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "message.name",
            "description": "<p>User   name</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": ".name.first",
            "description": "<p>User first name</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": ".name.last",
            "description": "<p>User last name</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "message.contact",
            "description": "<p>User contact info</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": ".contact.officenumber",
            "description": "<p>User office number</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": ".contact.officeaddress",
            "description": "<p>User office address</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": ".contact.state",
            "description": "<p>State</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": ".contact.city",
            "description": "<p>City</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": ".contact.zipcode",
            "description": "<p>Zipcode</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"_id\": \"5668431d462d814c6cfa246a\",\n    \"email\": \"john@mavs.uta.edu\",\n    \"password\": \"apwoijepofiajw.ajwieojfapowijefopiajwioejfoiajwoejfaiwejf\",\n    \"name\" :{\n        \"first\": \"John\",\n        \"last\": \"Doe\"\n    },\n    \"contact\": {\n        \"officenumber\": 123456789,\n        \"officeaddress\": \"1000 Texas Drive\",\n        \"state\": \"Texas\",\n        \"city\": \"Arlington\",\n        \"zipdoce\": 76013\n    }\n    \"date_created\": \"2015-12-09T15:05:01.245Z\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_account.js",
    "groupTitle": "Account",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/api/patient/patientID",
    "title": "Delete a current patient",
    "name": "DeletePatient",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "patientID",
            "description": "<p>Patient object id</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "success",
            "description": "<p>true</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "message",
            "description": "<p>patient deleted</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n  \"success\": \"true\",\n  \"message\": \"patient deleted\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_patient.js",
    "groupTitle": "Patient",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/api/patient",
    "title": "Get all patients for user",
    "name": "GetPatient",
    "group": "Patient",
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_patient.js",
    "groupTitle": "Patient",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/api/patient/health/patientID",
    "title": "Get patient's health information",
    "name": "GetPatientHealth",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "patientID",
            "description": "<p>String representing object id in patient</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "health",
            "description": "<p>Number representing health of patient (0 - 3)</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Date</p> ",
            "optional": false,
            "field": "start_time",
            "description": "<p>Start date of the health recording</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>Date</p> ",
            "optional": false,
            "field": "end_time",
            "description": "<p>End date of the health recording</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "patient",
            "description": "<p>String representing the object id of the patient</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "skeleton_location",
            "description": "<p>File location of skeleton rendering.</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "video_location",
            "description": "<p>File location of the raw video recording.</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_patient.js",
    "groupTitle": "Patient",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/api/patient",
    "title": "Create a new patient",
    "name": "PostPatient",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "name",
            "description": "<p>name info</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name.first",
            "description": "<p>first name</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name.last",
            "description": "<p>last name</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Date</p> ",
            "optional": false,
            "field": "dateOfBirth",
            "description": "<p>date of birth</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "gender",
            "description": "<p>male or female</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact",
            "description": "<p>contact info</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.email",
            "description": "<p>patient email</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.mobilenumber",
            "description": "<p>patient mobile</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.address",
            "description": "<p>patient address</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.state",
            "description": "<p>state</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.city",
            "description": "<p>city</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.zipcode",
            "description": "<p>zipcode</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Boolean</p> ",
            "optional": false,
            "field": "priority",
            "description": "<p>mark patient as priority</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "expectedWalkTime",
            "description": "<p>walking time info</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "expectedWalkTime.hour",
            "description": "<p>hours of walking time</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "expectedWalkTime.minute",
            "description": "<p>minutes of walking time</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "expectedWalkTime.second",
            "description": "<p>seconds of walking time</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_patient.js",
    "groupTitle": "Patient",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/api/patient/patientID",
    "title": "Update a current patient",
    "name": "PutPatient",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "patientID",
            "description": "<p>Patient object id</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "success",
            "description": "<p>true</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "message",
            "description": "<p>patient updated</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\": \"true\",\n    \"message\": \"patient updated\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_patient.js",
    "groupTitle": "Patient",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>authentication token</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>user object id</p> "
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/api/authentication",
    "title": "Login with email and password",
    "name": "PostPublic",
    "group": "Public",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>user email</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>user password</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n    \"success\": \"true\",\n    \"message\": {\n        \"_id\": \"5668431d462d814c6cfa246a\",\n        \"email\": \"jesus.linares@mavs.uta.edu\",\n        \"password\": \"�bE�f\\u001aX���.a05937690ee24ea1f22ec961638f5b6a96b61f40dce2ff96ee818c15ce56aed7\",\n        \"accessToken\": \"\",\n        \"__v\": 0,\n        \"contact\": {\n            \"officenumber\": 2222222222,\n            \"officeaddress\": \"701 S Nedderman Dr\",\n            \"state\": \"tx\",\n            \"city\": \"arlington\",\n            \"zipcode\": 76019\n        },\n        \"name\": {\n            \"first\": \"jesus\",\n            \"last\": \"linares\"\n        },\n        \"date_created\": \"2015-12-09T15:05:01.245Z\"\n    }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_public.js",
    "groupTitle": "Public"
  },
  {
    "type": "post",
    "url": "/api/register",
    "title": "Register a new user",
    "name": "PostPublicRegister",
    "group": "Public",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>user email</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>password</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "name",
            "description": "<p>name info</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name.first",
            "description": "<p>first name</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name.last",
            "description": "<p>last name</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Object</p> ",
            "optional": false,
            "field": "contact",
            "description": "<p>contact info</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "contact.officenumber",
            "description": "<p>office number</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.officeaddress",
            "description": "<p>address</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.state",
            "description": "<p>state</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "contact.city",
            "description": "<p>city</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>Number</p> ",
            "optional": false,
            "field": "contact.zipcode",
            "description": "<p>zipcode</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_public.js",
    "groupTitle": "Public"
  },
  {
    "type": "post",
    "url": "/api/reset",
    "title": "Reset the users password",
    "description": "<p>This should be changed to use email verification asap.</p> ",
    "name": "PostPublicReset",
    "group": "Public",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p> "
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>password</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>email</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "NodeJS/routes/api_public.js",
    "groupTitle": "Public"
  }
] });
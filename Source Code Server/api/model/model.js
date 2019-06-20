const mysql = require('mysql');

const con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "basmnjnszk465",
  database: "sqa2"
});
module.exports = con;
const request = require('request');
const fs = require('fs');
module.exports.index = (req, res)=>{
    let khoa = req.query.khoa
    let data = fs.readFileSync('./info/'+khoa+'.json');
    res.json(JSON.parse(data));
}
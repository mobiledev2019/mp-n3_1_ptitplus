var con = require('../model/model')
module.exports.index = (req, res)=>{
    let gv = req.query.gv;
    let mon = req.query.mon;
    let sql = 'SELECT * FROM CMT WHERE gv=? AND mon=?';
    con.query(sql, [gv, mon], (err, rs, fields)=>{
    	if (err) throw err
    	res.json(rs);
    })    
}
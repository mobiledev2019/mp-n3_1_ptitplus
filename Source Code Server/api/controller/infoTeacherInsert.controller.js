var con = require('../model/model')
module.exports.index = (req, res)=>{
    let gv = req.query.gv;
    let mon = req.query.mon;
    let sv = req.query.sv;
    let cmt = req.query.cmt;
    let sql = 'INSERT INTO CMT VALUES (?, ?, ?, ?)';
    con.query(sql, [gv, mon, sv, cmt], (err, rs)=>{
    	if (err){
    		let sql2 = 'UPDATE CMT SET cmt=? WHERE gv=? and mon=? and sv=?';
    		con.query(sql2, [cmt, gv, mon, sv], (err, rs)=>{
    			if (err) throw err;
    			res.end('done');
    		})
    	}
    	res.end("done");
    })    
}
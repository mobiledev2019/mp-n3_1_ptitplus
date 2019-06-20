const pointModel = require('../model/model')
module.exports.index =  (req, res, next) =>{
    let msv = req.query.msv.toUpperCase();
    let sql = `SELECT * FROM sqa2.subject where msv="${msv}";`
    
    pointModel.query(sql, (err, result) =>{
        if(err) throw err
        res.json(result)
    })
}
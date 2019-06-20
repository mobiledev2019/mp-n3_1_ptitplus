const request = require('request');
const cheerio = require('cheerio');
module.exports.index = (req, res)=>{
    let msv = req.query.msv
    let url = `http://qldt.ptit.edu.vn/Default.aspx?page=gioithieu`
    
    request(url, (error, response, html)=>{
        const $ = cheerio.load(html)
        var body = $('span.TextThongTin')
        var temp = []
        $(body).each(function(i, span){
            temp.push($(this).text())
        })
        res.json({content : temp[0]})
        
    });
}
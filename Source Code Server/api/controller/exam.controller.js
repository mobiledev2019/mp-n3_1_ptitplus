const request = require('request');
const cheerio = require('cheerio');
module.exports.index = (req, res)=>{
    let msv = req.query.msv
    let url = `http://qldt.ptit.edu.vn/default.aspx?page=xemlichthi&id=${msv}`
    
    request(url, (error, response, html)=>{
        const $ = cheerio.load(html)
        var temp =[]
        let body = $('.grid-view tbody tr td span.Label')
        $(body).each(function(i, span){
            temp.push($(this).text())
        })
        let json = []
        
        for(var i = 0; i< temp.length;i = i+10){
            let jsonObject = {}
            jsonObject.stt = temp[i + 0]
            jsonObject.MaMH = temp[i + 1]
            jsonObject.TenMH = temp[i + 2]
            jsonObject.NgayThi = temp[i + 5]
            jsonObject.GioBD = temp[i + 6]
            jsonObject.SoPhut = temp[i + 7]
            jsonObject.Phong = temp[i + 8]
            jsonObject.GhiChu = temp[i + 9]
            json.push(jsonObject)
        }
        res.json(json)
    });
}
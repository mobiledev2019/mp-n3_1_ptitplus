const cheerio = require('cheerio')
const request = require('request')

module.exports.index = (req, res1, next) =>{
    const link = req.query.link;
    var jar = request.jar();
    request.get({
        url : link,
        followAllRedirects : true, 
        jar : jar
    }, (err, res, body)=>{
        let $ = cheerio.load(body);
        let header = $('head').html();
        let data = $('.sections_group').html();
        let content = header + data;
        res1.end(content);
    });
};
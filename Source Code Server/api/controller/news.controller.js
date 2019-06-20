const request = require('request');
const cheerio = require('cheerio');
module.exports.index = (req, res1)=>{
    var jsonArray = [];
    request.get({
        url : 'http://portal.ptit.edu.vn/giaovu/'
    }, (err, res, body)=>{
        if (err)
            return;
        let $ = cheerio.load(body);
        let data  = $('.blog_wrapper .post-desc');
    // console.log(data.html())
    $(data).each((i, el)=>{
        let jsonObject = {};
        jsonObject.time = $(el).find('.post-date').text();
        jsonObject.link = $(el).find('.entry-title').find('a').attr('href');
        jsonObject.title = $(el).find('.entry-title').find('a').text();
        jsonArray.push(jsonObject);
    })
    console.log(jsonArray);
    res1.json(jsonArray);
});
}
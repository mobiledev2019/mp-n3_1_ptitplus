const request = require('request');
const cheerio = require('cheerio');
module.exports.index = (req, res1)=>{
    let majors = req.query.majors
    var arr = ['http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/ky-thuat-dien-tu-vien-thong/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/cong-nghe-ky-thuat-dien-dien-tu/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/cong-nghe-thong-tin/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/an-toan-thong-tin/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/cong-nghe-da-phuong-tien/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/quan-tri-kinh-doanh/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/marketing/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/ke-toan/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/truyen-thong-da-phuong-tien/',
    'http://portal.ptit.edu.vn/tuyensinh/nganh-hoc/thuong-mai-dien-tu/']
    request.get(arr[majors], (err, res, body)=>{
        if (err) throw err;
        const $ = cheerio.load(body);
        $('img').remove();
        $('.title').css('padding', '10px');
        let head = $('head');
        let data = $('.sections_group');
        let script = $('body script');
        let content = head + data + script;
        res1.end(content);
    })
}
const request = require('request');
const cheerio = require('cheerio');
const fs = require('fs');
var jar = request.jar();
var $;
var ten = "nobody";
var getTen = (user, pass, res1)=>{
    request.get({
        url : 'http://qldt.ptit.edu.vn/',
        followAllRedirects : true, 
        jar : jar
    }, (err, res, body)=>{
        if (err) {
            console.log(err);
            console.log("doan 1");
            res1.end("loi");
            return;
        }
        let $ = cheerio.load(body);
        var capcha = $('span#ctl00_ContentPlaceHolder1_ctl00_lblCapcha').text();
        if (capcha != null){
            request.post({
                url : 'http://qldt.ptit.edu.vn/default.aspx',
                form : {
                    __EVENTTARGET : '',
                    __EVENTARGUMENT : '',
                    __VIEWSTATE : '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',
                    __VIEWSTATEGENERATOR : 'CA0B0334',
                    ctl00$ContentPlaceHolder1$ctl00$txtCaptcha : 'RJO0A',
                    ctl00$ContentPlaceHolder1$ctl00$btnXacNhan : 'Vào website'
                },
                followAllRedirects : true,      
                jar : jar
            }, (err, res, body) =>{
                if (err){
                    console.log(err);
                    console.log("doan 2");
                    res1.end("loi");
                    return;
                }
                request.post({
                    url : 'http://qldt.ptit.edu.vn/default.aspx',
                    form : {
                        __EVENTTARGET : "",
                        __EVENTARGUMENT : "",
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa : user,
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau : pass,
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap : "Đăng Nhập"
                    },
                    followAllRedirects : true,
                    jar : jar
                },function (err, res, body) {
                    console.log('lan 2');
                    if (err){
                        console.log(err);
                        console.log("doan 3");
                        res1.end("loi");
                        return;
                    }
                    $ = cheerio.load(body);
                    ten = $('#ctl00_Header1_ucLogout_lblNguoiDung').text();
                    if (ten === ''){
                        ten = $('#ctl00_Header1_Logout1_lblNguoiDung').text();
                    }
                    if (ten === ''){
                        console.log("o day");
                        res1.json({ten:'nobody'});
                        return;
                    }
                    console.log(ten)
                    let arr = ten.split(' ');
                    ten = '';
                    for (let i = 2; i < arr.length-1; i++){
                        ten += (arr[i] + ' ');
                    }
                    ten = ten.trim();
                    var ngaySinh = '';
                    var lop = '';
                    var khoa = '';
                    request.get({
                        url : 'http://qldt.ptit.edu.vn/default.aspx',
                        qs : {
                            page : 'thoikhoabieu',
                            sta : 1
                        },  
                        followAllRedirects : true,
                        jar : jar
                    }, (err, res, data)=>{
                        if (err){
                            console.log(err);
                            console.log("doan 4");
                            res1.end("loi");
                            return;
                        }
                        $ = cheerio.load(data);
                        ngaySinh = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV').text().split(':')[1];
                        let tmp = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentLopSV').text();
                        tmp = tmp.split('-');
                        lop = tmp[0].trim();
                        khoa = tmp[tmp.length-1].trim().split(':')[1].trim();
                        res1.json({ten:ten, ngaySinh:ngaySinh, lop:lop, khoa:khoa});
                    });
                });
            });
        }else{
            request.post({
                url : 'http://qldt.ptit.edu.vn/default.aspx',
                form : {
                    __EVENTTARGET : "",
                    __EVENTARGUMENT : "",
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa : user,
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau : pass,
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap : "Đăng Nhập"
                },
                followAllRedirects : true,
                jar : jar
            },function (err, res, body) {
                if (err){
                    console.log(err);
                    res1.end("loi");
                    return;
                }
                $ = cheerio.load(body);
                ten = $('#ctl00_Header1_ucLogout_lblNguoiDung').text();
                if (ten === ''){
                    ten = $('#ctl00_Header1_Logout1_lblNguoiDung').text();
                }
                if (ten === ''){
                    res1.end("loi");
                    return;
                }
                console.log(ten)
                let arr = ten.split(' ');
                ten = '';
                for (let i = 2; i < arr.length-1; i++){
                    ten += (arr[i] + ' ');
                }
                ten = ten.trim();
                var ngaySinh = '';
                var lop = '';
                var khoa = '';
                request.get({
                    url : 'http://qldt.ptit.edu.vn/default.aspx',
                    qs : {
                        page : 'thoikhoabieu',
                        sta : 1
                    },  
                    followAllRedirects : true,
                    jar : jar
                }, (err, res, data)=>{
                    if (err){
                        console.log(err);
                        res1.end("loi");
                        return;
                    }
                    $ = cheerio.load(data);
                    fs.writeFileSync('test.html',data);
                    ngaySinh = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV').text().split(':')[1];
                    console.log(ngaySinh);
                    let tmp = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentLopSV').text();
                    tmp = tmp.split('-');
                    lop = tmp[0].trim();
                    khoa = tmp[tmp.length-1].trim().split(':')[1].trim();
                    res1.json({ten:ten, ngaySinh:ngaySinh, lop:lop, khoa:khoa});
                })
            });
        }
    });
}
module.exports.index = (req, res1)=>{
    let user = req.query.user
    let pass = req.query.pass
    request.get({
        url : 'http://qldt.ptit.edu.vn/',
        followAllRedirects : true, 
        jar : jar
    }, (err, res, body)=>{
        if (err) {
            console.log(err);
            res1.end("loi");
            return;
        }
        let $ = cheerio.load(body);
        var capcha = $('span#ctl00_ContentPlaceHolder1_ctl00_lblCapcha').text();
        if (capcha != null){
            request.post({
                url : 'http://qldt.ptit.edu.vn/default.aspx',
                form : {
                    __EVENTTARGET : '',
                    __EVENTARGUMENT : '',
                    __VIEWSTATE : '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',
                    __VIEWSTATEGENERATOR : 'CA0B0334',
                    ctl00$ContentPlaceHolder1$ctl00$txtCaptcha : 'RJO0A',
                    ctl00$ContentPlaceHolder1$ctl00$btnXacNhan : 'Vào website'
                },
                followAllRedirects : true,      
                jar : jar
            }, (err, res, body) =>{
                if (err){
                    console.log(err);
                    res1.end("loi");
                    return;
                }
                request.post({
                    url : 'http://qldt.ptit.edu.vn/default.aspx',
                    form : {
                        __EVENTTARGET : "",
                        __EVENTARGUMENT : "",
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa : user,
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau : pass,
                        ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap : "Đăng Nhập"
                    },
                    followAllRedirects : true,
                    jar : jar
                },function (err, res, body) {
                    if (err){
                        console.log(err);
                        res1.end("loi");
                        return;
                    }
                    $ = cheerio.load(body);
                    ten = $('#ctl00_Header1_ucLogout_lblNguoiDung').text();
                    if (ten === ''){
                        ten = $('#ctl00_Header1_Logout1_lblNguoiDung').text();
                    }
                    if (ten === ''){
                        getTen(user, pass, res1);
                        return;
                    }
                    console.log(ten)
                    let arr = ten.split(' ');
                    ten = '';
                    for (let i = 2; i < arr.length-1; i++){
                        ten += (arr[i] + ' ');
                    }
                    ten = ten.trim();
                    var ngaySinh = '';
                    var lop = '';
                    var khoa = '';
                    request.get({
                        url : 'http://qldt.ptit.edu.vn/default.aspx',
                        qs : {
                            page : 'thoikhoabieu',
                            sta : 1
                        },  
                        followAllRedirects : true,
                        jar : jar
                    }, (err, res, data)=>{
                        if (err){
                            console.log(err);
                            res1.end("loi");
                            return;
                        }
                        $ = cheerio.load(data);
                        fs.writeFileSync('test.html',data);
                        ngaySinh = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV').text().split(':')[1];
                        console.log(ngaySinh);
                        let tmp = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentLopSV').text();
                        tmp = tmp.split('-');
                        lop = tmp[0].trim();
                        khoa = tmp[tmp.length-1].trim().split(':')[1].trim();
                        res1.json({ten:ten, ngaySinh:ngaySinh, lop:lop, khoa:khoa});
                    })
                });

            });
        } else{
            request.post({
                url : 'http://qldt.ptit.edu.vn/default.aspx',
                form : {
                    __EVENTTARGET : "",
                    __EVENTARGUMENT : "",
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa : user,
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau : pass,
                    ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap : "Đăng Nhập"
                },
                followAllRedirects : true,
                jar : jar
            },function (err, res, body) {
                if (err){
                    console.log(err);
                    res1.end("loi");
                    return;
                }
                $ = cheerio.load(body);
                ten = $('#ctl00_Header1_ucLogout_lblNguoiDung').text();
                if (ten === ''){
                    ten = $('#ctl00_Header1_Logout1_lblNguoiDung').text();
                }
                if (ten === ''){
                    getTen(user, pass, res1);
                    return;
                }
                console.log(ten)
                let arr = ten.split(' ');
                ten = '';
                for (let i = 2; i < arr.length-1; i++){
                    ten += (arr[i] + ' ');
                }
                ten = ten.trim();
                var ngaySinh = '';
                var lop = '';
                var khoa = '';
                request.get({
                    url : 'http://qldt.ptit.edu.vn/default.aspx',
                    qs : {
                        page : 'thoikhoabieu',
                        sta : 1
                    },  
                    followAllRedirects : true,
                    jar : jar
                }, (err, res, data)=>{
                    if (err){
                        console.log(err);
                        res1.end("loi");
                        return;
                    }
                    $ = cheerio.load(data);
                    fs.writeFileSync('test.html',data);
                    ngaySinh = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV').text().split(':')[1];
                    console.log(ngaySinh);
                    let tmp = $('#ctl00_ContentPlaceHolder1_ctl00_lblContentLopSV').text();
                    tmp = tmp.split('-');
                    lop = tmp[0].trim();
                    khoa = tmp[tmp.length-1].trim().split(':')[1].trim();
                    res1.json({ten:ten, ngaySinh:ngaySinh, lop:lop, khoa:khoa});
                })
            });
        }
    });
}


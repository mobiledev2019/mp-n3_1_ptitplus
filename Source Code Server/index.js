const express = require('express')
const app = express()
const port = 3000

const bodyParser = require('body-parser')
app.use(bodyParser.json())


const pointRouter = require('./api/router/point.router')
app.use('/point', pointRouter)
const scheduleRouter =require('./api/router/schedule.router')
app.use('/schedule', scheduleRouter)
const examRouter = require('./api/router/exam.router')
app.use('/exam', examRouter)
const infoRouter = require('./api/router/info.router')
app.use('/info', infoRouter)
const signinRouter = require('./api/router/signin.router')
app.use('/signin', signinRouter)
const newsRouter = require('./api/router/news.router')
app.use('/news', newsRouter)
const newsContentRouter = require('./api/router/newsContent.router')
app.use('/newsContent', newsContentRouter)
const majorsRouter = require('./api/router/majors.router')
app.use('/majors', majorsRouter)
const infoTeacherRouter = require('./api/router/infoTeacher.router')
app.use('/infoTeacher', infoTeacherRouter)
const infoTeacherInsertRouter = require('./api/router/infoTeacherInsert.router')
app.use('/infoTeacherInsert', infoTeacherInsertRouter)
const infoTeacherSelectRouter = require('./api/router/infoTeacherSelect.router')
app.use('/infoTeacherSelect', infoTeacherSelectRouter)
const lichThiRouter = require('./api/router/lichThi.router')
app.use('/lichThi', lichThiRouter)



app.get('/', function (req, res) {
  res.send('this is my project using connect mongodb and android')
})


app.listen(port, ()=>{
    console.log('Server listening to 3000');
    
})

const express = require('express')
const router = express.Router()

const infoTeacherController = require('../controller/infoTeacher.controller')

router.get('/', infoTeacherController.index)

module.exports = router;


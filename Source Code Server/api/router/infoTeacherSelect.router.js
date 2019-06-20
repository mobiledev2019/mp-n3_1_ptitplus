const express = require('express')
const router = express.Router()

const infoTeacherSelectController = require('../controller/infoTeacherSelect.controller')

router.get('/', infoTeacherSelectController.index)

module.exports = router;


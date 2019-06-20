const express = require('express')
const router = express.Router()

const infoTeacherInsertController = require('../controller/infoTeacherInsert.controller')

router.get('/', infoTeacherInsertController.index)

module.exports = router;


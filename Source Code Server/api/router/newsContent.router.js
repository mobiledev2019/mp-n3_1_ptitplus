const express = require('express')
const router = express.Router()

const newsContentConntroller = require('../controller/newsContent.controller')

router.get('/', newsContentConntroller.index)

module.exports = router;
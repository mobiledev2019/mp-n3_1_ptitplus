const express = require('express')
const router = express.Router()

const scheludeConntroller = require('../controller/schedule.controller')

router.get('/', scheludeConntroller.index)

module.exports = router;
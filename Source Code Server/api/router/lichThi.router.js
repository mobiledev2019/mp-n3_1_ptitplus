const express = require('express')
const router = express.Router()

const lichThiController = require('../controller/lichThi.controller')

router.get('/', lichThiController.index)

module.exports = router;


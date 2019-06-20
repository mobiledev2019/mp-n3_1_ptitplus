const express = require('express')
const router = express.Router()

const pointController = require('../controller/point.controller')

router.get('/', pointController.index)

module.exports = router;
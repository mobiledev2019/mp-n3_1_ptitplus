const express = require('express')
const router = express.Router()

const majorsController = require('../controller/majors.controller')

router.get('/', majorsController.index)

module.exports = router;


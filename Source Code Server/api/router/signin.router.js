const express = require('express')
const router = express.Router()

const signinController = require('../controller/signin.controller')

router.get('/', signinController.index)

module.exports = router;


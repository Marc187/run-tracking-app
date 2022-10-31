const knexModule = require('knex');
const express = require('express');
const request = require('../database/courses.js');
const router = express.Router();
const auth = require('../middleware/authentification')
const userVerification = require('../middleware/user_verification')

router.get('/:id_utilisateur', auth, userVerification, async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur
        const data = await request.getCourses(id_utilisateur)

        res.status(200).json(data)
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
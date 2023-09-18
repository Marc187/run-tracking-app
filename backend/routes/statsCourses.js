const knexModule = require('knex');
const express = require('express');
const request = require('../database/statsCourses.js');
const router = express.Router();
const auth = require('../middleware/authentification')

router.get('/:id_utilisateur', auth, async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur
        const data = await request.getStatsCourses(id_utilisateur)

        // Authentification de l'Utilisateur
        if (req.user.id != id_utilisateur) return res.status(401).json({ message: "Unauthorized."})

        res.status(200).json(data)
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
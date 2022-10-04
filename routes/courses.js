const express = require('express');
const request = require('../database/courses.js');
const router = express.Router();

router.get('/:id_utilisateur', async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur
        const data = await request.getCourses(id_utilisateur)

        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course enregistrée à cet utilistateur' });
        }

        res.status(200).json(data)
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
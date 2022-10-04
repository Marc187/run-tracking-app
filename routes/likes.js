const express = require('express');
const request = require('../database/connexion.js');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.get('/like', async (req, res) => {
    try {
        const id_course = req.body.id_course || null
        const id_utilisateur = req.body.id_utilisateur || null

        console.log(id_course, id_utilisateur)
        const data = await request.getLike(id_course, id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course ou utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.post('/like', async (req, res) => {
    try {
        const id_course = req.body.id_course || null
        const id_utilisateur = req.body.id_utilisateur || null

        console.log(id_course, id_utilisateur)
        const data = await request.addLike(id_course, id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course ou utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.delete('/like', async (req, res) => {
    try {
        const id_course = req.body.id_course || null
        const id_utilisateur = req.body.id_utilisateur || null

        console.log(id_course, id_utilisateur)
        const data = await request.deleteLike(id_course, id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course ou utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
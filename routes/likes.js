const express = require('express');
const request = require('../database/likes.js');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.get('/:id_course/:id_utilisateur', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const id_utilisateur = req.params.id_utilisateur

        console.log(id_course, id_utilisateur)
        const data = await request.getLike(id_course, id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course ou utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ liked: true })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.post('/:id_course/:id_utilisateur', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const id_utilisateur = req.params.id_utilisateur

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

router.delete('/:id_course/:id_utilisateur', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const id_utilisateur = req.params.id_utilisateur

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
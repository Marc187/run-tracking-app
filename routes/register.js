const express = require('express');
const request = require('../database/connexion.js');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.post('/', async (req, res) => {
    try {
        const nom_utilisateur = req.body.nom_utilisateur;
        const nom = req.body.nom;
        const email = req.body.email;
        const password = bcrypt.hashSync(req.body.password);
        
        const data = await request.createUser(nom_utilisateur, nom, email, password)
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})


module.exports = router;
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

        request.createUser([nom_utilisateur, nom, email, password], (err) => {
            if (err) {
                console.log(err);
                return res.status(500).send("Server error!!!!");

            }
            request.findUserByEmail(email, (err, user) => {
                if (err) return res.status(500).send('Server error');
                res.status(200).send({
                    "user": user
                });
            });
        });
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
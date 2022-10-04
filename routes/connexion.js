const express = require('express');
const request = require('../database/connexion.js');
const router = express.Router();
const bcrypt = require('bcryptjs');

router.post('/register', async (req, res) => {
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

router.post('/login', (req, res) => {
    const email = req.body.email;
    const password = req.body.password;
    request.findUserByEmail(email, (err, user) => {
        if (err) return res.status(500).send('Server error!');
        if (!user) return res.status(404).send('User not found!');
        const result = bcrypt.compareSync(password, user.password);
        if (!result) return res.status(401).send('Password not valid!');

        const expiresIn = 24 * 60 * 60;
        const accessToken = jwt.sign({ id: user.id }, process.env.TOKEN_KEY, {
            expiresIn: expiresIn
        });
        res.status(200).send({"access_token": accessToken, "expires_in": expiresIn });
    });
});

module.exports = router;
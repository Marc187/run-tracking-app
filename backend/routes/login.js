const express = require('express');
const request = require('../database/connexion.js');
const router = express.Router();
const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs');

router.post('/', async (req, res) => {
    try {
        const email = req.body.email;
        const password = req.body.password;
        
        const data = await request.findUserByEmail(email, password)

        // Verifie si l'utilisateur existe dans la BD
        if (!data) {
            return res.status(404).json({ message: "Aucun utilisateur avec cet ID trouvé." })    
        }

        if(bcrypt.compareSync(password, data.password)){
        const expiresIn = 24 * 60 * 60;
        const accessToken = jwt.sign({ id: data.id },  process.env.TOKEN_KEY, {
            expiresIn: expiresIn
        });
        
        res.status(200).send({ "token": accessToken,  "id": data.id});
    }
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
const express = require('express');
const router = express.Router();
const request = require('../database/utilisateurs')

router.get('/:name', async (req, res) => {
    try {
        // Accede a la BD et retourne les utilisateurs selon le nom
        const name = req.params.name
        const users = await request.getUserByName(name)

        res.status(200).json(users)
    } catch (error) {
        console.log(error)
        res.status(500).json(error.message);
    }
})


module.exports = router;
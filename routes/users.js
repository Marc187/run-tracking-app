const express = require('express');
const router = express.Router();
const auth = require('../middleware/authentification')
const userVerification = require('../middleware/user_verification')
const usersRequest = require('../database/utilisateurs')
const abonnementsRequest = require('../database/abonnements')

router.get('/:id_user/:name', auth, async (req, res) => {
    try {
        // Accede a la BD et retourne les utilisateurs selon le nom
        const name = req.params.name
        const id_user = req.params.id_user
        const users = await usersRequest.getUserByName(name)

        // Verifie si le user a deja ete abonne
        for (i = 0; i < users.length; i++) {
            const subscribed = await abonnementsRequest.getSubscription(users[i].id, id_user)
            users[i].subscribed = subscribed.length > 0 ? true : false
        }

        res.status(200).json(users)
    } catch (error) {
        console.log(error)
        res.status(500).json(error.message);
    }
})


module.exports = router;

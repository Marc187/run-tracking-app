const express = require('express');
const request = require('../database/abonnements.js');
const router = express.Router();
const auth = require('../middleware/authentification')
const userVerification = require('../middleware/user_verification')


// Route to add a subscription to a user
router.post('/:id_utilisateur_suivi', auth, async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.user.id

        const data = await request.getSubscription(id_utilisateur_suivi, id_utilisateur_suivant)

        if (data.length == 0) {
            await request.addSubscribe(id_utilisateur_suivi, id_utilisateur_suivant)
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

// Route to delete a subscription from a user
router.delete('/:id_utilisateur_suivi', auth, async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.user.id

        // Supprime l'abonnement de l'utilisateur
        const data = await request.deleteSubscribe(id_utilisateur_suivi, id_utilisateur_suivant)
        
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
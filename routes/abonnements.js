const express = require('express');
const request = require('../database/abonnements.js');
const router = express.Router();
const auth = require('../middleware/authentification')

// Route to get the number of subscriptions for a user
router.get('/:id_utilisateur', auth, async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur
        
        // Authentification de l'Utilisateur
        if (req.user.id != id_utilisateur) return res.status(401).json({ message: "Unauthorized."})

        const data = await request.getSubscriptions(id_utilisateur)
        
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json(["nom_utilisateur1"])
    } catch (error) {
        res.status(500).json(error.message);
    }
})

// Route to add a subscription to a user
router.post('/:id_utilisateur_suivi/:id_utilisateur_suivant', auth, async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.params.id_utilisateur_suivant

        // Authentification de l'Utilisateur
        if (req.user.id != id_utilisateur_suivant) return res.status(401).json({ message: "Unauthorized."})

        const data = await request.addSubscribe(id_utilisateur_suivi, id_utilisateur)
        
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

// Route to delete a subscription from a user
router.delete('/:id_utilisateur_suivi/:id_utilisateur_suivant', auth, async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.params.id_utilisateur_suivant

        // Authentification de l'Utilisateur
        if (req.user.id != id_utilisateur_suivant) return res.status(401).json({ message: "Unauthorized."})

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
const express = require('express');
const request = require('../database/abonnements.js');
const router = express.Router();

// Route to get the number of subscriptions for a user
router.get('/:id_utilisateur', async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur

        console.log(id_utilisateur)
        const data = await request.getSubscriptions(id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json(["nom_utilisateur1"])
    } catch (error) {
        res.status(500).json(error.message);
    }
})

// Route to add a subscription to a user
router.post('/:id_utilisateur_suivi/:id_utilisateur_suivant', async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.params.id_utilisateur_suivant

        console.log(id_utilisateur_suivi, id_utilisateur_suivant)
        const data = await request.addSubscribe(id_course, id_utilisateur)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

// Route to delete a subscription from a user
router.delete('/:id_utilisateur_suivi/:id_utilisateur_suivant', async (req, res) => {
    try {
        const id_utilisateur_suivi = req.params.id_utilisateur_suivi
        const id_utilisateur_suivant = req.params.id_utilisateur_suivant

        console.log(id_utilisateur_suivi, id_utilisateur_suivant)
        const data = await request.deleteSubscribe(id_utilisateur_suivi, id_utilisateur_suivant)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucun utilisateur avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})
module.exports = router;
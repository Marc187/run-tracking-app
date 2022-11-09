const express = require('express');
const router = express.Router();
const auth = require('../middleware/authentification')
const userVerification = require('../middleware/user_verification')
const request = require('../database/abonnements')
const activityRequest = require('../database/activity')
const likesRequest = require('../database/likes')

router.get('/:id_utilisateur', auth, userVerification, async (req, res) => {
    try {
        // Accede a la BD et retourne une liste contenant tous les abonnements de l'utilisateur
        const id_utilisateur = req.params.id_utilisateur
        const data_abonnements = await request.getSubscriptions(id_utilisateur)
        const liste_abonnements = data_abonnements.map(x => x.id_utilisateur_suivi)
        liste_abonnements.push(id_utilisateur)

        // Prends toutes les courses des utilisateurs triées par ordre chronologiques
        const activity = await activityRequest.getActivity(liste_abonnements)
        
        // Verifie si la course a deja ete aime
        for (i = 0; i < activity.length; i++) {
            const liked = await likesRequest.getLike(activity[i].id, id_utilisateur)
            activity[i].liked = liked.length > 0 ? true : false
        }

        res.status(200).json(activity)
    } catch (error) {
        console.log(error)
        res.status(500).json(error.message);
    }
})


module.exports = router;
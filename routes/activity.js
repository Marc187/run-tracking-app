const express = require('express');
const router = express.Router();
const auth = require('../middleware/authentification')
const request = require('../database/abonnements')
const activityRequest = require('../database/activity')
const likesRequest = require('../database/likes')

router.get('/', auth, async (req, res) => {
    try {
        // Accede a la BD et retourne une liste contenant tous les abonnements de l'utilisateur
        const id_utilisateur = req.user.id
        const data_abonnements = await request.getSubscriptions(id_utilisateur)
        const liste_abonnements = data_abonnements.map(x => x.id_utilisateur_suivi)
        liste_abonnements.push(id_utilisateur)

        // Prends toutes les courses des utilisateurs triées par ordre chronologiques
        const activity = await activityRequest.getActivity(liste_abonnements)

        for (i = 0; i < activity.length; i++) {
            // Verifie si la course a deja ete aime
            const liked = await likesRequest.getLike(activity[i].id, id_utilisateur)
            course_is_liked = liked.length > 0 ? true : false
            activity[i].liked = course_is_liked

            // Ajoute le nombre de like au data
            const likes = await likesRequest.getLikes(activity[i].id)
            activity[i].likes =  course_is_liked ? likes.length - 1 : likes.length
        }

        res.status(200).json(activity)
    } catch (error) {
        console.log(error)
        res.status(500).json(error.message);
    }
})


module.exports = router;
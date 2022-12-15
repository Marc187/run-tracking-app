const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getActivity(liste_abonnements) {
    return knex('courses')
        .select('courses.id', 'id_utilisateur', 'nom', "avgSpeedInKMH", "distanceInMeters", "timeInMillis", "caloriesBurned", "timeStamps")
        .join('utilisateurs', 'courses.id_utilisateur', 'utilisateurs.id')
        .whereIn('courses.id_utilisateur', liste_abonnements)
        .orderBy('timeStamps', 'desc')
}

module.exports = {
    getActivity,
};
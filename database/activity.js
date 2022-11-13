const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getActivity(liste_abonnements) {
    return knex('courses')
        .select('courses.id', 'id_utilisateur', knex.raw("CONVERT(VARCHAR(1000), img, 1) as img") , "avgSpeedInKMH", "distanceInMeters", "timeInMillis", "caloriesBurned", "timeStamps")
        .join('utilisateurs', 'courses.id_utilisateur', 'utilisateurs.id')
        .whereIn('courses.id_utilisateur', liste_abonnements)
        .orderBy('timeStamps')
}

module.exports = {
    getActivity,
};
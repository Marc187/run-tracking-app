const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getActivity(liste_abonnements) {
    return knex('courses')
        .select('courses.id', 'id_utilisateur', 'distance', 'duree', 'date', 'nom')
        .join('utilisateurs', 'courses.id_utilisateur', 'utilisateurs.id')
        .whereIn('id_utilisateur', liste_abonnements)
        .orderBy('date')
}

module.exports = {
    getActivity,
};
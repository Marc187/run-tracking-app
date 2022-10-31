const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getActivity(liste_abonnements) {
    return knex('courses')
        .whereIn('id_utilisateur', liste_abonnements)
        .orderBy('date')
}

module.exports = {
    getActivity,
};
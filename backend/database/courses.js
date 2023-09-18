const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getCourses(id_utilisateur) {
    return knex('courses')
        .where('id_utilisateur', id_utilisateur)
}


module.exports = {
    getCourses,
};
const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getUserById(id) {
    return knex('utilisateurs')
        .where('id', id)
}

module.exports = {
    getUserById,
};
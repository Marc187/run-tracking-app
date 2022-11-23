const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getUserById(id) {
    return knex('utilisateurs')
        .where('id', id)
}

function getUserByName(name) {
    return knex('utilisateurs')
        .where('nom', 'like', `%${name}%`)
}

module.exports = {
    getUserById,
    getUserByName
};
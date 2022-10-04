const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function createUser(nom_utilisateur, nom, email, password) {
    return knex('utilisateurs')
        .returning('id')
        .insert({ nom_utilisateur, nom, email, password })
        
}

function findUserByEmail (email) {
    return knex('utilisateurs')
        .where('email', email)
}

module.exports = {
    createUser,
    findUserByEmail
};
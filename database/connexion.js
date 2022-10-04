const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function createUser(nom_utilisateur, nom, email, password) {
    return knex('courses')
        .returning('id')
        .insert({ nom_utilisateur, nom, email, password })
        
}

function findUserByEmail (email) {
    return knex('Utilisateurs')
        .where('email', email)
}

module.exports = {
    createUser,
    findUserByEmail
};
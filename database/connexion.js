const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function createUser(nom_utilisateur, nom, email, password) {
    return knex('utilisateurs')
        .returning('id')
        .insert({ nom_utilisateur, nom, email, password }) 
}

function changePassword(id, password) {
    return knex('utilisateurs')
        .where('id', id)
        .update('password', password)
}

function findUserByEmail (email) {
    return knex('utilisateurs')
        .where('email', email)
        .first()
}

function findUserById (id) {
    return knex('utilisateurs')
        .where('id', id)
        .first()
}


module.exports = {
    createUser,
    changePassword,
    findUserByEmail,
    findUserById
};
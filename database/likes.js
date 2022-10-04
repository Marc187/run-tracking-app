const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getLike(id_course, id_utilisateur) {
    return knex('likes')
        .where('id', id)
}

function addLike(id_course, id_utilisateur) {
    return knex('likes')
        .insert({ id_course, id_utilisateur })
        
}

function deleteLike(id_course, id_utilisateur) {
    return knex('likes')
        .where('id_course', id_course, 'id_utilisateur', id_utilisateur)
        .del()
}

module.exports = {
    getLike,
    addLike,
    deleteLike
};
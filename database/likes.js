const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


// Function to get the number of likes for a course
function getLike(id_course, id_utilisateur) {
    return knex('likes')
        .where('id_course ', id_course)
        .andWhere('id_utilisateur', id_utilisateur)
}


// Function to add a like
function addLike(id_course, id_utilisateur) {
    return knex('likes')
        .insert({ id_course, id_utilisateur })
        
}

// Function to delete a like
function deleteLike(id_course, id_utilisateur) {
    return knex('likes')
        .where('id_course ', id_course)
        .andWhere('id_utilisateur', id_utilisateur)
        .del()
}

module.exports = {
    getLike,
    addLike,
    deleteLike
};
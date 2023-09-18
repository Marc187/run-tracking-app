const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


// Function qui retourne si un utilisateur a aime une course
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

// Function to return a list of all the likes of a course
function getLikes(id_course) {
    return knex('courses')
        .where('id', id_course)
        .join('likes', 'likes.id_course', 'courses.id')
        
}



module.exports = {
    getLike,
    addLike,
    getLikes,
    deleteLike
};
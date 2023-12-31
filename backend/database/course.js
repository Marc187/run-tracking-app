const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


function getCourse(id) {
    return knex('courses')
        .where('id', id)
}

function addCourse(id_utilisateur, timeStamps, avgSpeedInKMH, distanceInMeters, timeInMillis, caloriesBurned) {
    return knex('courses')
        .returning('id')
        .insert({ id_utilisateur, timeStamps, avgSpeedInKMH, distanceInMeters, timeInMillis, caloriesBurned })
        
}

function deleteCourse(id) {
    return knex('courses')
        .where('id', id)
        .del()
}

module.exports = {
    getCourse,
    addCourse,
    deleteCourse
};
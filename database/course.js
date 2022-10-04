const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


function getCourse(id) {
    return knex('courses')
        .where('id', id)
}

function deleteCourse(id) {
    return knex('courses')
        .where('id', id)
        .del()
}

module.exports = {
    getCourse,
    deleteCourse
};
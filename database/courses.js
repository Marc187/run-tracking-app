const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


function getCourse(id) {
    return knex('Courses')
        .where('id', id)
}

module.exports = {
    getCourse,
};
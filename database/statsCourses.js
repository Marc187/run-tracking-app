const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getStatsCourses(id_utilisateur) {
    return knex.raw(`select id_utilisateur, sum(distance) as totdist, LEFT(date,7) as mois from courses where id_utilisateur = ${id_utilisateur} group by id_utilisateur, LEFT(date,7)`)
}


module.exports = {
    getStatsCourses,
};
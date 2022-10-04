const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function getSubscriptions(id_utilisateur) {
    return knex('abonnements')
        .where('id', id_utilisateur)
}

function addSubscribe(id_utilisateur_suivi, id_utilisateur_suivant) {
    return knex('abonnements')
        .insert({ id_utilisateur_suivi, id_utilisateur_suivant })
        
}

function deleteSubscribe(id_utilisateur_suivi, id_utilisateur_suivant) {
    return knex('abonnements')
        .where('id_utilisateur_suivi', id_utilisateur_suivi) 
        .andWhere('id_utilisateur_suivant', id_utilisateur_suivant)
        .del()
}

module.exports = {
    getSubscriptions,
    addSubscribe,
    deleteSubscribe
};
const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);


function getSubscriptions(id_utilisateur) {
    return knex('abonnements')
        .where('id_utilisateur_suivant', id_utilisateur)
}

function getSubscription(id_utilisateur_suivi, id_utilisateur_suivant) {
    return knex('abonnements')
        .where('id_utilisateur_suivi', id_utilisateur_suivi)
        .andWhere('id_utilisateur_suivant', id_utilisateur_suivant)
}

// Function to add a subscription to a user
function addSubscribe(id_utilisateur_suivi, id_utilisateur_suivant) {
    return knex('abonnements')
        .insert({ id_utilisateur_suivi, id_utilisateur_suivant })
        
}

// Function to delete a subscription from a user
function deleteSubscribe(id_utilisateur_suivi, id_utilisateur_suivant) {
    return knex('abonnements')
        .where('id_utilisateur_suivi', id_utilisateur_suivi) 
        .andWhere('id_utilisateur_suivant', id_utilisateur_suivant)
        .del()
}


module.exports = {
    getSubscriptions,
    getSubscription,
    addSubscribe,
    deleteSubscribe
};
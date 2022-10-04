const knexModule = require('knex');
const infoConnexion = require('../constants');
const knex = knexModule(infoConnexion);

function createUser (user, cb) {
    return knex('INSERT INTO Utilisateurs (nom_utilisateur, nom, email, password) VALUES (?,?,?)', user, (err) => {
        cb(err)
    });
}

function findUserByEmail (email, cb) {
    return database.get(`SELECT * FROM Utilisateurs WHERE email = ?`, [email], (err, row) => {
        cb(err, row)
    });
}

module.exports = {
    createUser,
    findUserByEmail
};
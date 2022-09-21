const infoConnexion = {
    client: 'mssql',
    connection: {
        host: 'sv55.cmaisonneuve.qc.ca',
        user: '5D1Equipe04',
        password: 'ExBVLhPiW0VNzvNYhxVgr8HdRY2vaF',
        database: '5D1Equipe04',
        options: {
            enableArithAbort: false,
        },
    },
    useNullAsDefault: true,
    pool: { min: 0, max: 7 },
};

// eslint-disable-next-line import/prefer-default-export
module.exports = infoConnexion;

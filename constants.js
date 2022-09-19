const dbLoginInfo = {
    client: 'mssql',
    connection: {
        host: 'sv55.cmaisonneuve.qc.ca',
        user: '4D1Equipe08',
        password: 'njn686',
        database: '4D1Equipe08',
        options: {
            enableArithAbort: false,
        },
    },
    useNullAsDefault: true,
    pool: { min: 0, max: 7 },
};

// eslint-disable-next-line import/prefer-default-export
module.exports = dbLoginInfo;

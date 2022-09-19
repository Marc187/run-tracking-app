const express = require('express');

const app = express();
const cors = require('cors');

const personnes = require('./routes/personnes');
const conditions = require('./routes/conditions');
const connexion = require('./routes/connexion');
const ippes = require('./routes/ippes');

const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.use('/personnes', personnes);
app.use('/conditions', conditions);
app.use('/connexion', connexion);
app.use('/ippes', ippes);

app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

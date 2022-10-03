const express = require('express');
const app = express();
const cors = require('cors');
const swaggerUi = require('swagger-ui-express')
const swaggerDocument = require('./documentation_api.yaml')
const PORT = process.env.PORT || 3000;

const courses = require('./routes/courses');


app.use(cors());
app.use(express.json());
app.use('/', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

app.use('/courses', courses);

app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

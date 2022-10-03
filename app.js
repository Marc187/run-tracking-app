const express = require('express');
const app = express();
const cors = require('cors');
const PORT = process.env.PORT || 3000;

// Packages nécessaires pour la documentation Swagger
const YAML = require('yamljs')
const swaggerUi = require('swagger-ui-express')
const swaggerDocument = YAML.load('./documentation_api.yaml')


// Importation des fichiers de routes
const courses = require('./routes/courses');


// Middlewares nécessaires à l'application
app.use(cors());
app.use(express.json());
app.use('/', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

// Routes
app.use('/courses', courses);


app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

const express = require('express');
const app = express();
const cors = require('cors');
const PORT = process.env.PORT || 3000;

// Packages nécessaires pour la documentation Swagger
const YAML = require('yamljs')
const swaggerUi = require('swagger-ui-express')
const swaggerDocument = YAML.load('./documentation_api.yaml')


// Importation des fichiers de routes
const course = require('./routes/course.js');
const courses = require('./routes/courses.js');

// Middlewares nécessaires à l'application
app.use(cors());
app.use(express.json());

// Routes
app.use('/course', course);
app.use('/courses', courses);

// Route pour la documentation
app.use('/', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

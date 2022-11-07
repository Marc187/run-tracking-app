const express = require('express');
const app = express();
const cors = require('cors');
require('dotenv').config()
const PORT = process.env.PORT || 3000;

// Packages nécessaires pour la documentation Swagger
const YAML = require('yamljs')
const swaggerUi = require('swagger-ui-express')
const swaggerDocument = YAML.load('./documentation_api.yaml')

// Importation des fichiers de routes
const course = require('./routes/course.js');
const courses = require('./routes/courses.js');
const statsCourses = require('./routes/statsCourses.js');
const login = require('./routes/login');
const register = require('./routes/register');
const like = require('./routes/like');
const abonnements = require('./routes/abonnements');
const activity = require('./routes/activity')

// Middlewares nécessaires à l'application
app.use(cors());
app.use(express.json());

// Routes
app.use('/course', course);
app.use('/courses', courses);
app.use('/statsCourses', statsCourses);
app.use('/like', like);
app.use('/login', login);
app.use('/register', register);
app.use('/subscribtions', abonnements);
app.use('/subscribe', abonnements);
app.use('/activity', activity)

// Route pour la documentation
app.use('/', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

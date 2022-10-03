const express = require('express');
const app = express();
const cors = require('cors');
const PORT = process.env.PORT || 3000;

const courses = require('./routes/courses');


app.use(cors());
app.use(express.json());

app.use('/courses', courses);
app.get('/', (req, res) => {
    res.send("<h1>Hello World! test heroku </h1>")
})

app.listen(PORT, () => {
    console.log(`Mon application roule sur http://localhost:${PORT}`);
});

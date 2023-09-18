const express = require('express');
const request = require('../database/course.js');
const requestUser = require('../database/users.js');
const router = express.Router();
const path = require('path');
const auth = require('../middleware/authentification')

// Multer
const multer = require("multer");

const storage = multer.diskStorage({
    destination: `${process.cwd()}/uploads/images_courses/`,
    filename: (req, file, cb) => {
        console.log(`${req.params.id_course}${path.extname(file.originalname)}`)
        return cb(null, `${req.params.id_course}.png`)
    }
});

const upload = multer ({
    storage: storage
});


router.post('/', auth, async (req, res) => {
    try {
        const id_utilisateur = req.user.id
        const timeStamps = req.body.timeStamps
        const avgSpeedInKMH = req.body.avgSpeedInKMH
        const distanceInMeters = req.body.distanceInMeters
        const timeInMillis = req.body.timeInMillis
        const caloriesBurned = req.body.caloriesBurned

        
        // Verifie si l'Utilisateur est bien dans la base de donnees
        const user = await requestUser.getUserById(id_utilisateur)
        if (!user) {
            return res.status(404).json({ message: "Aucun utilisateur avec cet ID trouvé." })    
        }

        const data = await request.addCourse(id_utilisateur, timeStamps, avgSpeedInKMH, distanceInMeters, timeInMillis, caloriesBurned)

        res.status(200).json({ message: "success", id_course: data[0].id })
    } catch (error) {
        res.status(500).json(error.message);
    }
})


router.get("/image/:id_course", async (req, res) => {
    try {
        image_path = path.join(__dirname, "..", "uploads", "images_courses")
        console.log(image_path)
        res.sendFile(path.join(image_path, req.params.id_course + ".png"));
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.post("/image/:id_course", upload.single("image_course"), async (req, res) => {
    try {
        res.status(200).json({ message: "Image de la course ajoutée avec succès."})
    } catch (error) {
        res.status(500).json(error.message);
    }
})



module.exports = router;
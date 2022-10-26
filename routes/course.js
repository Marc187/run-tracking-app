const express = require('express');
const request = require('../database/course.js');
const requestUser = require('../database/utilisateurs.js');
const router = express.Router();
const auth = require('../middleware/authentification')

router.get('/:id_course', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const data = await request.getCourse(id_course)

        // Verifie si la course appartient a l'utilisateur
        if (req.user.id != data[0].id_utilisateur) return res.status(401).json({ message: "Unauthorized."})


        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course avec cet id trouvée' });
        }
        
        res.status(200).json(data[0])
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.post('/', auth, async (req, res) => {
    try {
        const id_utilisateur = req.body.id_utilisateur
        const distance = req.body.distance || null
        const duree = req.body.duree || null
        const date = new Date().toISOString()

        // Authentification de l'Utilisateur
        if (req.user.id != id_utilisateur) return res.status(401).json({ message: "Unauthorized."})
        
        // Verifie si l'Utilisateur est bien dans la base de donnees
        const user = await requestUser.getUserById(id_utilisateur)
        if (!user) {
            return res.status(404).json({ message: "Aucun utilisateur avec cet ID trouvé." })    
        }

        const data = await request.addCourse(date, distance, duree, id_utilisateur)
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.delete('/:id_course', auth, async (req, res) => {
    try {
        const id_course = req.params.id_course
        
        // Verifie si la course appartient a l'utilisateur
        const course = await request.getCourse(id_course)
        if (req.user.id != course[0].id_utilisateur) return res.status(401).json({ message: "Unauthorized."})

        //Suppression de la course
        const data = await request.deleteCourse(id_course)

        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
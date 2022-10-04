const express = require('express');
const request = require('../database/course.js');
const router = express.Router();

router.get('/:id_course', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const data = await request.getCourse(id_course)

        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course avec cet id trouvée' });
        }
        
        res.status(200).json(data[0])
    } catch (error) {
        res.status(500).json(error.message);
    }
})

router.delete('/:id_course', async (req, res) => {
    try {
        const id_course = req.params.id_course
        const data = await request.deleteCourse(id_course)
        
        console.log(data)
        if (data.length === 0) {
            return res.status(404).json({ message: 'Aucune course avec cet id trouvée' });
        }
        
        res.status(200).json({ message: "success" })
    } catch (error) {
        res.status(500).json(error.message);
    }
})

module.exports = router;
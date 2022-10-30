const express = require('express');
const request = require('../database/course.js');
const router = express.Router();

router.get('/:id_utilisateur', async (req, res) => {
    try {
        const id_utilisateur = req.params.id_utilisateur

        
        const data = await request.getCourse(id_course)
        
        res.status(200).json(data[0])
    } catch (error) {
        res.status(500).json(error.message);
    }
})


module.exports = router;
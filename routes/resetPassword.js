const express = require('express');
const request = require('../database/connexion.js');
const router = express.Router();
const bcrypt = require('bcryptjs');
const auth = require('../middleware/authentification')

router.put('/', auth ,async (req, res) => {
    try {
        const id = req.body.id;
        const oldPassword = req.body.oldpassword;
        const newPassword = bcrypt.hashSync(req.body.newpassword);

        const data = await request.findUserById(id)

        if(bcrypt.compareSync(oldPassword, data.password)){
            try{
                const data = await request.changePassword(id, newPassword)
                res.status(200).json({ message: "success" })
            } catch (error){
                res.status(500).json(error.message);
            }
            
        }else{
            return res.status(404).json({ message: 'Mot de passe incorect' });
        }

    } catch (error) {
        res.status(500).json(error.message);
    }
})


module.exports = router;
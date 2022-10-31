

const userVerification = (req, res, next) => {
    const id_utilisateur = req.params.id_utilisateur || req.query.id_utilisateur || req.body.id_utilisateur
    
    if (req.user.id != id_utilisateur) {
        return res.status(401).json({ message: "Unauthorized."})
    }

    next()
}

module.exports = userVerification
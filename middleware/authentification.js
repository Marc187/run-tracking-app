const jwt = require("jsonwebtoken")

const verifyToken = (req, res, next) => {
    const token = req.headers["authorization"].split(" ")[1]

    if (!token) return res.status(403).json({ message: "Un jeton est requis pour l'authentification" })

    try {
        const decoded = jwt.verify(token, process.env.TOKEN_KEY)
        //console.log(decoded)
        req.user = decoded
    } catch (err) {
        return res.status(401).json({ message: "Jeton Invalide" })
    }
    
    next()
}

module.exports = verifyToken
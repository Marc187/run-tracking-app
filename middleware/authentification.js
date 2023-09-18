const jwt = require("jsonwebtoken")

const verifyToken = (req, res, next) => {
    let token = req.headers["authorization"]
    if (token) token = token.split(" ")[1]

    if (!token) return res.status(403).json({ message: "Un jeton est requis pour l'authentification" })

    try {
        const decoded = jwt.verify(token, process.env.TOKEN_KEY);
        // tu place, dans req.user le id de l<utilisateur du jeton
        req.user = decoded
    } catch (err) {
        return res.status(401).json({ message: "Jeton Invalide" })
    }
    
    next()
}

module.exports = verifyToken
import { Router } from "express";

import UserController from "../controller/UserController.js";

const router = new Router();

// Será mais ou menos assim o modelo: /api/user/email/lorransantos@gmail.com
// Estando tudo pronto, colar o seguinte no navegador: http://localhost:8080/api/user/email/testeuser@gmail.com
router.get("/api/user/email/:email", UserController.findByEmail);
router.post("/api/user/auth", UserController.getAccessToken);

export default router;

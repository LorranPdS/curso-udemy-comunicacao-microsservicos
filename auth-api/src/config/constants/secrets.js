const env = process.env;

/**
 * Veja abaixo entre aspas que criamos uma "secret" para a nossa API auth-api.
 * Basicamente o que iremos fazer é dar um nome "secret" para a nossa API em base64 e coloca entre aspas
 * Para criar esse nome em base64, iremos fazer o seguinte:
 * 1. acessar o site https://www.base64encode.org/
 * 2. no campo de texto, iremos digitar "auth-api-secret-dev-123456" e clicar no botão "Encode"
 * 3. o texto gerado foi o texto "YXV0aC1hcGktc2VjcmV0LWRldi0xMjM0NTY=", então esse será o secret da nossa aplicação que está colado abaixo
 * 4. esse secret é necessário para conseguirmos conectar e gerar o nosso token de acesso
 */
export const API_SECRET = env.API_SECRET ? env.API_SECRET : "YXV0aC1hcGktc2VjcmV0LWRldi0xMjM0NTY=";
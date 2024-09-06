/**
 * 
 * 1. primeira coisa a fazer é configurar o acesso ao banco de dados, e para fazer isso, 
 * iremos configurar a biblioteca do Sequelize, que irá nos permitir acessar o banco de dados
 * 
 * 2. segundo, vamos precisar da lib do Sequelize no nosso package.json, e por isso ela foi
 * importada lá. Também vamos precisar ter a lib do PostgreSQL porque vai ser o banco de 
 * dados que iremos acessar, então vamos precisar do conector dele
 * 
 * 3. para pegarmos a lib do Sequelize, dê o seguinte comando estando com o terminal 
 * aberto no diretório raiz do projeto: "yarn add sequelize"
 * 
 * 4. tendo instalado todos os módulos do sequelize para nós, agora vamos pegar a lib do 
 * postgres para nós usando o comando "yarn add pg" para usarmos o conector dele ao banco de dados
 * 
 * 5. instalações terminadas, podemos começar a importar o objeto do sequelize para esse nosso 
 * arquivo e seguir nas configurações necessárias
 * 
 */

import Sequelize from "sequelize";

// 6. esse "1y5h8j" é uma senha aleatória
const sequelize = new Sequelize("auth-db", "postgres", "1y5h8j", {
    host: "localhost",
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
        syncOnAssociation: true,
        timestamp: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    },
});

sequelize
.authenticate()
.then(() => {
    console.log('Connection has been stabilished:');
})
.catch((err) => {
    console.error('Unable to connect to the database.');
    console.error(err.message);
});

export default sequelize;

/**
 * 7. finalizadas as configurações, lembre de garantir que o container com o banco de dados "auth-db" 
 * está rodando para fazermos a conexão
 * 8. agora execute o comando "yarn startDev", sendo esse o comando definodo por nós no 
 * arquivo app.js para rodarmos o sistema localmente
 * 
 * 9. se apareceu a seguinte mensagem, significa que está rodando da forma como deveria

yarn run v1.22.22
$ nodemon app.js
[nodemon] 3.1.4
[nodemon] to restart at any time, enter `rs`
[nodemon] watching path(s): *.*
[nodemon] watching extensions: js,mjs,cjs,json
[nodemon] starting `node app.js`
Server started sucessfully at port 8080

    OBSERVAÇÃO IMPORTANTE: de agora em diante, sempre lembrar de fazer o seguinte durante as 
    aulas para seguir no projeto:
    - garantir que o container do auth-db esteja rodando no Docker Desktop;
    - executar o comando "yarn startDev" para subir a aplicação "auth-api"
*/

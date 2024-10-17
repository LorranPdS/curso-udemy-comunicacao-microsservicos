-- Quando você adiciona um arquivo de SQL dentro de um diretório resources (como fizemos aqui), o Spring identifica esse arquivo e, quando formos subir essa aplicação, ele irá rodar esse script de import também
-- Foi necessário trocar os id para 1001, 1002 para que nos creates não dessem chave duplicada. Futuramente, colocar isso como UUID

INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (1001, 'Comic Books');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (1002, 'Movies');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (1003, 'Books');

INSERT INTO SUPPLIER (ID, NAME) VALUES (1001, 'Panini Comics');
INSERT INTO SUPPLIER (ID, NAME) VALUES (1002, 'Amazon');

-- O CURRENT_TIMESTAMP salva a data e a hora atual de quando foi executado o insert
INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1001, 'Crise nas Infinitas Terras', 1001, 1001, 10, CURRENT_TIMESTAMP);
INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1002, 'Interestelar', 1002, 1002, 5, CURRENT_TIMESTAMP);
INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1003, 'Harry Potter e a Pedra Filosofal', 1002, 1003, 3, CURRENT_TIMESTAMP);

-- Esse script somente rodou quando eu fui lá no arquivo 'application.yaml' e configurei de 'update' para 'create-drop'

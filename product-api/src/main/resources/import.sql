-- Quando você adiciona um arquivo de SQL dentro de um diretório resources (como fizemos aqui), o Spring identifica esse arquivo e, quando formos subir essa aplicação, ele irá rodar esse script de import também

INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (1, 'Comic Books');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (2, 'Movies');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (3, 'Books');

INSERT INTO SUPPLIER (ID, NAME) VALUES (1, 'Panini Comics');
INSERT INTO SUPPLIER (ID, NAME) VALUES (2, 'Amazon');

INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE) VALUES (1, 'Crise nas Infinitas Terras', 1, 1, 10);
INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE) VALUES (2, 'Interestelar', 2, 2, 5);
INSERT INTO PRODUCT (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE) VALUES (3, 'Harry Potter e a Pedra Filosofal', 2, 3, 3);

-- Esse script somente rodou quando eu fui lá no arquivo 'application.yaml' e configurei de 'update' para 'create-drop'
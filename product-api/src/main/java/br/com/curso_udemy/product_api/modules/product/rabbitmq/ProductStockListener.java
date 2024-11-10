package br.com.curso_udemy.product_api.modules.product.rabbitmq;

import br.com.curso_udemy.product_api.modules.product.dto.ProductStockDto;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    Essa classe foi criada para que possamos ouvir a fila de atualização de estoque e por ele
    irei receber um objeto do tipo "ProductStockDto"
 */
@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    /*
        1. o caminho que está no parâmetro "queues" da annotation @RabbitListener é o
        caminho do arquivo "application.yml" que criamos, que seria o caminho da nossa fila

        2. quando nós vamos publicar, nós não publicamos na fila (queue) mas publicamos direto
        na routing key para aquele topic, e daí ele redireciona para a fila. Nesse caso abaixo,
        para ouvirmos, nós configuramos para ouvirmos direto a fila porque nós estamos apenas
        ouvindo a fila. Então:
            - vai ter uma pessoa num topic publicando com uma chave (key)
            - o rabbit internamente vai redirecionar para uma fila
            - terá alguém ouvindo essa fila
            - e quem irá publicar nessa fila vai ser a outra aplicação que nós ainda iremos
                desenvolver (que será a aplicação 'sales-api')

        3. nessa aula 42, foi criado um payload para simular uma publicação do 'sales-api'
            indo lá no rabbit para publicar a mensagem e vimos caindo por aqui e passando
            a mensagem pelo nosso log aqui da aplicação, e é muito interessante ver isso

     */
    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDto product) throws JsonProcessingException {
        log.info("Recebendo mensagem: {}", new ObjectMapper().writeValueAsString(product));
        productService.updateProductStock(product);
    }
}

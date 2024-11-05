package br.com.curso_udemy.product_api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Como já percebemos pela annotation abaixo, essa aqui será uma classe de configuração gerenciada pelo Spring
@Configuration
public class RabbitConfig {

    /**
     * Abaixo eu irei colocar os mesmos campos que eu configurei lá no arquivo 'application.yml',
     * inclusive direcionando para os caminhos através dos pontos que irei colocar dentro da
     * annotation @Value
     */

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.product-stock}")
    private String productStockKey;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    @Value("${app-config.rabbit.queue.product-stock}")
    private String productStockMq;

    @Value("${app-config.rabbit.queue.sales-confirmation}")
    private String salesConfirmationMq;

    // Abaixo, criaremos um Bean para definir o nosso TopicExchange

    @Bean
    public TopicExchange productTopicExchange(){
        return new TopicExchange(productTopicExchange);
    }

    // Criaremos outro Bean para definir as nossas queues e os bindings das nossas queues

    @Bean
    public Queue productStockMq(){
        /* CRIAÇÃO DA QUEUE
        - no primeiro parâmetro: ele criará a fila com o nome que foi definido ali
        - no segundo parâmetro: com o true nesse segundo parâmetro abaixo relativo ao
            "durable" (entre na classe 'Queue' para entender), fazemos com que nosso
            serviço sempre inicie a aplicação com a fila (queue) criada
         */
        return new Queue(productStockMq, true);
    }

    // Segunda QUEUE criada abaixo
    @Bean
    public Queue salesConfirmationMq(){
        return new Queue(salesConfirmationMq, true);
    }

    /*
        Agora temos que informar ao rabbit que essa queue estará vinculada a esse exchange
        através de uma chave de roteamento (routing-key)
     */
    @Bean
    public Binding productStockMqBinding(TopicExchange topicExchange){
        return BindingBuilder
                .bind(productStockMq()) // fila que iremos dar o binding (método que criamos do tipo 'Queue')
                .to(topicExchange) // para quem iremos fazer o binding dessa fila
                .with(productStockKey); // com o nome da routingKey que queremos, que é a 'productStockKey'
    }

    @Bean
    public Binding salesConfirmationMqBinding(TopicExchange topicExchange){

        /*
            Então se publicarmos uma mensagem nesse "topicExchange" através dessa chave "salesConfirmationKey",
            automaticamente o Rabbit terá a inteligência de redirecionar essa mensagem para essa fila
            chamada "salesConfirmationMq()"
         */
        return BindingBuilder
                .bind(salesConfirmationMq()) // essa fila criada
                .to(topicExchange) // estará vinculada a essa exchange
                .with(salesConfirmationKey); // através dessa chave
    }

    /*
        Essa configuração abaixo significa o seguinte:
        - vamos supor que temos um objeto em Java (product por exemplo) e queremos mandar uma mensagem
            para o rabbit com esse objeto "product"... quando mandarmos publicar esse objeto "product"
            na fila (queue), automaticamente o rabbit vai converter esse objeto num JSON (ele irá criar
            a estrutura de um JSON para gente no formato de uma String) e, caso uma outra aplicação
            publique um Topic que nós estamos ouvindo (virá um JSON, claro, e o Java não identifica JSON),
            esse "MessageConverter" identifica se está chegando um JSON e tenta mapear com um formato do
            objeto que nós estamos informando, ou então ele vai simplesmente dar um erro caso ele não
            consiga converter.
         - o MessageConverter também consegue fazer o processo inverso, que é quando nós publicarmos um
            objeto, ele vai converter isso para um JsonString
         - na próxima vez criaremos alguns listenners porque ele não está ouvindo nenhuma fila por enquanto.
            Depois de criados os publishers e os listenners, temos tudo configurado
     */
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
























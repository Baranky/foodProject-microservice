package gsc.projects.shipppingservice.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rabbitConfig")
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;
    @Value("${spring.rabbitmq.producer.queue.name}")
    private String queueName;

    @Bean("rabbitMqCreateQueue")
    public Queue createQueue(){
        return new Queue(queueName);
    }

    @Bean("rabbitMqCreateExchange")
    public TopicExchange createExchange(){
        return new TopicExchange(exchange);
    }

    @Bean("rabbitMqBinding")
    public Binding binding(){
        return BindingBuilder.bind(createQueue())
                .to(createExchange())
                .with(routingKey);
    }

    @Bean("rabbitMqMessageConverter")
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean("rabbitMqAmqpTemplate")
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
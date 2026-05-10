package DetallePedido.DtePedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DtePedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtePedidoApplication.class, args);
	}

	@Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }

}

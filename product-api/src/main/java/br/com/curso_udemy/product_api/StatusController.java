package br.com.curso_udemy.product_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class StatusController {

    /**
     * Serve apenas de teste de conexão.
     *
     * Após começar a rodar o projeto, cole o seguinte link no navegador:
     * http://localhost:8081/api/status
     *
     * Se ele responder com os dados definidos por você no body do método "getApiStatus()",
     * significa que está tudo OK
     * @return
     */
    @GetMapping("status")
    public ResponseEntity<HashMap<String, Object>> getApiStatus(){
        var response = new HashMap<String, Object>();
        response.put("service", "Product-Api");
        response.put("status", "up");
        response.put("httpStatus", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}

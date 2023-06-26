package com.example.dynamic.calculations.client;

import com.example.dynamic.calculations.client.model.Formula;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class DynamicCalculationsClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(DynamicCalculationsClientApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/api/v1/formulas";

//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl, String.class);
//        //Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//

//        HttpEntity<Formula> request = new HttpEntity<>(new Formula("test"));
//        Formula formula = restTemplate.postForObject(fooResourceUrl, request, Formula.class);

//        Formula response
//                = restTemplate.getForObject(fooResourceUrl + "/14", Formula.class);
//        System.out.println(response.getFormulaString());

//        restTemplate.delete(fooResourceUrl + "/" + 19);

        Formula updatedInstance
                = restTemplate.getForObject(fooResourceUrl + "/" + 23, Formula.class);

        updatedInstance.setId(23);
        updatedInstance.setX5(9);
        String resourceUrl =
                fooResourceUrl + '/' + 23;
        HttpEntity<Formula> requestUpdate = new HttpEntity<>(updatedInstance);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);

    }

}

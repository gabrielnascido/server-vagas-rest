package com.example.servervagasrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ServerVagasRestApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a porta para hospedar o servidor (ex: 8080): ");
        String port = scanner.nextLine();
        scanner.close();

        if (port == null || port.trim().isEmpty()) {
            System.out.println("Nenhuma porta fornecida. Usando a porta padrão (8080).");
            port = "8080";
        }

        SpringApplication app = new SpringApplication(ServerVagasRestApplication.class);

        app.setDefaultProperties(java.util.Collections.singletonMap("server.port", port));

        app.run(args);
    }

}

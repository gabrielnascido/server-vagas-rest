package com.example.servervagasrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.Collections;

@SpringBootApplication
public class ServerVagasRestApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        String port = JOptionPane.showInputDialog(
                null,
                "Digite a porta para hospedar o servidor (ex: 8080):",
                "Configuração do Servidor",
                JOptionPane.QUESTION_MESSAGE
        );

        if (port == null || port.trim().isEmpty()) {
            port = "8080";
            JOptionPane.showMessageDialog(null, "Nenhuma porta definida. Iniciando na 8080.");
        }

        SpringApplication app = new SpringApplication(ServerVagasRestApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));

        ConfigurableApplicationContext context = app.run(args);

        openBrowser(port);
    }

    private static void openBrowser(String port) {
        String url = "http://localhost:" + port + "/dashboard.html";

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao tentar abrir o navegador: " + e.getMessage());
            }
        } else {
            System.out.println("Não foi possível abrir o navegador automaticamente. Acesse: " + url);
        }
    }
}
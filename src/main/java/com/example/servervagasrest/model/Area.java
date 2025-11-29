package com.example.servervagasrest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Area {
    ADMINISTRACAO("ADM", "Administração"),
    AGRICULTURA("AGR", "Agricultura"),
    ARTES("ART", "Artes"),
    ATENDIMENTO_CLIENTE("ATC", "Atendimento ao Cliente"),
    COMERCIAL("COM", "Comercial"),
    COMUNICACAO("CMN", "Comunicação"),
    CONSTRUCAO_CIVIL("CSC", "Construção Civil"),
    CONSULTORIA("CSL", "Consultoria"),
    CONTABILIDADE("CTB", "Contabilidade"),
    DESIGN("DSN", "Design"),
    EDUCACAO("EDU", "Educação"),
    ENGENHARIA("ENG", "Engenharia"),
    FINANCAS("FIN", "Finanças"),
    JURIDICA("JUR", "Jurídica"),
    LOGISTICA("LOG", "Logística"),
    MARKETING("MKT", "Marketing"),
    PRODUCAO("PRO", "Produção"),
    RECURSOS_HUMANOS("RH", "Recursos Humanos"),
    SAUDE("SAU", "Saúde"),
    SEGURANCA("SEG", "Segurança"),
    TECNOLOGIA_DA_INFORMACAO("TI", "Tecnologia da Informação"),
    TELEMARKETING("TLM", "Telemarketing"),
    VENDAS("VND", "Vendas"),
    OUTROS("OUT", "Outros");

    private final String code;
    private final String name;

    private Area(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static Area fromName(String name) {
        if (name == null) {
            return null;
        }

        for (Area area : Area.values()) {

            if (area.name.equalsIgnoreCase(name)) {
                return area;
            }
        }

        throw new IllegalArgumentException("Área de vaga '" + name + "' não reconhecida.");
    }
}

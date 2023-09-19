package com.example.service;

import com.example.entity.Agente;
import com.example.entity.Regiao;
import com.example.entity.XmlData;
import com.example.repository.XmlRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlService {

  private final XmlRepository xmlRepository;

  public XmlService(XmlRepository xmlRepository) {
    this.xmlRepository = xmlRepository;
  }

  public ResponseEntity<String> processXmlFiles(MultipartFile[] files)
    throws JAXBException, IOException {
    List<XmlData> xmlDataList = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        return ResponseEntity
          .badRequest()
          .body(
            "{\"message\": \"Nenhum conteúdo do arquivo foi encontrado.\"}"
          );
      }

      // Inicialize o contexto JAXB para a classe XmlData
      JAXBContext jaxbContext = JAXBContext.newInstance(XmlData.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // Faz a conversão do XML para um objeto XmlData
      XmlData xmlData = (XmlData) unmarshaller.unmarshal(file.getInputStream());

      xmlDataList.add(xmlData);
      // System.out.println(xmlData);

      // Imprime os agentes na tela.
      xmlData
        .getAgentes()
        .forEach(agente -> {
          System.out.println("Código do Agente: " + agente.getCodigo());
        });
      System.out.println("_______________________________");

      // Printa os dados consolidados.
      printConsolidatedValues(xmlData);
    }
    // Salva os registros no banco de dados
    System.out.println("Salvando Dados do XML no Banco de Dados");
    xmlRepository.saveAll(xmlDataList);
    return ResponseEntity
      .ok()
      .body("{\"message\": \"Upload de arquivo XML bem-sucedido\"}");
  }

  // Função que procura pelos dados de valores consolidados de cada região baseado no arquivo Xml.
  private void printConsolidatedValues(XmlData xmlData) {
    if (xmlData != null && xmlData.getAgentes() != null) {
      Map<String, Double> totalCompraPorSigla = new HashMap<>();
      Map<String, Double> totalGeracaoPorSigla = new HashMap<>();
      System.out.println("XML AGENTES: " + xmlData.getAgentes().size() + "");
      for (Agente agente : xmlData.getAgentes()) {
        if (agente.getRegioes() != null) {
          for (Regiao regiao : agente.getRegioes()) {
            if (regiao.getCompra() != null) {
              double totalCompra = regiao
                .getCompra()
                .stream()
                .mapToDouble(compra ->
                  compra
                    .getValor()
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .sum()
                )
                .sum();
              totalCompraPorSigla.merge(
                regiao.getSigla(),
                totalCompra,
                (a, b) -> a + b
              );
            }

            if (regiao.getGeracao() != null) {
              double totalGeracao = regiao
                .getGeracao()
                .stream()
                .mapToDouble(geracao ->
                  geracao
                    .getValor()
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .sum()
                )
                .sum();
              totalGeracaoPorSigla.merge(
                regiao.getSigla(),
                totalGeracao,
                (a, b) -> a + b
              );
            }
          }
        }
      }

      // Imprime os registros de cada região.
      totalCompraPorSigla.forEach((sigla, totalCompra) -> {
        Double totalGeracao = totalGeracaoPorSigla.getOrDefault(sigla, 0.0);
        System.out.println("Regiao: \"" + sigla + "\"");
        System.out.printf("Total Geracao: \"%.2f\"\n", totalGeracao);
        System.out.printf("Total Compra:  \"%.2f\"\n", totalCompra);
        System.out.println("_______________________________");
      });
    }
  }
}

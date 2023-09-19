package com.example.controller;

import com.example.service.XmlService;
import javax.xml.bind.JAXBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class XmlController {

  private final XmlService xmlService;

  public XmlController(XmlService xmlService) {
    this.xmlService = xmlService;
  }

  @PostMapping("/")
  public ResponseEntity<String> uploadXmlFiles(
    @RequestParam("files") MultipartFile[] files
  ) {
    try {
      ResponseEntity<String> response = xmlService.processXmlFiles(files);
      System.out.println(response);
      return response;
    } catch (JAXBException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Erro ao processar o XML");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("Erro interno do servidor");
    }
  }
}

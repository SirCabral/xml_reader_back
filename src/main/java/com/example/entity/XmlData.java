package com.example.entity;

import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "xml_data")
@XmlRootElement(name = "agentes")
public class XmlData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
  private Long id;

  @OneToMany(mappedBy = "xmlData", cascade = CascadeType.ALL)
  private List<Agente> agentes;

  @XmlElement(name = "agente")
  public List<Agente> getAgentes() {
    return agentes;
  }

  public void setAgentes(List<Agente> agentes) {
    this.agentes = agentes;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("XmlData {");
    sb.append("\n  agentes = [");
    if (agentes != null) {
      for (Agente agente : agentes) {
        sb.append("\n    ").append(agente.toString());
      }
    }
    sb.append("\n  ]");
    sb.append("\n}");

    return sb.toString();
  }
}

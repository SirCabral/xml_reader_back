package com.example.entity;

import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "agente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Agente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @XmlElement(name = "codigo")
  private Integer codigo;

  @XmlElement(name = "data")
  private String data;

  @ManyToOne
  @JoinColumn(name = "xml_data_id", referencedColumnName = "id")
  private XmlData xmlData;

  @OneToMany(mappedBy = "agente", cascade = CascadeType.ALL)
  @XmlElement(name = "regiao")
  private List<Regiao> regioes;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public List<Regiao> getRegioes() {
    return regioes;
  }

  public void setRegioes(List<Regiao> regioes) {
    this.regioes = regioes;
  }

  @Override
  public String toString() {
    return (
      "Agente{" +
      "codigo=" +
      codigo +
      ", data='" +
      data +
      ", regiao='" +
      regioes.toString() +
      "'}"
    );
  }
}

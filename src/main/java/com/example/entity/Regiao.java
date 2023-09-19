package com.example.entity;

import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "regiao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Regiao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @XmlAttribute(name = "sigla")
  private String sigla;

  @ManyToOne
  @JoinColumn(name = "agente_id", referencedColumnName = "id")
  private Agente agente;

  @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
  @XmlElement(name = "geracao")
  private List<Geracao> geracao;

  @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
  @XmlElement(name = "compra")
  private List<Compra> compra;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSigla() {
    return sigla;
  }

  public void setSigla(String sigla) {
    this.sigla = sigla;
  }

  public List<Geracao> getGeracao() {
    return geracao;
  }

  public void setGeracao(List<Geracao> geracao) {
    this.geracao = geracao;
  }

  public List<Compra> getCompra() {
    return compra;
  }

  public void setCompra(List<Compra> compra) {
    this.compra = compra;
  }

  @Override
  public String toString() {
    return (
      "Regiao{" +
      "id=" +
      id +
      ", sigla='" +
      sigla +
      '\'' +
      ", geracao='" +
      geracao +
      '\'' +
      ", compra='" +
      compra +
      '\'' +
      '}'
    );
  }
}

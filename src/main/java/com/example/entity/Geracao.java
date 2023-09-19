package com.example.entity;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "geracao")
public class Geracao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  private List<Double> valor;

  @ManyToOne
  @JoinColumn(name = "regiao_id", referencedColumnName = "id")
  private Regiao regiao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Double> getValor() {
    return valor;
  }

  public void setValor(List<Double> valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "Geracao{" + "id=" + id + ", valor=" + valor + '}';
  }
}

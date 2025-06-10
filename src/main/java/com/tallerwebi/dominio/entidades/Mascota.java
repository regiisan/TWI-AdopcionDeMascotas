package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean adoptado;
    private String img;
    private String salud;
    private String comportamiento;
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    @Enumerated(EnumType.STRING)
    private NivelEnergia nivelEnergia;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAdoptado() {
        return adoptado;
    }
    public void setAdoptado(Boolean adoptado) {
        this.adoptado = adoptado;
    }

    public String getImg() { return img; }
    public void setImg(String img) {
        this.img = img;
    }

    public String getSalud() { return salud; }
    public void setSalud(String salud) {this.salud = salud;}

    public String getComportamiento() { return comportamiento; }
    public void setComportamiento(String comportamiento) {this.comportamiento = comportamiento;}

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public Tamano getTamano() { return tamano; }
    public void setTamano(Tamano tamano) { this.tamano = tamano; }

    public NivelEnergia getNivelEnergia() { return nivelEnergia; }
    public void setNivelEnergia(NivelEnergia nivelEnergia) { this.nivelEnergia = nivelEnergia; }
}

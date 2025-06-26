package com.tallerwebi.dominio.entidades;


public class MascotaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean adoptado;
    private String img;
    private String salud;
    private String comportamiento;
    private Tipo tipo;
    private Integer edad;
    private Sexo sexo;
    private Tamano tamano;
    private NivelEnergia nivelEnergia;
    private int coincidencias;
    private boolean esMatch;
    private Usuario usuario;

    public MascotaDto(Mascota mascota, int coincidencias) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.descripcion = mascota.getDescripcion();
        this.adoptado = mascota.getAdoptado();
        this.img = mascota.getImg();
        this.salud = mascota.getSalud();
        this.comportamiento = mascota.getComportamiento();
        this.tipo = mascota.getTipo();
        this.edad = mascota.getEdad();
        this.sexo = mascota.getSexo();
        this.tamano = mascota.getTamano();
        this.nivelEnergia = mascota.getNivelEnergia();
        this.coincidencias = coincidencias;
        this.esMatch = coincidencias > 3;
        this.usuario = mascota.getUsuario();
    }

    public MascotaDto(Mascota mascota) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.descripcion = mascota.getDescripcion();
        this.adoptado = mascota.getAdoptado();
        this.img = mascota.getImg();
        this.salud = mascota.getSalud();
        this.comportamiento = mascota.getComportamiento();
        this.tipo = mascota.getTipo();
        this.edad = mascota.getEdad();
        this.sexo = mascota.getSexo();
        this.tamano = mascota.getTamano();
        this.nivelEnergia = mascota.getNivelEnergia();
        this.coincidencias = 0;
        this.esMatch = false;
        this.usuario = mascota.getUsuario();
    }

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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public NivelEnergia getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(NivelEnergia nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public int getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(int coincidencias) {
        this.coincidencias = coincidencias;
    }

    public boolean isEsMatch() {
        return esMatch;
    }

    public void setEsMatch(boolean esMatch) {
        this.esMatch = esMatch;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSalud() {
        return salud;
    }

    public void setSalud(String salud) {
        this.salud = salud;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mascota obtenerEntidad() {
        Mascota mascota = new Mascota();
        return this.obtenerEntidad(mascota);
    }

    public Mascota obtenerEntidad(Mascota mascota) {
        mascota.setId(this.id);
        mascota.setNombre(this.nombre);
        mascota.setDescripcion(this.descripcion);
        mascota.setAdoptado(this.adoptado);
        mascota.setImg(this.img);
        mascota.setSalud(this.salud);
        mascota.setComportamiento(this.comportamiento);
        mascota.setTipo(this.tipo);
        mascota.setEdad(this.edad);
        mascota.setSexo(this.sexo);
        mascota.setTamano(this.tamano);
        mascota.setNivelEnergia(this.nivelEnergia);
        mascota.setUsuario(this.usuario);
        return mascota;
    }


}

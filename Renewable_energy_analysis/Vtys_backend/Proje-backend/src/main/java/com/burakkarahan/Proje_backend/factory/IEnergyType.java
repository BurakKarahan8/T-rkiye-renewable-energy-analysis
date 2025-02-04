package com.burakkarahan.Proje_backend.factory;

public interface IEnergyType {
    Integer getId();
    String getIl();
    Float getKuruluGuc();
    void setId(Integer id);
    void setIl(String il);
    void setKuruluGuc(Float kuruluGuc);
}
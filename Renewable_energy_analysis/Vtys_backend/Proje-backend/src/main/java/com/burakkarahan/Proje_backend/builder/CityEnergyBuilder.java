package com.burakkarahan.Proje_backend.builder;

import com.burakkarahan.Proje_backend.entities.CityEnergyDTO;

public class CityEnergyBuilder {
    private CityEnergyDTO cityEnergy;

    public CityEnergyBuilder() {
        cityEnergy = new CityEnergyDTO();
    }

    public CityEnergyBuilder withId(Integer id) {
        cityEnergy.setId(id);
        return this;
    }

    public CityEnergyBuilder withName(String name) {
        cityEnergy.setName(name);
        return this;
    }

    public CityEnergyBuilder withWindEnergy(double windEnergy) {
        cityEnergy.setWindEnergy(windEnergy);
        return this;
    }

    public CityEnergyBuilder withSolarEnergy(double solarEnergy) {
        cityEnergy.setSolarEnergy(solarEnergy);
        return this;
    }

    public CityEnergyBuilder withHydroEnergy(double hydroEnergy) {
        cityEnergy.setHydroEnergy(hydroEnergy);
        return this;
    }

    public CityEnergyDTO build() {
        return cityEnergy;
    }
}
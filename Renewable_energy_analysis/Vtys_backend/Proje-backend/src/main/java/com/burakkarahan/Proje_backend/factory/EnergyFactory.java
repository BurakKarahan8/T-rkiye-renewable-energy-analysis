package com.burakkarahan.Proje_backend.factory;

import org.springframework.stereotype.Component;
import com.burakkarahan.Proje_backend.entities.*;

@Component
public class EnergyFactory {
    public static IEnergyType createEnergy(String type) {
        switch (type.toUpperCase()) {
            case "GUNES":
                return new GunesEnerjisi();
            case "RUZGAR":
                return new RuzgarEnerjisi();
            case "HIDROELEKTRIK":
                return new HidroelektrikEnerjisi();
            default:
                throw new IllegalArgumentException("Geçersiz enerji tipi: " + type);
        }
    }
}
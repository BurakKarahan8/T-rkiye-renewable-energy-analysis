package com.burakkarahan.Proje_backend.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burakkarahan.Proje_backend.entities.CityEnergyDTO;
import com.burakkarahan.Proje_backend.entities.GunesEnerjisi;
import com.burakkarahan.Proje_backend.entities.HidroelektrikEnerjisi;
import com.burakkarahan.Proje_backend.entities.RuzgarEnerjisi;
import com.burakkarahan.Proje_backend.repository.GunesEnerjisiRepository;
import com.burakkarahan.Proje_backend.repository.HidroelektrikEnerjisiRepository;
import com.burakkarahan.Proje_backend.repository.RuzgarEnerjisiRepository;
import com.burakkarahan.Proje_backend.service.IEnergyService;
import com.burakkarahan.Proje_backend.builder.CityEnergyBuilder;

@Service
public class EnergyServiceImpl implements IEnergyService {

    @Autowired
    private GunesEnerjisiRepository gunesEnerjisiRepository;

    @Autowired
    private HidroelektrikEnerjisiRepository hidroelektrikEnerjisiRepository;

    @Autowired
    private RuzgarEnerjisiRepository ruzgarEnerjisiRepository;

    @Override
    public List<GunesEnerjisi> getAllGunesEnerjisi() {
        List<GunesEnerjisi> gunesEnerjisiList = gunesEnerjisiRepository.findAll();
        return gunesEnerjisiList;
    }

    @Override
    public List<HidroelektrikEnerjisi> getAllHidroelektrikEnerjisi() {
        List<HidroelektrikEnerjisi> hidroelektrikEnerjisiList = hidroelektrikEnerjisiRepository.findAll();
        return hidroelektrikEnerjisiList;
    }

    @Override
    public List<RuzgarEnerjisi> getAllRuzgarEnerjisi() {
        List<RuzgarEnerjisi> ruzgarEnerjisiList = ruzgarEnerjisiRepository.findAll();
        return  ruzgarEnerjisiList;
    }

    @Override
    public List<CityEnergyDTO> getCityEnergyData() {
        Map<String, CityEnergyDTO> cityEnergyMap = new HashMap<>();

        // Güneş enerjisi verilerini al
        for (GunesEnerjisi solar : gunesEnerjisiRepository.findAll()) {
            String cityName = solar.getIl();

            // Güneş enerjisi için builder kullanarak nesne oluşturma
            CityEnergyDTO dto = new CityEnergyBuilder()
                .withId(solar.getId())
                .withName(cityName)
                .withSolarEnergy(solar.getKuruluGuc())
                .build();

            cityEnergyMap.put(cityName, dto);
        }

        // Rüzgar enerjisi verilerini al
        for (RuzgarEnerjisi ruzgar : ruzgarEnerjisiRepository.findAll()) {
            String cityName = ruzgar.getIl();

            // Rüzgar enerjisi için builder kullanarak nesne oluşturma
            CityEnergyDTO dto = cityEnergyMap.getOrDefault(cityName, new CityEnergyDTO());
            dto.setId(ruzgar.getId());
            dto.setName(cityName);
            dto.setWindEnergy(ruzgar.getKuruluGuc());

            cityEnergyMap.put(cityName, dto);
        }

        // Hidroelektrik enerjisi verilerini al
        for (HidroelektrikEnerjisi hidro : hidroelektrikEnerjisiRepository.findAll()) {
            String cityName = hidro.getIl();

            // Hidroelektrik enerjisi için builder kullanarak nesne oluşturma
            CityEnergyDTO dto = cityEnergyMap.getOrDefault(cityName, new CityEnergyDTO());
            dto.setId(hidro.getId());
            dto.setName(cityName);
            dto.setHydroEnergy(hidro.getKuruluGuc());

            cityEnergyMap.put(cityName, dto);
        }

        return new ArrayList<>(cityEnergyMap.values());
    }

}
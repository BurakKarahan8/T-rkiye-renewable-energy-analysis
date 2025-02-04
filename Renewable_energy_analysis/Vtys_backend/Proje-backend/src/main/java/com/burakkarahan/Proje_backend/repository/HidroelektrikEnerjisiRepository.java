package com.burakkarahan.Proje_backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.burakkarahan.Proje_backend.config.DatabaseConnection;
import com.burakkarahan.Proje_backend.entities.HidroelektrikEnerjisi;
import com.burakkarahan.Proje_backend.factory.EnergyFactory;
import com.burakkarahan.Proje_backend.factory.IEnergyType;

@Repository
public class HidroelektrikEnerjisiRepository {
    private final DatabaseConnection dbConnection;

    public HidroelektrikEnerjisiRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public List<HidroelektrikEnerjisi> findAll() {
        List<HidroelektrikEnerjisi> hidroelektrikEnerjisiList = new ArrayList<>();
        String sql = "SELECT * FROM hidroelektrik_enerjisi";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                IEnergyType energy = EnergyFactory.createEnergy("HIDROELEKTRIK");
                energy.setId(rs.getInt("id"));
                energy.setIl(rs.getString("il"));
                energy.setKuruluGuc(rs.getFloat("kurulu_guc"));
                
                hidroelektrikEnerjisiList.add((HidroelektrikEnerjisi) energy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Hidroelektrik enerjisi verileri alınamadı: " + e.getMessage());
        }

        return hidroelektrikEnerjisiList;
    }
}

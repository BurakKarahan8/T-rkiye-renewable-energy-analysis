package com.burakkarahan.Proje_backend.repository;

import org.springframework.stereotype.Repository;
import com.burakkarahan.Proje_backend.config.DatabaseConnection;
import com.burakkarahan.Proje_backend.entities.GunesEnerjisi;
import com.burakkarahan.Proje_backend.factory.IEnergyType;
import com.burakkarahan.Proje_backend.factory.EnergyFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GunesEnerjisiRepository {
    private final DatabaseConnection dbConnection;

    public GunesEnerjisiRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public List<GunesEnerjisi> findAll() {
        List<GunesEnerjisi> gunesEnerjisiList = new ArrayList<>();
        String sql = "SELECT * FROM gunes_enerjisi";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                IEnergyType energy = EnergyFactory.createEnergy("GUNES");
                energy.setId(rs.getInt("id"));
                energy.setIl(rs.getString("il"));
                energy.setKuruluGuc(rs.getFloat("kurulu_guc"));
                
                gunesEnerjisiList.add((GunesEnerjisi) energy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Güneş enerjisi verileri alınamadı: " + e.getMessage());
        }

        return gunesEnerjisiList;
    }
}

package com.burakkarahan.Proje_backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.burakkarahan.Proje_backend.config.DatabaseConnection;
import com.burakkarahan.Proje_backend.entities.RuzgarEnerjisi;
import com.burakkarahan.Proje_backend.factory.EnergyFactory;
import com.burakkarahan.Proje_backend.factory.IEnergyType;

@Repository
public class RuzgarEnerjisiRepository {
    private final DatabaseConnection dbConnection;

    public RuzgarEnerjisiRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public List<RuzgarEnerjisi> findAll() {
        List<RuzgarEnerjisi> ruzgarEnerjisiList = new ArrayList<>();
        String sql = "SELECT * FROM ruzgar_enerjisi";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                IEnergyType energy = EnergyFactory.createEnergy("RUZGAR");
                energy.setId(rs.getInt("id"));
                energy.setIl(rs.getString("il"));
                energy.setKuruluGuc(rs.getFloat("kurulu_guc"));
                
                ruzgarEnerjisiList.add((RuzgarEnerjisi) energy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Rüzgar enerjisi verileri alınamadı: " + e.getMessage());
        }

        return ruzgarEnerjisiList;
    }
}

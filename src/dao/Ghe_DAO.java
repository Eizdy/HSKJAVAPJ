package dao;

import connectDB.ConnectDB;
import entity.Ghe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ghe_DAO {

    public boolean themGhe(Ghe ghe) {
        String sql = "INSERT INTO Ghe (maGhe, viTri, daDat) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getMaGhe());
            stmt.setString(2, ghe.getViTri());
            stmt.setBoolean(3, ghe.isDaDat());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatGhe(Ghe ghe) {
        String sql = "UPDATE Ghe SET viTri = ?, daDat = ? WHERE maGhe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getViTri());
            stmt.setBoolean(2, ghe.isDaDat());
            stmt.setString(3, ghe.getMaGhe());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaGhe(String maGhe) {
        String sql = "DELETE FROM Ghe WHERE maGhe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ghe timGheTheoMa(String maGhe) {
        String sql = "SELECT * FROM Ghe WHERE maGhe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ghe(
                        rs.getString("maGhe"),
                        rs.getString("viTri"),
                        rs.getBoolean("daDat")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ghe> layTatCaGhe() {
        List<Ghe> ds = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new Ghe(
                        rs.getString("maGhe"),
                        rs.getString("viTri"),
                        rs.getBoolean("daDat")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

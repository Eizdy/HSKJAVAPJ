package dao;

import entity.PhongChieuPhim;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class PhongChieuPhim_DAO {
    private Connection connection;

    public PhongChieuPhim_DAO() {
        try {
            // Giả định bạn có sẵn ConnectDB.getConnection()
            connection = ConnectDB.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public boolean themPhong(PhongChieuPhim phong) {
        String sql = "INSERT INTO PhongChieuPhim (maPhong, tenPhong, soGhe) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, phong.getTenPhong());
            stmt.setInt(3, phong.getSoLuongGhe());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ ALL
    public List<PhongChieuPhim> layTatCaPhong() {
        List<PhongChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhongChieuPhim";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ma = rs.getString("maPhong");
                String ten = rs.getString("tenPhong");
                int soGhe = rs.getInt("soGhe");
                ds.add(new PhongChieuPhim(ma, ten, soGhe));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // READ ONE
    public PhongChieuPhim timPhongTheoMa(String maPhong) {
        String sql = "SELECT * FROM PhongChieuPhim WHERE maPhong = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String ten = rs.getString("tenPhong");
                int soGhe = rs.getInt("soGhe");
                return new PhongChieuPhim(maPhong, ten, soGhe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean capNhatPhong(PhongChieuPhim phong) {
        String sql = "UPDATE PhongChieuPhim SET tenPhong = ?, soGhe = ? WHERE maPhong = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phong.getTenPhong());
            stmt.setInt(2, phong.getSoLuongGhe());
            stmt.setString(3, phong.getMaPhong());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean xoaPhong(String maPhong) {
        String sql = "DELETE FROM PhongChieuPhim WHERE maPhong = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

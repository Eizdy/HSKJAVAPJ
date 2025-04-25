package dao;

import connectDB.ConnectDB;
import entity.PhongChieuPhim;

import java.sql.*;
import java.util.*;

public class PhongChieuPhim_DAO {

    public boolean themPhong(PhongChieuPhim phong) {
        String sql = "INSERT INTO PhongChieuPhim (maPhong, tenPhong, soLuongGhe) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, phong.getTenPhong());
            stmt.setInt(3, phong.getSoLuongGhe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatPhong(PhongChieuPhim phong) {
        String sql = "UPDATE PhongChieuPhim SET tenPhong = ?, soLuongGhe = ? WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phong.getTenPhong());
            stmt.setInt(2, phong.getSoLuongGhe());
            stmt.setString(3, phong.getMaPhong());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaPhong(String maPhong) {
        String sql = "DELETE FROM PhongChieuPhim WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhongChieuPhim timPhongTheoMa(String maPhong) {
        String sql = "SELECT * FROM PhongChieuPhim WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PhongChieuPhim(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhongChieuPhim> layTatCaPhong() {
        List<PhongChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhongChieuPhim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new PhongChieuPhim(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

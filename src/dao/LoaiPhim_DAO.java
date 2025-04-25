package dao;

import connectDB.ConnectDB;
import entity.LoaiPhim;

import java.sql.*;
import java.util.*;

public class LoaiPhim_DAO {

    public boolean themLoaiPhim(LoaiPhim loai) {
        String sql = "INSERT INTO LoaiPhim (maLoai, tenLoai, moTa) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loai.getMaLoai());
            stmt.setString(2, loai.getTenLoai());
            stmt.setString(3, loai.getMoTa());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLoaiPhim(LoaiPhim loai) {
        String sql = "UPDATE LoaiPhim SET tenLoai = ?, moTa = ? WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loai.getTenLoai());
            stmt.setString(2, loai.getMoTa());
            stmt.setString(3, loai.getMaLoai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaLoaiPhim(String maLoai) {
        String sql = "DELETE FROM LoaiPhim WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLoai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LoaiPhim timLoaiPhimTheoMa(String maLoai) {
        String sql = "SELECT * FROM LoaiPhim WHERE maLoai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLoai);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new LoaiPhim(
                        rs.getString("maLoai"),
                        rs.getString("tenLoai"),
                        rs.getString("moTa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LoaiPhim> layTatCaLoaiPhim() {
        List<LoaiPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM LoaiPhim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new LoaiPhim(
                        rs.getString("maLoai"),
                        rs.getString("tenLoai"),
                        rs.getString("moTa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

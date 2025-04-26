package dao;

import connectDB.ConnectDB;
import entity.PhongChieuPhim;

import java.sql.*;
import java.util.*;

public class PhongChieuPhim_DAO {

    public boolean themPhong(PhongChieuPhim phong) throws SQLException {
        String sql = "INSERT INTO PhongChieuPhim (maPhong, tenPhong, trangThaiPhongChieu, soLuongGhe) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, phong.getTenPhong());
            stmt.setBoolean(3, phong.isTrangThaiPhongChieu());
            stmt.setInt(4, phong.getSoLuongGhe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm phòng chiếu: " + e.getMessage(), e);
        }
    }

    public boolean capNhatPhong(PhongChieuPhim phong) throws SQLException {
        String sql = "UPDATE PhongChieuPhim SET tenPhong = ?, trangThaiPhongChieu = ?, soLuongGhe = ? WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phong.getTenPhong());
            stmt.setBoolean(2, phong.isTrangThaiPhongChieu());
            stmt.setInt(3, phong.getSoLuongGhe());
            stmt.setString(4, phong.getMaPhong());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật phòng chiếu: " + e.getMessage(), e);
        }
    }

    public boolean xoaPhong(String maPhong) throws SQLException {
        String sql = "DELETE FROM PhongChieuPhim WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi xóa phòng chiếu: " + e.getMessage(), e);
        }
    }

    public PhongChieuPhim timPhongTheoMa(String maPhong) throws SQLException {
        String sql = "SELECT * FROM PhongChieuPhim WHERE maPhong = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PhongChieuPhim(
                            rs.getString("maPhong"),
                            rs.getString("tenPhong"),
                            rs.getInt("soLuongGhe"),
                            rs.getBoolean("trangThaiPhongChieu")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi tìm phòng chiếu theo mã: " + e.getMessage(), e);
        }
        return null;
    }

    public List<PhongChieuPhim> layTatCaPhongChieu() throws SQLException {
        List<PhongChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhongChieuPhim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new PhongChieuPhim(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe"),
                        rs.getBoolean("trangThaiPhongChieu")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy tất cả phòng chiếu: " + e.getMessage(), e);
        }
        return ds;
    }
}
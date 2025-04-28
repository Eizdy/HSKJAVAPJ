package dao;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.PhongChieuPhim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ghe_DAO {

    public boolean themGhe(Ghe ghe) {
        String sql = "INSERT INTO Ghe (maGhe, viTri, trangThai, phong) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getMaGhe());
            stmt.setString(2, ghe.getViTri());
            stmt.setBoolean(3, ghe.isTrangThai());
            stmt.setString(4, ghe.getPhong() != null ? ghe.getPhong().getMaPhong() : null);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatGhe(Ghe ghe) {
        String sql = "UPDATE Ghe SET viTri = ?, trangThai = ?, phong = ? WHERE maGhe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getViTri());
            stmt.setBoolean(2, ghe.isTrangThai());
            stmt.setString(3, ghe.getPhong() != null ? ghe.getPhong().getMaPhong() : null);
            stmt.setString(4, ghe.getMaGhe());

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
                String maPhong = rs.getString("phong");
                PhongChieuPhim phong = maPhong != null ? new PhongChieuPhim(maPhong) : null;

                return new Ghe(
                        rs.getString("maGhe"),
                        rs.getString("viTri"),
                        rs.getBoolean("trangThai"),
                        phong
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
                String maPhong = rs.getString("phong");
                PhongChieuPhim phong = maPhong != null ? new PhongChieuPhim(maPhong) : null;

                ds.add(new Ghe(
                        rs.getString("maGhe"),
                        rs.getString("viTri"),
                        rs.getBoolean("trangThai"),
                        phong
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<String> layViTriGheTheoMaHoaDon(String maHoaDon) throws SQLException {
        List<String> viTriGheList = new ArrayList<>();
        
        String sqlGetMaVeAndLichChieu = 
            "SELECT vxp.maVe, vxp.maLichChieu " +
            "FROM HoaDon hd " +
            "JOIN VeXemPhim vxp ON hd.maVe = vxp.maVe " +
            "WHERE hd.maHD = ?";
        
        String maLichChieu = null;
        String baseMaVe = null;
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlGetMaVeAndLichChieu)) {
            
            stmt.setString(1, maHoaDon);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    baseMaVe = rs.getString("maVe");
                    maLichChieu = rs.getString("maLichChieu");
                } else {
                    throw new SQLException("Không tìm thấy hóa đơn với mã: " + maHoaDon);
                }
            }
        }

        String sqlGetSoLuong = "SELECT soLuong FROM HoaDon WHERE maHD = ?";
        int soLuong;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlGetSoLuong)) {
            
            stmt.setString(1, maHoaDon);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    soLuong = rs.getInt("soLuong");
                } else {
                    throw new SQLException("Không tìm thấy số lượng vé cho hóa đơn: " + maHoaDon);
                }
            }
        }

        int baseMaVeNumber;
        try {
            baseMaVeNumber = Integer.parseInt(baseMaVe.substring(1));
        } catch (NumberFormatException e) {
            throw new SQLException("Mã vé không hợp lệ: " + baseMaVe);
        }

        List<String> maVeList = new ArrayList<>();
        for (int i = 0; i < soLuong; i++) {
            String maVe = String.format("V%03d", baseMaVeNumber + i);
            maVeList.add(maVe);
        }

        String sqlGetViTri = 
            "SELECT g.viTri " +
            "FROM Ghe g " +
            "JOIN VeXemPhim vxp ON g.maGhe = vxp.maGhe " +
            "WHERE vxp.maVe IN (" + String.join(",", java.util.Collections.nCopies(maVeList.size(), "?")) + ") " +
            "AND vxp.maLichChieu = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlGetViTri)) {
            
            for (int i = 0; i < maVeList.size(); i++) {
                stmt.setString(i + 1, maVeList.get(i));
            }
            stmt.setString(maVeList.size() + 1, maLichChieu);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    viTriGheList.add(rs.getString("viTri"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy vị trí ghế theo mã hóa đơn: " + e.getMessage(), e);
        }

        return viTriGheList;
    }
}
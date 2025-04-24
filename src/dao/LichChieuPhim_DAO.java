package dao;

import connectDB.ConnectDB;
import entity.LichChieuPhim;
import entity.Phim;
import entity.PhongChieuPhim;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LichChieuPhim_DAO {

    public boolean themLichChieu(LichChieuPhim lc) {
        String sql = "INSERT INTO LichChieuPhim (maLichChieu, maPhim, maPhong, thoiGianChieu, giaVe) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getMaLichChieu());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setString(3, lc.getPhong().getMaPhong());
            stmt.setTimestamp(4, Timestamp.valueOf(lc.getThoiGianChieu()));
            stmt.setDouble(5, lc.getGiaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLichChieu(LichChieuPhim lc) {
        String sql = "UPDATE LichChieuPhim SET maPhim = ?, maPhong = ?, thoiGianChieu = ?, giaVe = ? WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhim().getMaPhim());
            stmt.setString(2, lc.getPhong().getMaPhong());
            stmt.setTimestamp(3, Timestamp.valueOf(lc.getThoiGianChieu()));
            stmt.setDouble(4, lc.getGiaVe());
            stmt.setString(5, lc.getMaLichChieu());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaLichChieu(String maLichChieu) {
        String sql = "DELETE FROM LichChieuPhim WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LichChieuPhim timLichChieuTheoMa(String maLichChieu) {
        String sql = "SELECT * FROM LichChieuPhim WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LichChieuPhim lc = new LichChieuPhim();
                lc.setMaLichChieu(rs.getString("maLichChieu"));

                Phim phim = new Phim();
                phim.setMaPhim(rs.getString("maPhim"));
                lc.setPhim(phim);

                PhongChieuPhim phong = new PhongChieuPhim();
                phong.setMaPhong(rs.getString("maPhong"));
                lc.setPhong(phong);

                lc.setThoiGianChieu(rs.getTimestamp("thoiGianChieu").toLocalDateTime());
                lc.setGiaVe(rs.getDouble("giaVe"));

                return lc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LichChieuPhim> layTatCaLichChieu() {
        List<LichChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM LichChieuPhim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LichChieuPhim lc = new LichChieuPhim();
                lc.setMaLichChieu(rs.getString("maLichChieu"));

                Phim phim = new Phim();
                phim.setMaPhim(rs.getString("maPhim"));
                lc.setPhim(phim);

                PhongChieuPhim phong = new PhongChieuPhim();
                phong.setMaPhong(rs.getString("maPhong"));
                lc.setPhong(phong);

                lc.setThoiGianChieu(rs.getTimestamp("thoiGianChieu").toLocalDateTime());
                lc.setGiaVe(rs.getDouble("giaVe"));

                ds.add(lc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

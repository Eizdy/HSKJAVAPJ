package dao;

import connectDB.ConnectDB;
import entity.LichChieuPhim;
import entity.Phim;
import entity.PhongChieuPhim;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class LichChieuPhim_DAO {

    public boolean themLichChieu(LichChieuPhim lc) {
        String sql = "INSERT INTO LichChieuPhim (maLichChieu, maPhim, maPhong, thoiGianChieu, trangThai, ngayChieu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getMaLichChieu());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setString(3, lc.getPhong().getMaPhong());
            stmt.setString(4, lc.getThoiGianChieu());
            stmt.setString(5, lc.getTrangThai());
            stmt.setDate(6, Date.valueOf(lc.getNgayChieu()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLichChieu(LichChieuPhim lc) {
        String sql = "UPDATE LichChieuPhim SET maPhim = ?, maPhong = ?, thoiGianChieu = ?, trangThai = ?, ngayChieu = ? WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhim().getMaPhim());
            stmt.setString(2, lc.getPhong().getMaPhong());
            stmt.setString(3, lc.getThoiGianChieu());
            stmt.setString(4, lc.getTrangThai());
            stmt.setDate(5, Date.valueOf(lc.getNgayChieu()));
            stmt.setString(6, lc.getMaLichChieu());

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
                Phim phim = new Phim();
                phim.setMaPhim(rs.getString("maPhim"));

                PhongChieuPhim phong = new PhongChieuPhim();
                phong.setMaPhong(rs.getString("maPhong"));

                return new LichChieuPhim(
                        rs.getString("maLichChieu"),
                        phim,
                        phong,
                        rs.getString("thoiGianChieu"),
                        rs.getString("trangThai"),
                        rs.getDate("ngayChieu").toLocalDate()
                );
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
                Phim phim = new Phim();
                phim.setMaPhim(rs.getString("maPhim"));

                PhongChieuPhim phong = new PhongChieuPhim();
                phong.setMaPhong(rs.getString("maPhong"));

                LichChieuPhim lc = new LichChieuPhim(
                        rs.getString("maLichChieu"),
                        phim,
                        phong,
                        rs.getString("thoiGianChieu"),
                        rs.getString("trangThai"),
                        rs.getDate("ngayChieu").toLocalDate()
                );
                ds.add(lc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

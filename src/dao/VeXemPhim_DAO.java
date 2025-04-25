package dao;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.KhachHang;
import entity.LichChieuPhim;
import entity.VeXemPhim;

import java.sql.*;
import java.util.*;

public class VeXemPhim_DAO {

    public boolean themVe(VeXemPhim ve) {
        String sql = "INSERT INTO VeXemPhim (maVe, maLichChieu, maGhe, maKhachHang, giaVe) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ve.getMaVe());
            stmt.setString(2, ve.getLichChieu().getMaLichChieu());
            stmt.setString(3, ve.getGhe().getMaGhe());
            stmt.setString(4, ve.getKhachHang().getMaKhachHang());
            stmt.setDouble(5, ve.getGiaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatVe(VeXemPhim ve) {
        String sql = "UPDATE VeXemPhim SET maLichChieu = ?, maGhe = ?, maKhachHang = ?, giaVe = ? WHERE maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ve.getLichChieu().getMaLichChieu());
            stmt.setString(2, ve.getGhe().getMaGhe());
            stmt.setString(3, ve.getKhachHang().getMaKhachHang());
            stmt.setDouble(4, ve.getGiaVe());
            stmt.setString(5, ve.getMaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaVe(String maVe) {
        String sql = "DELETE FROM VeXemPhim WHERE maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public VeXemPhim timVeTheoMa(String maVe) {
        String sql = "SELECT * FROM VeXemPhim WHERE maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LichChieuPhim lich = new LichChieuPhim();
                lich.setMaLichChieu(rs.getString("maLichChieu"));

                Ghe ghe = new Ghe();
                ghe.setMaGhe(rs.getString("maGhe"));

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));

                return new VeXemPhim(
                        rs.getString("maVe"),
                        lich,
                        ghe,
                        kh,
                        rs.getDouble("giaVe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VeXemPhim> layTatCaVe() {
        List<VeXemPhim> ds = new ArrayList<>();
        String sql = "SELECT * FROM VeXemPhim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LichChieuPhim lich = new LichChieuPhim();
                lich.setMaLichChieu(rs.getString("maLichChieu"));

                Ghe ghe = new Ghe();
                ghe.setMaGhe(rs.getString("maGhe"));

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));

                ds.add(new VeXemPhim(
                        rs.getString("maVe"),
                        lich,
                        ghe,
                        kh,
                        rs.getDouble("giaVe")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

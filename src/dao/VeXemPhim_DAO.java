package dao;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.KhachHang;
import entity.LichChieuPhim;
import entity.NhanVien;
import entity.VeXemPhim;
import entity.PhongChieuPhim;

import java.sql.*;
import java.util.*;

public class VeXemPhim_DAO {

    public boolean themVe(VeXemPhim ve) {
        String sql = "INSERT INTO VeXemPhim (maVe, maLichChieu, maGhe, maKH, giaVe, maNV) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ve.getMaVe());
            stmt.setString(2, ve.getLichChieu().getMaLichChieu());
            stmt.setString(3, ve.getGhe().getMaGhe());
            stmt.setString(4, ve.getKhachHang() != null ? ve.getKhachHang().getMaKhachHang() : null);
            stmt.setDouble(5, ve.getGiaVe());
            stmt.setString(6, ve.getNhanVien().getMaNV());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm vé: " + e.getMessage(), e);
        }
    }

    public boolean capNhatVe(VeXemPhim ve) {
        String sql = "UPDATE VeXemPhim SET maLichChieu = ?, maGhe = ?, maKH = ?, giaVe = ?, maNV = ? WHERE maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ve.getLichChieu().getMaLichChieu());
            stmt.setString(2, ve.getGhe().getMaGhe());
            stmt.setString(3, ve.getKhachHang() != null ? ve.getKhachHang().getMaKhachHang() : null);
            stmt.setDouble(4, ve.getGiaVe());
            stmt.setString(5, ve.getNhanVien().getMaNV());
            stmt.setString(6, ve.getMaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật vé: " + e.getMessage(), e);
        }
    }

    public boolean xoaVe(String maVe) {
        String sql = "DELETE FROM VeXemPhim WHERE maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa vé: " + e.getMessage(), e);
        }
    }

    public VeXemPhim timVeTheoMa(String maVe) {
        String sql = "SELECT v.*, g.viTri, g.trangThai, g.phong " +
                     "FROM VeXemPhim v " +
                     "LEFT JOIN Ghe g ON v.maGhe = g.maGhe " +
                     "WHERE v.maVe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LichChieuPhim lich = new LichChieuPhim();
                lich.setMaLichChieu(rs.getString("maLichChieu"));

                Ghe ghe = new Ghe();
                ghe.setMaGhe(rs.getString("maGhe"));
                ghe.setViTri(rs.getString("viTri"));
                ghe.setTrangThai(rs.getBoolean("trangThai"));
                String maPhong = rs.getString("phong");
                if (maPhong != null) {
                    PhongChieuPhim phong = new PhongChieuPhim();
                    phong.setMaPhong(maPhong);
                    ghe.setPhong(phong);
                }

                KhachHang kh = new KhachHang();
                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    kh.setMaKhachHang(maKH);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                return new VeXemPhim(
                        rs.getString("maVe"),
                        lich,
                        ghe,
                        maKH != null ? kh : null,
                        rs.getDouble("giaVe"),
                        nv
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm vé: " + e.getMessage(), e);
        }
        return null;
    }

    public List<VeXemPhim> layTatCaVe() {
        List<VeXemPhim> ds = new ArrayList<>();
        String sql = "SELECT v.*, g.viTri, g.trangThai, g.phong " +
                     "FROM VeXemPhim v " +
                     "LEFT JOIN Ghe g ON v.maGhe = g.maGhe";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LichChieuPhim lich = new LichChieuPhim();
                lich.setMaLichChieu(rs.getString("maLichChieu"));

                Ghe ghe = new Ghe();
                ghe.setMaGhe(rs.getString("maGhe"));
                ghe.setViTri(rs.getString("viTri"));
                ghe.setTrangThai(rs.getBoolean("trangThai"));
                String maPhong = rs.getString("phong");
                if (maPhong != null) {
                    PhongChieuPhim phong = new PhongChieuPhim();
                    phong.setMaPhong(maPhong);
                    ghe.setPhong(phong);
                }

                KhachHang kh = new KhachHang();
                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    kh.setMaKhachHang(maKH);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV"));

                ds.add(new VeXemPhim(
                        rs.getString("maVe"),
                        lich,
                        ghe,
                        maKH != null ? kh : null,
                        rs.getDouble("giaVe"),
                        nv
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách vé: " + e.getMessage(), e);
        }
        return ds;
    }
}
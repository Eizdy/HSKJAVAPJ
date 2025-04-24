package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (maHoaDon, maKhachHang, ngayLap, tongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hd.getMaHoaDon());
            stmt.setString(2, hd.getKhachHang().getMaKhachHang());
            stmt.setTimestamp(3, Timestamp.valueOf(hd.getNgayLap()));
            stmt.setDouble(4, hd.getTongTien());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET maKhachHang = ?, ngayLap = ?, tongTien = ? WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hd.getKhachHang().getMaKhachHang());
            stmt.setTimestamp(2, Timestamp.valueOf(hd.getNgayLap()));
            stmt.setDouble(3, hd.getTongTien());
            stmt.setString(4, hd.getMaHoaDon());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaHoaDon(String maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHoaDon);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon timHoaDonTheoMa(String maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString("maHoaDon"));

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                hd.setKhachHang(kh);

                hd.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hd.setDanhSachChiTiet(new ArrayList<>()); // Danh sách chi tiết có thể lấy từ DAO khác nếu cần
                return hd;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString("maHoaDon"));

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                hd.setKhachHang(kh);

                hd.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hd.setDanhSachChiTiet(new ArrayList<>()); // Có thể gọi DAO khác để lấy danh sách chi tiết
                ds.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

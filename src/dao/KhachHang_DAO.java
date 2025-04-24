package dao;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHang_DAO {

    public boolean themKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (maKhachHang, hoTen, soDienThoai, email, tenDangNhap) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKhachHang());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getSoDienThoai());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getTaiKhoan().getTenDangNhap());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET hoTen = ?, soDienThoai = ?, email = ?, tenDangNhap = ? WHERE maKhachHang = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setString(3, kh.getEmail());
            stmt.setString(4, kh.getTaiKhoan().getTenDangNhap());
            stmt.setString(5, kh.getMaKhachHang());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhachHang(String maKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhachHang timKhachHangTheoMa(String maKhachHang) {
        String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));

                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));
                kh.setTaiKhoan(tk);

                return kh;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> layTatCaKhachHang() {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));

                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));
                kh.setTaiKhoan(tk);

                ds.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

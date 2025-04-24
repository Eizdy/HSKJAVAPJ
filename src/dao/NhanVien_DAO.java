package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_DAO {

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, soDienThoai, email, tenDangNhap) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getSoDienThoai());
            stmt.setString(4, nv.getEmail());
            stmt.setString(5, nv.getTaiKhoan().getTenDangNhap());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, soDienThoai = ?, email = ?, tenDangNhap = ? WHERE maNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getSoDienThoai());
            stmt.setString(3, nv.getEmail());
            stmt.setString(4, nv.getTaiKhoan().getTenDangNhap());
            stmt.setString(5, nv.getMaNhanVien());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNhanVien);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NhanVien timNhanVienTheoMa(String maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setHoTen(rs.getString("hoTen"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));

                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));
                nv.setTaiKhoan(tk);

                return nv;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setHoTen(rs.getString("hoTen"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));

                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));
                nv.setTaiKhoan(tk);

                ds.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

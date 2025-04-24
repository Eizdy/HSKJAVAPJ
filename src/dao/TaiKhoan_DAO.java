package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {

    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, vaiTro) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tk.getTenDangNhap());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getVaiTro());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET matKhau = ?, vaiTro = ? WHERE tenDangNhap = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tk.getMatKhau());
            stmt.setString(2, tk.getVaiTro());
            stmt.setString(3, tk.getTenDangNhap());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaTaiKhoan(String tenDangNhap) {
        String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public TaiKhoan timTaiKhoanTheoTen(String tenDangNhap) {
        String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("tenDangNhap"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoan> layTatCaTaiKhoan() {
        List<TaiKhoan> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new TaiKhoan(
                        rs.getString("tenDangNhap"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

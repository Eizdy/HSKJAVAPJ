package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;

import java.sql.*;
import java.util.*;


public class TaiKhoan_DAO {

    public boolean themTaiKhoan(TaiKhoan tk) {
        validateTaiKhoan(tk);

        if (timTaiKhoanTheoTen(tk.getTenDangNhap()) != null) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại: " + tk.getTenDangNhap());
        }

        String sql = "INSERT INTO TaiKhoan (tenTK, matKhau) VALUES (?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tk.getTenDangNhap());
            stmt.setString(2, tk.getMatKhau());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm tài khoản: " + e.getMessage(), e);
        }
    }

    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        validateTaiKhoan(tk);

        String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenTK = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tk.getMatKhau());
            stmt.setString(2, tk.getTenDangNhap());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật tài khoản: " + e.getMessage(), e);
        }
    }

    public boolean xoaTaiKhoan(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }

        String sql = "DELETE FROM TaiKhoan WHERE tenTK = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa tài khoản: " + e.getMessage(), e);
        }
    }

    public TaiKhoan timTaiKhoanTheoTen(String tenDangNhap) {
        // Validate input
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }

        String sql = "SELECT * FROM TaiKhoan WHERE tenTK = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("tenTK"),
                        rs.getString("matKhau")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm tài khoản: " + e.getMessage(), e);
        }
        return null;
    }

    public List<TaiKhoan> layTatCaTaiKhoan() {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT tenTK, matKhau FROM TaiKhoan";

        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tenDangNhap = rs.getString("tenTK");
                String matKhau = rs.getString("matKhau");
                dsTaiKhoan.add(new TaiKhoan(tenDangNhap, matKhau));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
               
        } 
        return dsTaiKhoan;
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }
        if (matKhau == null || matKhau.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }

        String sql = "SELECT * FROM TaiKhoan WHERE tenTK = ? AND matKhau = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("tenTK"), // Map tenTK to tenDangNhap
                        rs.getString("matKhau")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đăng nhập: " + e.getMessage(), e);
        }
        return null;
    }


    public boolean isAdmin(String tenDangNhap) {
        // Validate input
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }

        return "ADMIN".equalsIgnoreCase(tenDangNhap);
    }


    private void validateTaiKhoan(TaiKhoan tk) {
        if (tk == null) {
            throw new IllegalArgumentException("Tài khoản không được null.");
        }
        if (tk.getTenDangNhap() == null || tk.getTenDangNhap().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }
        if (tk.getMatKhau() == null || tk.getMatKhau().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }
    }

}
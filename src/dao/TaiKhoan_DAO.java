package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) for managing TaiKhoan entities in the database.
 */
public class TaiKhoan_DAO {

    /**
     * Adds a new account to the database.
     *
     * @param tk The TaiKhoan object to add.
     * @return true if the account was added successfully, false otherwise.
     * @throws IllegalArgumentException if tenDangNhap or matKhau is null/empty, or if tenDangNhap already exists.
     */
    public boolean themTaiKhoan(TaiKhoan tk) {
        // Validate input
        validateTaiKhoan(tk);

        // Check for duplicate tenDangNhap
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

    /**
     * Updates an existing account's password in the database.
     *
     * @param tk The TaiKhoan object with updated password.
     * @return true if the account was updated successfully, false otherwise.
     * @throws IllegalArgumentException if tenDangNhap or matKhau is null/empty.
     */
    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        // Validate input
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

    /**
     * Deletes an account from the database based on tenDangNhap.
     *
     * @param tenDangNhap The username of the account to delete.
     * @return true if the account was deleted successfully, false otherwise.
     * @throws IllegalArgumentException if tenDangNhap is null/empty.
     */
    public boolean xoaTaiKhoan(String tenDangNhap) {
        // Validate input
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

    /**
     * Retrieves an account from the database based on tenDangNhap.
     *
     * @param tenDangNhap The username of the account to find.
     * @return The TaiKhoan object if found, null otherwise.
     * @throws IllegalArgumentException if tenDangNhap is null/empty.
     */
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
                        rs.getString("tenTK"), // Map tenTK to tenDangNhap
                        rs.getString("matKhau")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm tài khoản: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Retrieves all accounts from the database.
     *
     * @return A List of TaiKhoan objects.
     */
    public List<TaiKhoan> layTatCaTaiKhoan() {
        List<TaiKhoan> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(new TaiKhoan(
                        rs.getString("tenTK"), // Map tenTK to tenDangNhap
                        rs.getString("matKhau")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách tài khoản: " + e.getMessage(), e);
        }
        return ds;
    }

    /**
     * Authenticates a user by checking tenDangNhap and matKhau.
     *
     * @param tenDangNhap The username to authenticate.
     * @param matKhau The password to authenticate.
     * @return The TaiKhoan object if authentication is successful, null otherwise.
     * @throws IllegalArgumentException if tenDangNhap or matKhau is null/empty.
     */
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        // Validate input
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

    /**
     * Checks if the given username belongs to an admin account.
     *
     * @param tenDangNhap The username to check.
     * @return true if the user is an admin, false otherwise.
     * @throws IllegalArgumentException if tenDangNhap is null/empty.
     */
    public boolean isAdmin(String tenDangNhap) {
        // Validate input
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        }

        // Based on the table, "ADMIN" is the admin account
        return "ADMIN".equalsIgnoreCase(tenDangNhap);
    }

    /**
     * Validates a TaiKhoan object to ensure tenDangNhap and matKhau are not null or empty.
     *
     * @param tk The TaiKhoan object to validate.
     * @throws IllegalArgumentException if validation fails.
     */
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
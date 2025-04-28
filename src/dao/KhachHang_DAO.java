package dao;

import connectDB.ConnectDB;
import entity.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHang_DAO {

    public boolean themKhachHang(KhachHang khachHang) {
        String sql = "INSERT INTO KhachHang (maKH, tenKH) VALUES (?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, khachHang.getMaKhachHang());
            stmt.setString(2, khachHang.getTenKH());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm khách hàng: " + e.getMessage(), e);
        }
    }

    public List<KhachHang> layTatCaKhachHang() {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                ds.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách khách hàng: " + e.getMessage(), e);
        }
        return ds;
    }

    public String generateNextMaKhachHang() {
        try {
            List<KhachHang> khachHangList = layTatCaKhachHang();
            int maxNumber = 0;
            for (KhachHang kh : khachHangList) {
                String maKH = kh.getMaKhachHang();
                if (maKH != null && maKH.startsWith("KH")) {
                    try {
                        int number = Integer.parseInt(maKH.substring(2));
                        maxNumber = Math.max(maxNumber, number);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return String.format("KH%02d", maxNumber + 1); // e.g., KH51
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo mã khách hàng: " + e.getMessage(), e);
        }
    }
}
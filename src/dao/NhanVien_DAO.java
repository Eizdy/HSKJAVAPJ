package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_DAO {
    private Connection conn;

    public NhanVien_DAO() {
        // Kết nối với cơ sở dữ liệu
        ConnectDB.getInstance().connect(); // Đảm bảo kết nối trước khi sử dụng
        this.conn = ConnectDB.getConnection();
        
        // Kiểm tra kết nối có thành công không
        if (this.conn == null) {
            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM nhanvien";  // Cập nhật câu lệnh SQL nếu cần thiết
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNhanVien(rs.getString("maNV"));
                    nv.setHoTen(rs.getString("hoTen"));
                    nv.setSoDienThoai(rs.getString("sdt"));
                    nv.setEmail(rs.getString("email"));
                    nhanVienList.add(nv);
                }
            } else {
                System.out.println("Không có kết nối đến cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public boolean themNhanVien(NhanVien nv) {
        try {
            if (conn != null) {
                String query = "INSERT INTO nhanvien (maNV, hoTen, sdt, email) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, nv.getMaNhanVien());
                ps.setString(2, nv.getHoTen());
                ps.setString(3, nv.getSoDienThoai());
                ps.setString(4, nv.getEmail());

                int result = ps.executeUpdate();
                return result > 0;
            } else {
                System.out.println("Không có kết nối đến cơ sở dữ liệu.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        try {
            if (conn != null) {
                String query = "UPDATE nhanvien SET hoTen = ?, sdt = ?, email = ? WHERE maNV = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, nv.getHoTen());
                ps.setString(2, nv.getSoDienThoai());
                ps.setString(3, nv.getEmail());
                ps.setString(4, nv.getMaNhanVien());

                int result = ps.executeUpdate();
                return result > 0;
            } else {
                System.out.println("Không có kết nối đến cơ sở dữ liệu.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVien(String maNV) {
        try {
            if (conn != null) {
                String query = "DELETE FROM nhanvien WHERE maNV = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, maNV);

                int result = ps.executeUpdate();
                return result > 0;
            } else {
                System.out.println("Không có kết nối đến cơ sở dữ liệu.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        ConnectDB.disconnect();
    }
}

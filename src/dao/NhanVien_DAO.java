package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class NhanVien_DAO {
    private Connection conn;

<<<<<<< HEAD
    public NhanVien_DAO() {
        // Kết nối với cơ sở dữ liệu
        ConnectDB.getInstance().connect(); // Đảm bảo kết nối trước khi sử dụng
        this.conn = ConnectDB.getConnection();
        
        // Kiểm tra kết nối có thành công không
        if (this.conn == null) {
            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
        }
=======
    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, soDienThoai, email, tenDangNhap, chucVu, ngaySinh) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getSoDienThoai());
            stmt.setString(4, nv.getEmail());
            stmt.setString(5, nv.getTaiKhoan().getTenDangNhap());
            stmt.setString(6, nv.getChucVu());
            stmt.setDate(7, Date.valueOf(nv.getNgaySinh()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, soDienThoai = ?, email = ?, tenDangNhap = ?, chucVu = ?, ngaySinh = ? WHERE maNhanVien = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getSoDienThoai());
            stmt.setString(3, nv.getEmail());
            stmt.setString(4, nv.getTaiKhoan().getTenDangNhap());
            stmt.setString(5, nv.getChucVu());
            stmt.setDate(6, Date.valueOf(nv.getNgaySinh()));
            stmt.setString(7, nv.getMaNhanVien());

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
                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));

                return new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        tk,
                        rs.getString("chucVu"),
                        rs.getDate("ngaySinh").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
>>>>>>> 2693e92dbe9f3994a861eb7980a1a1337f18671f
    }

    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM nhanvien";  // Cập nhật câu lệnh SQL nếu cần thiết
                ResultSet rs = stmt.executeQuery(query);

<<<<<<< HEAD
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
=======
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap"));

                ds.add(new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        tk,
                        rs.getString("chucVu"),
                        rs.getDate("ngaySinh").toLocalDate()
                ));
>>>>>>> 2693e92dbe9f3994a861eb7980a1a1337f18671f
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

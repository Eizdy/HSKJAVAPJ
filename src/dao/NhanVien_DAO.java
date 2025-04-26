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
        ConnectDB.getInstance().connect();
        this.conn = ConnectDB.getConnection();

        if (this.conn == null) {
            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

//    public boolean themNhanVien(NhanVien nv) {
//        String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, soDienThoai, email, tenDangNhap, chucVu, ngaySinh) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, nv.getMaNhanVien());
//            stmt.setString(2, nv.getHoTen());
//            stmt.setString(3, nv.getSoDienThoai());
//            stmt.setString(4, nv.getEmail());
//            stmt.setString(5, nv.getTaiKhoan().getTenDangNhap());
//            stmt.setString(6, nv.getChucVu());
//            stmt.setDate(7, Date.valueOf(nv.getNgaySinh()));
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    
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
            stmt.setDate(7, java.sql.Date.valueOf(nv.getNgaySinh()));

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, soDienThoai = ?, email = ?, tenDangNhap = ?, chucVu = ?, ngaySinh = ? WHERE maNhanVien = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NhanVien timNhanVienTheoMa(String maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
    }

    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public static void disconnect() {
        ConnectDB.disconnect();
    }
}

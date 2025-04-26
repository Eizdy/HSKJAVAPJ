package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class NhanVien_DAO{

	public boolean themNhanVien(NhanVien nv) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "INSERT INTO NhanVien (maNV, tenNV, ngaySinh, dienThoai, email, chucVu, tenTK) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.prepareStatement(sql);

	        stmt.setString(1, nv.getMaNV());
	        stmt.setString(2, nv.getTenNV());
	        stmt.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));
	        stmt.setString(4, nv.getDienThoai());
	        stmt.setString(5, nv.getEmail());
	        stmt.setString(6, nv.getChucVu());
	        stmt.setString(7, nv.getTenTK().getTenDangNhap());

	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}


	public boolean capNhatNhanVien(NhanVien nv) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "UPDATE NhanVien SET tenNV = ?, ngaySinh = ?, dienThoai = ?, email = ?, chucVu = ?, tenTK = ? WHERE maNV = ?";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.prepareStatement(sql);

	        stmt.setString(1, nv.getTenNV());
	        stmt.setDate(2, java.sql.Date.valueOf(nv.getNgaySinh()));
	        stmt.setString(3, nv.getDienThoai());
	        stmt.setString(4, nv.getEmail());
	        stmt.setString(5, nv.getChucVu());
	        stmt.setString(6, nv.getTenTK().getTenDangNhap());
	        stmt.setString(7, nv.getMaNV());

	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}


	public boolean xoaNhanVien(String maNV) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "DELETE FROM NhanVien WHERE maNV = ?";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.prepareStatement(sql);

	        stmt.setString(1, maNV);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}


	public NhanVien timNhanVienTheoMa(String maNV) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT maNV, tenNV, ngaySinh, dienThoai, email, chucVu, tenTK FROM NhanVien WHERE maNV = ?";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, maNV);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            TaiKhoan taiKhoan = new TaiKhoan();
	            taiKhoan.setTenDangNhap(rs.getString("tenTK"));

	            return new NhanVien(
	                rs.getString("maNV"),
	                rs.getString("tenNV"),
	                rs.getDate("ngaySinh").toLocalDate(),
	                rs.getString("dienThoai"),
	                rs.getString("email"),
	                rs.getString("chucVu"),
	                taiKhoan
	            );
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
	    return null;
	}

	public List<NhanVien> layTatCaNhanVien() {
	    List<NhanVien> ds = new ArrayList<>();
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT maNV, tenNV, ngaySinh, dienThoai, email, chucVu, tenTK FROM NhanVien";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            TaiKhoan taiKhoan = new TaiKhoan();
	            taiKhoan.setTenDangNhap(rs.getString("tenTK"));

	            ds.add(new NhanVien(
	                    rs.getString("maNV"),
	                    rs.getString("tenNV"),
	                    rs.getDate("ngaySinh").toLocalDate(),
	                    rs.getString("dienThoai"),
	                    rs.getString("email"),
	                    rs.getString("chucVu"),
	                    taiKhoan
	            ));
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
	    return ds;
	}


	public List<NhanVien> timNhanVienTheoTen(String tenNV) {
	    List<NhanVien> ds = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT maNV, tenNV, ngaySinh, dienThoai, email, chucVu, tenTK FROM NhanVien WHERE tenNV LIKE ?";
	    try {
	        conn = ConnectDB.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, "%" + tenNV + "%");
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            TaiKhoan taiKhoan = new TaiKhoan();
	            taiKhoan.setTenDangNhap(rs.getString("tenTK"));

	            ds.add(new NhanVien(
	                    rs.getString("maNV"),
	                    rs.getString("tenNV"),
	                    rs.getDate("ngaySinh").toLocalDate(),
	                    rs.getString("dienThoai"),
	                    rs.getString("email"),
	                    rs.getString("chucVu"),
	                    taiKhoan
	            ));
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
	    return ds;
	}

}
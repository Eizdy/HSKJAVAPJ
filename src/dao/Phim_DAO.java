package dao;

import connectDB.ConnectDB;
import entity.LoaiPhim;
import entity.Phim;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Phim_DAO {

    public boolean themPhim(Phim phim) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Phim (maPhim, tenPhim, theLoai, thoiLuong, daoDien, ngayKhoiChieu, moTa, ngonNgu, doTuoiGioiHan, nuocSX) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, phim.getMaPhim());
            stmt.setString(2, phim.getTenPhim());
            stmt.setString(3, phim.getTheLoai().getMaLoai());
            stmt.setInt(4, phim.getThoiLuong());
            stmt.setString(5, phim.getDaoDien());
            stmt.setDate(6, Date.valueOf(phim.getNgayKhoiChieu()));
            stmt.setString(7, phim.getMoTa());
            stmt.setString(8, phim.getNgonNgu());
            stmt.setInt(9, phim.getDoTuoiGioiHan());
            stmt.setString(10, phim.getNuocSX());

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

    public boolean capNhatPhim(Phim phim) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "UPDATE Phim SET tenPhim = ?, theLoai = ?, thoiLuong = ?, daoDien = ?, ngayKhoiChieu = ?, moTa = ?, ngonNgu = ?, doTuoiGioiHan = ?, nuocSX = ? WHERE maPhim = ?";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, phim.getTenPhim());
            stmt.setString(2, phim.getTheLoai().getMaLoai());
            stmt.setInt(3, phim.getThoiLuong());
            stmt.setString(4, phim.getDaoDien());
            stmt.setDate(5, Date.valueOf(phim.getNgayKhoiChieu()));
            stmt.setString(6, phim.getMoTa());
            stmt.setString(7, phim.getNgonNgu());
            stmt.setInt(8, phim.getDoTuoiGioiHan());
            stmt.setString(9, phim.getNuocSX());
            stmt.setString(10, phim.getMaPhim());

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

    public boolean xoaPhim(String maPhim) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "DELETE FROM Phim WHERE maPhim = ?";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, maPhim);
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

    public Phim timPhimTheoMa(String maPhim) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT p.maPhim, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, l.maLoai, l.tenLoai " +
                     "FROM Phim p JOIN LoaiPhim l ON p.theLoai = l.maLoai WHERE p.maPhim = ?";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maPhim);
            rs = stmt.executeQuery();

            if (rs.next()) {
                LoaiPhim loai = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));
                return new Phim(
                        rs.getString("maPhim"),
                        rs.getString("tenPhim"),
                        loai,
                        rs.getInt("thoiLuong"),
                        rs.getString("daoDien"),
                        rs.getDate("ngayKhoiChieu").toLocalDate(),
                        rs.getString("moTa"),
                        rs.getString("ngonNgu"),
                        rs.getInt("doTuoiGioiHan"),
                        rs.getString("nuocSX")
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

    public List<Phim> layTatCaPhim() {
        List<Phim> ds = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT p.maPhim, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, l.maLoai, l.tenLoai " +
                     "FROM Phim p JOIN LoaiPhim l ON p.theLoai = l.maLoai";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                LoaiPhim loai = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));
                ds.add(new Phim(
                        rs.getString("maPhim"),
                        rs.getString("tenPhim"),
                        loai,
                        rs.getInt("thoiLuong"),
                        rs.getString("daoDien"),
                        rs.getDate("ngayKhoiChieu").toLocalDate(),
                        rs.getString("moTa"),
                        rs.getString("ngonNgu"),
                        rs.getInt("doTuoiGioiHan"),
                        rs.getString("nuocSX")
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

    public List<Phim> timPhimTheoTen(String tenPhim) {
        List<Phim> ds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT p.maPhim, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, l.maLoai, l.tenLoai " +
                     "FROM Phim p JOIN LoaiPhim l ON p.theLoai = l.maLoai WHERE tenPhim LIKE ?";
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + tenPhim + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                LoaiPhim loai = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));
                ds.add(new Phim(
                        rs.getString("maPhim"),
                        rs.getString("tenPhim"),
                        loai,
                        rs.getInt("thoiLuong"),
                        rs.getString("daoDien"),
                        rs.getDate("ngayKhoiChieu").toLocalDate(),
                        rs.getString("moTa"),
                        rs.getString("ngonNgu"),
                        rs.getInt("doTuoiGioiHan"),
                        rs.getString("nuocSX")
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
package dao;

import connectDB.ConnectDB;
import entity.Phim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Phim_DAO {

    public boolean themPhim(Phim phim) {
        String sql = "INSERT INTO Phim (maPhim, tenPhim, theLoai, thoiLuong, daoDien, dienVien, ngayKhoiChieu, moTa, ngonNgu, doTuoiGioiHan, hinhAnh, giaVe) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phim.getMaPhim());
            stmt.setString(2, phim.getTenPhim());
            stmt.setString(3, phim.getTheLoai());
            stmt.setInt(4, phim.getThoiLuong());
            stmt.setString(5, phim.getDaoDien());
            stmt.setString(6, phim.getDienVien());
            stmt.setDate(7, Date.valueOf(phim.getNgayKhoiChieu()));
            stmt.setString(8, phim.getMoTa());
            stmt.setString(9, phim.getNgonNgu());
            stmt.setInt(10, phim.getDoTuoiGioiHan());
            stmt.setString(11, phim.getHinhAnh());
            stmt.setDouble(12, phim.getGiaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatPhim(Phim phim) {
        String sql = "UPDATE Phim SET tenPhim=?, theLoai=?, thoiLuong=?, daoDien=?, dienVien=?, ngayKhoiChieu=?, moTa=?, ngonNgu=?, doTuoiGioiHan=?, hinhAnh=?, giaVe=? WHERE maPhim=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phim.getTenPhim());
            stmt.setString(2, phim.getTheLoai());
            stmt.setInt(3, phim.getThoiLuong());
            stmt.setString(4, phim.getDaoDien());
            stmt.setString(5, phim.getDienVien());
            stmt.setDate(6, Date.valueOf(phim.getNgayKhoiChieu()));
            stmt.setString(7, phim.getMoTa());
            stmt.setString(8, phim.getNgonNgu());
            stmt.setInt(9, phim.getDoTuoiGioiHan());
            stmt.setString(10, phim.getHinhAnh());
            stmt.setDouble(11, phim.getGiaVe());
            stmt.setString(12, phim.getMaPhim());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaPhim(String maPhim) {
        String sql = "DELETE FROM Phim WHERE maPhim=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhim);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Phim timPhimTheoMa(String maPhim) {
        String sql = "SELECT * FROM Phim WHERE maPhim=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPhim(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Phim> layTatCaPhim() {
        List<Phim> ds = new ArrayList<>();
        String sql = "SELECT * FROM Phim";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ds.add(mapPhim(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    private Phim mapPhim(ResultSet rs) throws SQLException {
        Phim p = new Phim();
        p.setMaPhim(rs.getString("maPhim"));
        p.setTenPhim(rs.getString("tenPhim"));
        p.setTheLoai(rs.getString("theLoai"));
        p.setThoiLuong(rs.getInt("thoiLuong"));
        p.setDaoDien(rs.getString("daoDien"));
        p.setDienVien(rs.getString("dienVien"));
        p.setNgayKhoiChieu(rs.getDate("ngayKhoiChieu").toLocalDate());
        p.setMoTa(rs.getString("moTa"));
        p.setNgonNgu(rs.getString("ngonNgu"));
        p.setDoTuoiGioiHan(rs.getInt("doTuoiGioiHan"));
        p.setHinhAnh(rs.getString("hinhAnh"));
        p.setGiaVe(rs.getDouble("giaVe"));
        return p;
    }
}

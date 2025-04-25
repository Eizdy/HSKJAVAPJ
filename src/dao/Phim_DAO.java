package dao;

import connectDB.ConnectDB;
import entity.LoaiPhim;
import entity.Phim;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Phim_DAO {

    public boolean themPhim(Phim phim) {
        String sql = "INSERT INTO Phim (maPhim, tenPhim, maLoai, thoiLuong, daoDien, ngayKhoiChieu, moTa, ngonNgu, doTuoiGioiHan, nuocSX) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        }
        return false;
    }

    public boolean capNhatPhim(Phim phim) {
        String sql = "UPDATE Phim SET tenPhim = ?, maLoai = ?, thoiLuong = ?, daoDien = ?, ngayKhoiChieu = ?, moTa = ?, ngonNgu = ?, doTuoiGioiHan = ?, nuocSX = ? WHERE maPhim = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        }
        return false;
    }

    public boolean xoaPhim(String maPhim) {
        String sql = "DELETE FROM Phim WHERE maPhim = ?";
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
        String sql = "SELECT * FROM Phim WHERE maPhim = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LoaiPhim loai = new LoaiPhim();
                loai.setMaLoai(rs.getString("maLoai"));

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
                LoaiPhim loai = new LoaiPhim();
                loai.setMaLoai(rs.getString("maLoai"));

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
        }
        return ds;
    }
}

package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.VeXemPhim;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class HoaDon_DAO {

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (maHoaDon, ngayLap, soLuong, maVe) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hd.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hd.getNgayLap()));
            stmt.setInt(3, hd.getSoLuong());
            stmt.setString(4, hd.getVe().getMaVe());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET ngayLap = ?, soLuong = ?, maVe = ? WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(hd.getNgayLap()));
            stmt.setInt(2, hd.getSoLuong());
            stmt.setString(3, hd.getVe().getMaVe());
            stmt.setString(4, hd.getMaHoaDon());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaHoaDon(String maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHoaDon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon timHoaDonTheoMa(String maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));

                return new HoaDon(
                        rs.getString("maHoaDon"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHoaDon"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

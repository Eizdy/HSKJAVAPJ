package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.VeXemPhim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDon_DAO {

    // CREATE
    public boolean themChiTiet(ChiTietHoaDon cthd) {
        String sql = "INSERT INTO ChiTietHoaDon (maChiTiet, maVe, giaVe) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cthd.getMaChiTiet());
            stmt.setString(2, cthd.getVe().getMaVe());
            stmt.setDouble(3, cthd.getGiaVe());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE
    public boolean capNhatChiTiet(ChiTietHoaDon cthd) {
        String sql = "UPDATE ChiTietHoaDon SET maVe = ?, giaVe = ? WHERE maChiTiet = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cthd.getVe().getMaVe());
            stmt.setDouble(2, cthd.getGiaVe());
            stmt.setString(3, cthd.getMaChiTiet());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean xoaChiTiet(String maChiTiet) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE maChiTiet = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maChiTiet);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ ONE
    public ChiTietHoaDon timChiTietTheoMa(String maChiTiet) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE maChiTiet = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maChiTiet);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaChiTiet(rs.getString("maChiTiet"));

                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                cthd.setVe(ve);

                cthd.setGiaVe(rs.getDouble("giaVe"));
                return cthd;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ ALL
    public List<ChiTietHoaDon> layTatCaChiTiet() {
        List<ChiTietHoaDon> ds = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaChiTiet(rs.getString("maChiTiet"));

                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                cthd.setVe(ve);

                cthd.setGiaVe(rs.getDouble("giaVe"));
                ds.add(cthd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

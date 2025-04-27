package dao;

import connectDB.ConnectDB;
import entity.LichChieuPhim;
import entity.Phim;
import entity.PhongChieuPhim;
import entity.LoaiPhim;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class LichChieuPhim_DAO {

    public boolean themLichChieu(LichChieuPhim lc) throws SQLException {
        String sql = "INSERT INTO LichChieuPhim (maLichChieu, maPhim, maPhong, thoiGianChieu, trangThai, ngayChieu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getMaLichChieu());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setString(3, lc.getPhong().getMaPhong());
            stmt.setString(4, lc.getThoiGianChieu());
            stmt.setString(5, lc.getTrangThai());
            stmt.setDate(6, Date.valueOf(lc.getNgayChieu()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm lịch chiếu: " + e.getMessage(), e);
        }
    }

    public boolean capNhatLichChieu(LichChieuPhim lc) throws SQLException {
        String sql = "UPDATE LichChieuPhim SET maPhim = ?, maPhong = ?, thoiGianChieu = ?, trangThai = ?, ngayChieu = ? WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhim().getMaPhim());
            stmt.setString(2, lc.getPhong().getMaPhong());
            stmt.setString(3, lc.getThoiGianChieu());
            stmt.setString(4, lc.getTrangThai());
            stmt.setDate(5, Date.valueOf(lc.getNgayChieu()));
            stmt.setString(6, lc.getMaLichChieu());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật lịch chiếu: " + e.getMessage(), e);
        }
    }

    public boolean xoaLichChieu(String maLichChieu) throws SQLException {
        String sql = "DELETE FROM LichChieuPhim WHERE maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi xóa lịch chiếu: " + e.getMessage(), e);
        }
    }

    public LichChieuPhim timLichChieuTheoMa(String maLichChieu) throws SQLException {
        String sql = "SELECT lc.*, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, " +
                     "lp.maLoai, lp.tenLoai, pc.tenPhong " +
                     "FROM LichChieuPhim lc " +
                     "JOIN Phim p ON lc.maPhim = p.maPhim " +
                     "JOIN LoaiPhim lp ON p.theLoai = lp.maLoai " +
                     "JOIN PhongChieuPhim pc ON lc.maPhong = pc.maPhong " +
                     "WHERE lc.maLichChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Populate LoaiPhim
                    LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));

                    // Populate Phim
                    Phim phim = new Phim();
                    phim.setMaPhim(rs.getString("maPhim"));
                    phim.setTenPhim(rs.getString("tenPhim"));
                    phim.setTheLoai(loaiPhim);
                    phim.setThoiLuong(rs.getInt("thoiLuong"));
                    phim.setDaoDien(rs.getString("daoDien"));
                    phim.setNgayKhoiChieu(rs.getDate("ngayKhoiChieu") != null ? rs.getDate("ngayKhoiChieu").toLocalDate() : null);
                    phim.setMoTa(rs.getString("moTa"));
                    phim.setNgonNgu(rs.getString("ngonNgu"));
                    phim.setDoTuoiGioiHan(rs.getInt("doTuoiGioiHan"));
                    phim.setNuocSX(rs.getString("nuocSX"));

                    // Populate PhongChieuPhim
                    PhongChieuPhim phong = new PhongChieuPhim();
                    phong.setMaPhong(rs.getString("maPhong"));
                    phong.setTenPhong(rs.getString("tenPhong"));

                    // Create and return LichChieuPhim
                    return new LichChieuPhim(
                            rs.getString("maLichChieu"),
                            phim,
                            phong,
                            rs.getString("thoiGianChieu"),
                            rs.getString("trangThai"),
                            rs.getDate("ngayChieu") != null ? rs.getDate("ngayChieu").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi tìm lịch chiếu theo mã: " + e.getMessage(), e);
        }
        return null;
    }

    public List<LichChieuPhim> layTatCaLichChieu() throws SQLException {
        List<LichChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT lc.*, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, " +
                     "lp.maLoai, lp.tenLoai, pc.tenPhong " +
                     "FROM LichChieuPhim lc " +
                     "JOIN Phim p ON lc.maPhim = p.maPhim " +
                     "JOIN LoaiPhim lp ON p.theLoai = lp.maLoai " +
                     "JOIN PhongChieuPhim pc ON lc.maPhong = pc.maPhong";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Populate LoaiPhim
                LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));

                // Populate Phim
                Phim phim = new Phim();
                phim.setMaPhim(rs.getString("maPhim"));
                phim.setTenPhim(rs.getString("tenPhim"));
                phim.setTheLoai(loaiPhim);
                phim.setThoiLuong(rs.getInt("thoiLuong"));
                phim.setDaoDien(rs.getString("daoDien"));
                phim.setNgayKhoiChieu(rs.getDate("ngayKhoiChieu") != null ? rs.getDate("ngayKhoiChieu").toLocalDate() : null);
                phim.setMoTa(rs.getString("moTa"));
                phim.setNgonNgu(rs.getString("ngonNgu"));
                phim.setDoTuoiGioiHan(rs.getInt("doTuoiGioiHan"));
                phim.setNuocSX(rs.getString("nuocSX"));

                // Populate PhongChieuPhim
                PhongChieuPhim phong = new PhongChieuPhim();
                phong.setMaPhong(rs.getString("maPhong"));
                phong.setTenPhong(rs.getString("tenPhong"));

                // Create LichChieuPhim and add to list
                LichChieuPhim lc = new LichChieuPhim(
                        rs.getString("maLichChieu"),
                        phim,
                        phong,
                        rs.getString("thoiGianChieu"),
                        rs.getString("trangThai"),
                        rs.getDate("ngayChieu") != null ? rs.getDate("ngayChieu").toLocalDate() : null
                );
                ds.add(lc);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy tất cả lịch chiếu: " + e.getMessage(), e);
        }
        return ds;
    }

    public List<LichChieuPhim> layLichChieuTheoNgay(LocalDate ngay) throws SQLException {
        List<LichChieuPhim> ds = new ArrayList<>();
        String sql = "SELECT lc.*, p.tenPhim, p.thoiLuong, p.daoDien, p.ngayKhoiChieu, p.moTa, p.ngonNgu, p.doTuoiGioiHan, p.nuocSX, " +
                     "lp.maLoai, lp.tenLoai, pc.tenPhong " +
                     "FROM LichChieuPhim lc " +
                     "JOIN Phim p ON lc.maPhim = p.maPhim " +
                     "JOIN LoaiPhim lp ON p.theLoai = lp.maLoai " +
                     "JOIN PhongChieuPhim pc ON lc.maPhong = pc.maPhong " +
                     "WHERE lc.ngayChieu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(ngay));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Populate LoaiPhim
                    LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai"));

                    // Populate Phim
                    Phim phim = new Phim();
                    phim.setMaPhim(rs.getString("maPhim"));
                    phim.setTenPhim(rs.getString("tenPhim"));
                    phim.setTheLoai(loaiPhim);
                    phim.setThoiLuong(rs.getInt("thoiLuong"));
                    phim.setDaoDien(rs.getString("daoDien"));
                    phim.setNgayKhoiChieu(rs.getDate("ngayKhoiChieu") != null ? rs.getDate("ngayKhoiChieu").toLocalDate() : null);
                    phim.setMoTa(rs.getString("moTa"));
                    phim.setNgonNgu(rs.getString("ngonNgu"));
                    phim.setDoTuoiGioiHan(rs.getInt("doTuoiGioiHan"));
                    phim.setNuocSX(rs.getString("nuocSX"));

                    // Populate PhongChieuPhim
                    PhongChieuPhim phong = new PhongChieuPhim();
                    phong.setMaPhong(rs.getString("maPhong"));
                    phong.setTenPhong(rs.getString("tenPhong"));

                    // Create LichChieuPhim and add to list
                    LichChieuPhim lich = new LichChieuPhim(
                            rs.getString("maLichChieu"),
                            phim,
                            phong,
                            rs.getString("thoiGianChieu"),
                            rs.getString("trangThai"),
                            rs.getDate("ngayChieu") != null ? rs.getDate("ngayChieu").toLocalDate() : null
                    );
                    ds.add(lich);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy lịch chiếu theo ngày: " + e.getMessage(), e);
        }
        return ds;
    }

    public Map<String, Object> layThongTinHoaDonTheoMaLichChieu(String maLichChieu) throws SQLException {
        String sql = "SELECT p.tenPhim, lp.tenLoai, lc.thoiGianChieu, lc.ngayChieu, p.thoiLuong, pc.tenPhong " +
                     "FROM LichChieuPhim lc " +
                     "JOIN Phim p ON lc.maPhim = p.maPhim " +
                     "JOIN LoaiPhim lp ON p.theLoai = lp.maLoai " +
                     "JOIN PhongChieuPhim pc ON lc.maPhong = pc.maPhong " +
                     "WHERE lc.maLichChieu = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maLichChieu);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> invoiceDetails = new HashMap<>();
                    invoiceDetails.put("tenPhim", rs.getString("tenPhim"));
                    invoiceDetails.put("theLoai", rs.getString("tenLoai"));
                    invoiceDetails.put("thoiGianChieu", rs.getString("thoiGianChieu"));
                    invoiceDetails.put("ngayChieu", rs.getDate("ngayChieu") != null ? rs.getDate("ngayChieu").toLocalDate() : null);
                    invoiceDetails.put("thoiLuong", rs.getInt("thoiLuong"));
                    invoiceDetails.put("tenPhong", rs.getString("tenPhong"));
                    return invoiceDetails;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy thông tin hóa đơn theo mã lịch chiếu: " + e.getMessage(), e);
        }
        return null;
    }
}
package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.NhanVien;
import entity.VeXemPhim;
import entity.LichChieuPhim;
import entity.KhachHang;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Data Access Object (DAO) for managing HoaDon entities in the database.
 * Provides methods to retrieve and search for invoices.
 */
public class HoaDon_DAO {

    /**
     * Retrieves an invoice from the database based on its ID (maHD).
     *
     * @param maHD The ID of the invoice to find.
     * @return The HoaDon object if found, null otherwise.
     * @throws IllegalArgumentException if maHD is null or empty.
     */
    public HoaDon timHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }

        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe " +
                     "WHERE hd.maHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNV"));

                return new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm hóa đơn: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Retrieves all invoices from the database.
     *
     * @return A List of HoaDon objects.
     */
    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage(), e);
        }
        return ds;
    }

    /**
     * Retrieves invoices from the database based on the date of creation (ngayLap).
     *
     * @param ngayLap The date to filter invoices by (format: YYYY-MM-DD).
     * @return A List of HoaDon objects matching the specified date.
     * @throws IllegalArgumentException if ngayLap is null, empty, or not in the correct format.
     */
    public List<HoaDon> getHoaDonTheoNgay(String ngayLap) {
        if (ngayLap == null || ngayLap.trim().isEmpty()) {
            throw new IllegalArgumentException("Ngày lập không được để trống.");
        }

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(ngayLap);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ngày lập không đúng định dạng (YYYY-MM-DD): " + ngayLap);
        }

        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.*, v.giaVe, v.maLichChieu, v.maKH " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN VeXemPhim v ON hd.maVe = v.maVe " +
                     "WHERE hd.ngayLap = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(parsedDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));

                LichChieuPhim lichChieu = new LichChieuPhim();
                lichChieu.setMaLichChieu(rs.getString("maLichChieu"));
                ve.setLichChieu(lichChieu);

                String maKH = rs.getString("maKH");
                if (maKH != null) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setMaKhachHang(maKH);
                    ve.setKhachHang(khachHang);
                }

                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNV"));

                HoaDon hd = new HoaDon(
                        rs.getString("maHD"),
                        rs.getDate("ngayLap").toLocalDate(),
                        rs.getInt("soLuong"),
                        ve,
                        nv
                );
                ds.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy hóa đơn theo ngày: " + e.getMessage(), e);
        }
        return ds;
    }
}
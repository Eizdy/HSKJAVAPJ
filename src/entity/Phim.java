package entity;

import java.time.LocalDate;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private String theLoai;
    private int thoiLuong; // phút
    private String daoDien;
    private String dienVien;
    private LocalDate ngayKhoiChieu;
    private String moTa;
    private String ngonNgu;
    private int doTuoiGioiHan;
    private String hinhAnh;
    private double giaVe; // << Thêm thuộc tính giá vé

    public Phim() {
    }

    public Phim(String maPhim, String tenPhim, String theLoai, int thoiLuong,
                String daoDien, String dienVien, LocalDate ngayKhoiChieu,
                String moTa, String ngonNgu, int doTuoiGioiHan,
                String hinhAnh, double giaVe) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.thoiLuong = thoiLuong;
        this.daoDien = daoDien;
        this.dienVien = dienVien;
        this.ngayKhoiChieu = ngayKhoiChieu;
        this.moTa = moTa;
        this.ngonNgu = ngonNgu;
        this.doTuoiGioiHan = doTuoiGioiHan;
        this.hinhAnh = hinhAnh;
        this.giaVe = giaVe;
    }

    // Getter và Setter

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }

    public LocalDate getNgayKhoiChieu() {
        return ngayKhoiChieu;
    }

    public void setNgayKhoiChieu(LocalDate ngayKhoiChieu) {
        this.ngayKhoiChieu = ngayKhoiChieu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public int getDoTuoiGioiHan() {
        return doTuoiGioiHan;
    }

    public void setDoTuoiGioiHan(int doTuoiGioiHan) {
        this.doTuoiGioiHan = doTuoiGioiHan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    // === Hai phương thức yêu cầu ===
    public String getPoster() {
        return hinhAnh;
    }

    @Override
    public String toString() {
        return "Phim{" +
                "maPhim='" + maPhim + '\'' +
                ", tenPhim='" + tenPhim + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", thoiLuong=" + thoiLuong +
                ", daoDien='" + daoDien + '\'' +
                ", dienVien='" + dienVien + '\'' +
                ", ngayKhoiChieu=" + ngayKhoiChieu +
                ", moTa='" + moTa + '\'' +
                ", ngonNgu='" + ngonNgu + '\'' +
                ", doTuoiGioiHan=" + doTuoiGioiHan +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", giaVe=" + giaVe +
                '}';
    }
}

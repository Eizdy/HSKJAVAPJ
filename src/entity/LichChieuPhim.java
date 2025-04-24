package entity;

import java.time.LocalDateTime;

public class LichChieuPhim {
    private String maLichChieu;
    private Phim phim;
    private PhongChieuPhim phong;
    private LocalDateTime thoiGianChieu;
    private double giaVe;

    public LichChieuPhim() {
    }

    public LichChieuPhim(String maLichChieu, Phim phim, PhongChieuPhim phong,
                         LocalDateTime thoiGianChieu, double giaVe) {
        this.maLichChieu = maLichChieu;
        this.phim = phim;
        this.phong = phong;
        this.thoiGianChieu = thoiGianChieu;
        this.giaVe = giaVe;
    }

    public String getMaLichChieu() {
        return maLichChieu;
    }

    public void setMaLichChieu(String maLichChieu) {
        this.maLichChieu = maLichChieu;
    }

    public Phim getPhim() {
        return phim;
    }

    public void setPhim(Phim phim) {
        this.phim = phim;
    }

    public PhongChieuPhim getPhong() {
        return phong;
    }

    public void setPhong(PhongChieuPhim phong) {
        this.phong = phong;
    }

    public LocalDateTime getThoiGianChieu() {
        return thoiGianChieu;
    }

    public void setThoiGianChieu(LocalDateTime thoiGianChieu) {
        this.thoiGianChieu = thoiGianChieu;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    @Override
    public String toString() {
        return "Lịch chiếu: " + phim.getTenPhim() + " tại " + phong.getTenPhong()
                + " vào " + thoiGianChieu + " - Giá: " + giaVe + " VND";
    }
}

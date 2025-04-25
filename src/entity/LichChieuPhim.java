package entity;

import java.time.LocalDate;

public class LichChieuPhim {
    private String maLichChieu;
    private Phim phim;
    private PhongChieuPhim phong;
    private String thoiGianChieu;
    private String trangThai;
    private LocalDate ngayChieu;
	public LichChieuPhim(String maLichChieu, Phim phim, PhongChieuPhim phong, String thoiGianChieu, String trangThai,
			LocalDate ngayChieu) {
		super();
		this.maLichChieu = maLichChieu;
		this.phim = phim;
		this.phong = phong;
		this.thoiGianChieu = thoiGianChieu;
		this.trangThai = trangThai;
		this.ngayChieu = ngayChieu;
	}
	public LichChieuPhim() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getThoiGianChieu() {
		return thoiGianChieu;
	}
	public void setThoiGianChieu(String thoiGianChieu) {
		this.thoiGianChieu = thoiGianChieu;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public LocalDate getNgayChieu() {
		return ngayChieu;
	}
	public void setNgayChieu(LocalDate ngayChieu) {
		this.ngayChieu = ngayChieu;
	}
	
}

package entity;

import java.time.LocalDate;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private LoaiPhim theLoai;
    private int thoiLuong; // phút
    private String daoDien;
    private LocalDate ngayKhoiChieu;
    private String moTa;
    private String ngonNgu;
    private int doTuoiGioiHan;
    private String nuocSX;
	public Phim(String maPhim, String tenPhim, LoaiPhim theLoai, int thoiLuong, String daoDien, LocalDate ngayKhoiChieu,
			String moTa, String ngonNgu, int doTuoiGioiHan, String nuocSX) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.theLoai = theLoai;
		this.thoiLuong = thoiLuong;
		this.daoDien = daoDien;
		this.ngayKhoiChieu = ngayKhoiChieu;
		this.moTa = moTa;
		this.ngonNgu = ngonNgu;
		this.doTuoiGioiHan = doTuoiGioiHan;
		this.nuocSX = nuocSX;
	}
	public Phim() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public LoaiPhim getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(LoaiPhim theLoai) {
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
	public String getNuocSX() {
		return nuocSX;
	}
	public void setNuocSX(String nuocSX) {
		this.nuocSX = nuocSX;
	}

   
}

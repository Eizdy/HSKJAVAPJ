package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private LocalDate ngayLap;
    private int soLuong;
    private VeXemPhim ve;
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDon(String maHoaDon, LocalDate ngayLap, int soLuong, VeXemPhim ve) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.soLuong = soLuong;
		this.ve = ve;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public LocalDate getNgayLap() {
		return ngayLap;
	}
	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public VeXemPhim getVe() {
		return ve;
	}
	public void setVe(VeXemPhim ve) {
		this.ve = ve;
	}
    
}


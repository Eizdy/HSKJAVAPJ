package entity;

public class PhongChieuPhim {
    private String maPhong;
    private String tenPhong;
    private int soLuongGhe;
    private boolean trangThaiPhongChieu;
	public PhongChieuPhim(String maPhong, String tenPhong, int soLuongGhe, boolean trangThaiPhongChieu) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.soLuongGhe = soLuongGhe;
		this.trangThaiPhongChieu = trangThaiPhongChieu;
	}
	public PhongChieuPhim() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PhongChieuPhim(String maPhong) {
		super();
		this.maPhong = maPhong;
	}
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public int getSoLuongGhe() {
		return soLuongGhe;
	}
	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}
	public boolean isTrangThaiPhongChieu() {
		return trangThaiPhongChieu;
	}
	public void setTrangThaiPhongChieu(boolean trangThaiPhongChieu) {
		this.trangThaiPhongChieu = trangThaiPhongChieu;
	}
	@Override
    public String toString() {
        return tenPhong;
    }
    
}


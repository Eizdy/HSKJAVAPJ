package entity;

public class VeXemPhim {
    private String maVe;
    private LichChieuPhim lichChieu;
    private Ghe ghe;
    private KhachHang khachHang;
    private double giaVe;
	public VeXemPhim(String maVe, LichChieuPhim lichChieu, Ghe ghe, KhachHang khachHang, double giaVe) {
		super();
		this.maVe = maVe;
		this.lichChieu = lichChieu;
		this.ghe = ghe;
		this.khachHang = khachHang;
		this.giaVe = giaVe;
	}
	public VeXemPhim() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaVe() {
		return maVe;
	}
	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	public LichChieuPhim getLichChieu() {
		return lichChieu;
	}
	public void setLichChieu(LichChieuPhim lichChieu) {
		this.lichChieu = lichChieu;
	}
	public Ghe getGhe() {
		return ghe;
	}
	public void setGhe(Ghe ghe) {
		this.ghe = ghe;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public double getGiaVe() {
		return giaVe;
	}
	public void setGiaVe(double giaVe) {
		this.giaVe = giaVe;
	} 

    
}


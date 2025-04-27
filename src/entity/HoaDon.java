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
    private NhanVien maNV;
	public HoaDon(String maHoaDon, LocalDate ngayLap, int soLuong, VeXemPhim ve, NhanVien maNV) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.soLuong = soLuong;
		this.ve = ve;
		this.maNV = maNV;
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
	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	// Tính tổng tiền hóa đơn
		public double getTongTien() {
		    if (ve != null) {
		        return ve.getGiaVe() * soLuong;
		    }
		    return 0;
		}

		// Sinh ghi chú đơn giản cho hóa đơn
		public String getGhiChu() {
		    return "Hóa đơn ngày " + ngayLap.toString();
		}

		// Lấy tên nhân viên (tạm thời lấy mã nhân viên nếu chưa có tên)
		public String getTenNV() {
		    if (maNV != null) {
		        return maNV.getMaNV(); // Nếu sau này NhanVien có getTenNV() thì thay thế ở đây
		    }
		    return "Không rõ";
		}
}


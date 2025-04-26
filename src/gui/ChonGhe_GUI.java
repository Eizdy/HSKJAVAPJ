package gui;

import dao.HoaDon_DAO;
import dao.VeXemPhim_DAO;
import dao.KhachHang_DAO;
import entity.Ghe;
import entity.HoaDon;
import entity.LichChieuPhim;
import entity.NhanVien;
import entity.VeXemPhim;
import entity.KhachHang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChonGhe_GUI extends JFrame {
    private LichChieuPhim lichChieu;
    private VeXemPhim_DAO veXemPhimDAO = new VeXemPhim_DAO();
    private HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
    private KhachHang_DAO khachHangDAO = new KhachHang_DAO();
    private JButton[][] seatButtons;
    private Set<String> bookedSeats;
    private List<String> selectedSeats;
    private JButton btnConfirm;
    private String maPhong;

    public ChonGhe_GUI(LichChieuPhim lichChieu) {
        this.lichChieu = lichChieu;
        this.bookedSeats = new HashSet<>();
        this.selectedSeats = new ArrayList<>();
        this.maPhong = lichChieu.getPhong().getMaPhong();

        setTitle("Chọn Ghế - Suất chiếu: " + lichChieu.getMaLichChieu());
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        loadBookedSeats();
        add(createSeatPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadBookedSeats() {
        try {
            List<VeXemPhim> veList = veXemPhimDAO.layTatCaVe();
            for (VeXemPhim ve : veList) {
                if (ve.getLichChieu().getMaLichChieu().equals(lichChieu.getMaLichChieu())) {
                    bookedSeats.add(ve.getGhe().getMaGhe());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách ghế đã đặt: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createSeatPanel() {
        JPanel seatPanel = new JPanel(new GridLayout(5, 10, 5, 5));
        seatPanel.setBackground(new Color(45, 45, 45));
        seatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        seatButtons = new JButton[5][10];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                String rowLabel = String.valueOf((char) ('A' + row));
                String seatId = maPhong + rowLabel + (col + 1);
                seatButtons[row][col] = new JButton(rowLabel + (col + 1));
                seatButtons[row][col].setFont(new Font("Segoe UI", Font.PLAIN, 14));
                seatButtons[row][col].setCursor(new Cursor(Cursor.HAND_CURSOR));
                seatButtons[row][col].setActionCommand(seatId);

                if (bookedSeats.contains(seatId)) {
                    seatButtons[row][col].setBackground(Color.BLACK);
                    seatButtons[row][col].setEnabled(false);
                } else {
                    seatButtons[row][col].setBackground(Color.GREEN);
                }

                seatButtons[row][col].addActionListener(e -> {
                    JButton btn = (JButton) e.getSource();
                    String fullSeatId = btn.getActionCommand();
                    if (selectedSeats.contains(fullSeatId)) {
                        selectedSeats.remove(fullSeatId);
                        btn.setBackground(Color.GREEN);
                    } else {
                        selectedSeats.add(fullSeatId);
                        btn.setBackground(Color.ORANGE);
                    }
                    btnConfirm.setEnabled(!selectedSeats.isEmpty());
                });

                seatPanel.add(seatButtons[row][col]);
            }
        }
        return seatPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBackground(new Color(45, 45, 45));

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setBackground(new Color(0, 120, 215));
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setEnabled(false);
        btnConfirm.addActionListener(e -> confirmSelection());

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.addActionListener(e -> dispose());

        controlPanel.add(btnConfirm);
        controlPanel.add(btnCancel);
        return controlPanel;
    }

    private String generateNextMaVe() {
        try {
            List<VeXemPhim> veList = veXemPhimDAO.layTatCaVe();
            int maxNumber = 0;
            for (VeXemPhim ve : veList) {
                String maVe = ve.getMaVe();
                if (maVe != null && maVe.startsWith("V")) {
                    try {
                        int number = Integer.parseInt(maVe.substring(1));
                        maxNumber = Math.max(maxNumber, number);
                    } catch (NumberFormatException e) {
                        // Skip invalid maVe values
                    }
                }
            }
            return String.format("V%03d", maxNumber + 1);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo mã vé: " + e.getMessage(), e);
        }
    }

    private String generateNextMaHoaDon() {
        try {
            List<HoaDon> hoaDonList = hoaDonDAO.layTatCaHoaDon();
            int maxNumber = 0;
            for (HoaDon hd : hoaDonList) {
                String maHoaDon = hd.getMaHoaDon();
                if (maHoaDon != null && maHoaDon.startsWith("HD")) {
                    try {
                        int number = Integer.parseInt(maHoaDon.substring(2));
                        maxNumber = Math.max(maxNumber, number);
                    } catch (NumberFormatException e) {
                        // Skip invalid maHoaDon values
                    }
                }
            }
            return "HD" + (maxNumber + 1);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo mã hóa đơn: " + e.getMessage(), e);
        }
    }

    private void confirmSelection() {
        try {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV("NV01");

            // Generate base numbers for maVe and maHoaDon
            int baseMaVeNumber;
            try {
                String lastMaVe = generateNextMaVe();
                baseMaVeNumber = Integer.parseInt(lastMaVe.substring(1));
            } catch (Exception e) {
                baseMaVeNumber = 0;
            }

            // Generate a single maHoaDon for the invoice
            String maHoaDon = generateNextMaHoaDon();
            System.out.println("Tạo mã hóa đơn: " + maHoaDon);

            // List to store all tickets (VeXemPhim) for this booking
            List<VeXemPhim> tickets = new ArrayList<>();

            // Create and save tickets for each selected seat
            for (int i = 0; i < selectedSeats.size(); i++) {
                String seatId = selectedSeats.get(i);
                String maVe = String.format("V%03d", baseMaVeNumber + i);

                // Create and save new KhachHang
                String maKhachHang = khachHangDAO.generateNextMaKhachHang();
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(maKhachHang);
                khachHang.setTenKH(null);
                khachHangDAO.themKhachHang(khachHang);
                System.out.println("Khách hàng " + maKhachHang + " đã được thêm vào cơ sở dữ liệu.");

                // Create VeXemPhim
                VeXemPhim ve = new VeXemPhim();
                ve.setMaVe(maVe);
                ve.setLichChieu(lichChieu);

                Ghe ghe = new Ghe(seatId, seatId.substring(4), false, lichChieu.getPhong());
                ve.setGhe(ghe);
                ve.setKhachHang(khachHang);
                ve.setGiaVe(50000);
                ve.setNhanVien(nhanVien);

                // Save VeXemPhim
                veXemPhimDAO.themVe(ve);
                System.out.println("Vé " + maVe + " đã được thêm vào cơ sở dữ liệu.");

                // Add ticket to the list
                tickets.add(ve);
            }

            // Create a single HoaDon with the total quantity
            if (!tickets.isEmpty()) {
                HoaDon hoaDon = new HoaDon(maHoaDon, LocalDate.now(), selectedSeats.size(), tickets.get(0), nhanVien);
                System.out.println("Tạo hóa đơn: " + maHoaDon + ", maVe: " + tickets.get(0).getMaVe() + ", maNV: " + nhanVien.getMaNV() + ", soLuong: " + selectedSeats.size());

                // Save HoaDon
                hoaDonDAO.themHoaDon(hoaDon);
            }

            JOptionPane.showMessageDialog(this, "Đặt vé thành công cho " + selectedSeats.size() + " ghế!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đặt vé: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
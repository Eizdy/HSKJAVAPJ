package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import dao.LichChieuPhim_DAO;
import dao.Phim_DAO;
import entity.LichChieuPhim;
import entity.Phim;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class QuanLyBanVe_GUI extends JFrame {

    private JTable tablePhim;
    private DefaultTableModel modelPhim;
    private JDateChooser dateChooser;
    private JButton btnChonNgay;
    private JButton btnChonGhe;
    private LichChieuPhim_DAO lichChieu_DAO = new LichChieuPhim_DAO();
    private Phim_DAO phimDAO = new Phim_DAO();
    private List<LichChieuPhim> danhSachLich; // Store the list of showtimes for the selected date

    public QuanLyBanVe_GUI() {
        setTitle("Quản lý bán vé - Rạp Chiếu Phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JLabel lbl = new JLabel("QUẢN LÝ BÁN VÉ", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));

        JPanel header = new JPanel(new BorderLayout());
        header.add(lbl);
        return header;
    }

    private JPanel createSidebar() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(25, 25, 25));
        menu.setPreferredSize(new Dimension(180, 0));

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hoá đơn", "Bán vé", "Thống kê","Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                dispose();
                switch (item) {
                    case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
                    case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
                    case "Suất chiếu" -> new SuatChieu_GUI().setVisible(true);
                    case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
                    case "Hoá đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
                    case "Bán vé" -> new QuanLyBanVe_GUI().setVisible(true);
                    case "Thống kê" -> new QuanLyThongKe_GUI().setVisible(true);
                    case "Đăng xuất" -> System.exit(0);
                    default -> {}
                }
            });

            if (item.equals("Đăng xuất")) btn.setForeground(Color.RED);
            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        // Top panel for date selection
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 45, 45));

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 25));
        btnChonNgay = new JButton("Chọn Ngày");
        btnChonNgay.addActionListener(e -> loadPhimTheoNgay());

        topPanel.add(new JLabel("Chọn ngày chiếu:"));
        topPanel.add(dateChooser);
        topPanel.add(btnChonNgay);

        // Table to display movies and showtimes
        modelPhim = new DefaultTableModel(new String[]{
                "Mã lịch chiếu", "Tên phim", "Thời gian chiếu", "Ngày chiếu", "Thời lượng", "Đạo diễn", "Thể loại", "Ngôn ngữ"
        }, 0);
        tablePhim = new JTable(modelPhim);
        tablePhim.setBackground(new Color(60, 60, 60));
        tablePhim.setForeground(Color.WHITE);
        tablePhim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablePhim.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnChonGhe.setEnabled(tablePhim.getSelectedRow() != -1);
            }
        });

        JScrollPane scrollPhim = new JScrollPane(tablePhim);
        scrollPhim.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Danh sách phim và suất chiếu"));
        scrollPhim.getViewport().setBackground(new Color(45, 45, 45));

        // Button to select seats
        btnChonGhe = new JButton("Chọn ghế");
        btnChonGhe.setForeground(Color.WHITE);
        btnChonGhe.setBackground(new Color(0, 120, 215));
        btnChonGhe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnChonGhe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChonGhe.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChonGhe.setEnabled(false); // Initially disabled
        btnChonGhe.addActionListener(e -> chonGhe());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.add(scrollPhim, BorderLayout.CENTER);
        centerPanel.add(btnChonGhe, BorderLayout.SOUTH);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(centerPanel, BorderLayout.CENTER);

        return main;
    }

    private void loadPhimTheoNgay() {
        modelPhim.setRowCount(0);
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày.");
            return;
        }

        LocalDate ngay = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            danhSachLich = lichChieu_DAO.layLichChieuTheoNgay(ngay);
            if (danhSachLich.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có suất chiếu nào vào ngày " + ngay + ".");
                return;
            }

            for (LichChieuPhim lc : danhSachLich) {
                Phim p = phimDAO.timPhimTheoMa(lc.getPhim().getMaPhim());
                if (p != null) {
                    modelPhim.addRow(new Object[]{
                            lc.getMaLichChieu(),
                            p.getTenPhim(),
                            lc.getThoiGianChieu(),
                            lc.getNgayChieu(),
                            p.getThoiLuong(),
                            p.getDaoDien(),
                            p.getTheLoai() != null ? p.getTheLoai().getTenLoai() : "",
                            p.getNgonNgu()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonGhe() {
        int row = tablePhim.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu trước khi chọn ghế.");
            return;
        }

        String maLichChieu = modelPhim.getValueAt(row, 0).toString();
        LichChieuPhim selectedLich = danhSachLich.stream()
                .filter(lc -> lc.getMaLichChieu().equals(maLichChieu))
                .findFirst()
                .orElse(null);

        if (selectedLich != null) {
            new ChonGhe_GUI(selectedLich).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createFooter() {
        JLabel lbl = new JLabel("Nhóm Quản lý Rạp Chiếu Phim", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(lbl);
        return footer;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyBanVe_GUI().setVisible(true));
    }
}
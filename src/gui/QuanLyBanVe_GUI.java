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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuanLyBanVe_GUI extends JFrame {

    private JTable tablePhim;
    private DefaultTableModel modelPhim;
    private JDateChooser dateChooser;
    private JButton btnChonNgay;
    private JButton btnChonGhe;
    private LichChieuPhim_DAO lichChieu_DAO = new LichChieuPhim_DAO();
    private Phim_DAO phimDAO = new Phim_DAO();

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

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hoá đơn", "Bán vé", "Thống kê", "Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                dispose();
                // Điều hướng sang các giao diện khác nếu có
            });

            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

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

        modelPhim = new DefaultTableModel(new String[]{"Mã phim", "Tên phim", "Thời lượng", "Đạo diễn", "Thể loại"}, 0);
        tablePhim = new JTable(modelPhim);
        JScrollPane scrollPhim = new JScrollPane(tablePhim);

        btnChonGhe = new JButton("Chọn ghế");
        btnChonGhe.setForeground(Color.WHITE);
        btnChonGhe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChonGhe.setAlignmentX(Component.CENTER_ALIGNMENT);
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
            List<LichChieuPhim> danhSachLich = lichChieu_DAO.layLichChieuTheoNgay(ngay);
            Set<String> phimDaThem = new HashSet<>();

            for (LichChieuPhim lc : danhSachLich) {
                String maPhim = lc.getPhim().getMaPhim();
                if (!phimDaThem.contains(maPhim)) {
                    Phim p = phimDAO.timPhimTheoMa(maPhim);
                    if (p != null) {
                        modelPhim.addRow(new Object[]{
                                p.getMaPhim(),
                                p.getTenPhim(),
                                p.getThoiLuong(),
                                p.getDaoDien(),
                                p.getTheLoai() != null ? p.getTheLoai().getTenLoai() : ""
                        });
                        phimDaThem.add(maPhim);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void chonGhe() {
        int row = tablePhim.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim trước khi chọn ghế.");
            return;
        }
        String tenPhim = modelPhim.getValueAt(row, 1).toString();
        new ChonGhe_GUI(tenPhim).setVisible(true);
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

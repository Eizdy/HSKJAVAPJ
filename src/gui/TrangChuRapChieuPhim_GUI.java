package gui;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class TrangChuRapChieuPhim_GUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable tableVe;
    private JTextField txtTenKH, txtSDT, txtTongTien;
    private JComboBox<String> cboPTThanhToan;
    private int[] sttCounter = {1};

    public TrangChuRapChieuPhim_GUI() {
        setTitle("Trang chủ - Quản lý Rạp Chiếu Phim");
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
        JLabel lbl = new JLabel("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM", SwingConstants.CENTER);
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

        String[] items = {"Trang chủ", "Phim", "Lịch chiếu", "Đặt vé", "Khách hàng", "Thống kê", "Đăng xuất"};
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

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(60, 60, 60));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(40, 40, 40));
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

        JPanel phimPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        phimPanel.setBackground(new Color(45, 45, 45));

        String[] phimNames = {
                "Avengers: Endgame", "Inception", "The Batman",
                "Interstellar", "Joker", "Avatar 2"
        };
        String[] imagePaths = {
                "endgame.jpg",
                "boGia.jpg",
                "avatar.jpg",
                "joker.jpg",
                "Batman.jpg",
                "yeu.jpg"
        };

        for (int i = 0; i < phimNames.length; i++) {
            phimPanel.add(createMovieCard(phimNames[i], 75000, imagePaths[i]));
        }

        JScrollPane scrollPhim = new JScrollPane(phimPanel);
        scrollPhim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "🎥 Danh sách phim"));
        scrollPhim.setBackground(new Color(45, 45, 45));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(35, 35, 35));

        txtTenKH = createTextField("👤 Tên khách hàng");
        txtSDT = createTextField("📞 Số điện thoại");

        String[] cols = {"STT", "Phim", "Số vé", "Giá"};
        tableModel = new DefaultTableModel(cols, 0);
        tableVe = new JTable(tableModel);
        tableVe.setBackground(new Color(60, 60, 60));
        tableVe.setForeground(Color.WHITE);
        tableVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableVe.setRowHeight(22);

        JScrollPane sp = new JScrollPane(tableVe);
        sp.setBorder(BorderFactory.createTitledBorder("🎟️ Vé đã chọn"));

        cboPTThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cboPTThanhToan.setBorder(BorderFactory.createTitledBorder("💳 Phương thức thanh toán"));
        cboPTThanhToan.setBackground(new Color(70, 70, 70));
        cboPTThanhToan.setForeground(Color.WHITE);

        txtTongTien = createTextField("💰 Tổng tiền");
        txtTongTien.setEditable(false);

        JButton btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThanhToan.setBackground(new Color(0, 120, 215));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightPanel.add(txtTenKH);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(txtSDT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(sp);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(cboPTThanhToan);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(txtTongTien);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(btnThanhToan);

        main.add(scrollPhim, BorderLayout.CENTER);
        main.add(rightPanel, BorderLayout.EAST);

        return main;
    }

    private JTextField createTextField(String title) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        return field;
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

    private JPanel createMovieCard(String tenPhim, int giaVe, String imageFileName) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(55, 55, 55));
        card.setPreferredSize(new Dimension(200, 300));

        URL imgURL = getClass().getResource("/img/" + imageFileName);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imgLabel);
        } else {
            JLabel imgLabel = new JLabel("Không tìm thấy ảnh");
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imgLabel);
        }

        JLabel lblTen = new JLabel(tenPhim, SwingConstants.CENTER);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTen.setForeground(Color.WHITE);
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSpinner spn = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spn.setMaximumSize(new Dimension(60, 25));
        spn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = new JButton("Chọn");
        btn.setBackground(new Color(100, 149, 237));
        btn.setForeground(Color.WHITE);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            int sl = (int) spn.getValue();
            if (sl > 0) {
                int stt = sttCounter[0]++;
                int tong = sl * giaVe;
                tableModel.addRow(new Object[]{stt, tenPhim, sl, tong});
                capNhatTongTien();
            }
        });

        card.add(Box.createVerticalStrut(5));
        card.add(lblTen);
        card.add(Box.createVerticalStrut(5));
        card.add(new JLabel("🎫 Số vé:", SwingConstants.CENTER));
        card.add(spn);
        card.add(Box.createVerticalStrut(5));
        card.add(btn);
        return card;
    }

    private void capNhatTongTien() {
        int tong = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tong += (int) tableModel.getValueAt(i, 3);
        }
        txtTongTien.setText(tong + " VND");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TrangChuRapChieuPhim_GUI().setVisible(true));
    }
}

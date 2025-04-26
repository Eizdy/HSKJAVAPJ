package gui;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;

public class TrangChuRapChieuPhim_GUI extends JFrame {

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

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hoá đơn", "Bán vé", "Đăng xuất"};
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

        // Banner Panel
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(45, 45, 45));
        bannerPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Add some spacing below the banner

        URL bannerURL = getClass().getResource("/img/banner4.jpg");
        if (bannerURL != null) {
            ImageIcon bannerIcon = new ImageIcon(bannerURL);
            // Scale the banner to fit the width of the window (minus sidebar and padding)
            Image scaledBanner = bannerIcon.getImage().getScaledInstance(1050, 350, Image.SCALE_SMOOTH);
            JLabel bannerLabel = new JLabel(new ImageIcon(scaledBanner));
            bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bannerPanel.add(bannerLabel);
        } else {
            JLabel bannerLabel = new JLabel("Không tìm thấy ảnh banner", SwingConstants.CENTER);
            bannerLabel.setForeground(Color.WHITE);
            bannerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            bannerPanel.add(bannerLabel);
        }

        // Movie List Panel
        JPanel phimPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        phimPanel.setBackground(new Color(45, 45, 45));

        String[] phimNames = {
                "Avengers: Endgame", "Inception", "The Batman",
                "Interstellar", "Joker", "Avatar 2"
        };
        String[] imagePaths = {
                "endgame.jpg", "boGia.jpg", "avatar.jpg",
                "joker.jpg", "Batman.jpg", "yeu.jpg"
        };
        String[] descriptions = {
                "The epic conclusion to the Avengers saga.",
                "A thief who steals secrets through dreams.",
                "A dark and gritty take on the Dark Knight.",
                "A journey through space to save humanity.",
                "The origin story of the iconic villain.",
                "The long-awaited sequel to the 2009 film."
        };

        for (int i = 0; i < phimNames.length; i++) {
            phimPanel.add(createMovieCard(phimNames[i], descriptions[i], imagePaths[i]));
        }

        JScrollPane scrollPhim = new JScrollPane(phimPanel);
        scrollPhim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "🎥 Danh sách phim"));
        scrollPhim.setBackground(new Color(45, 45, 45));

        // Add banner and movie list to the main panel
        main.add(bannerPanel, BorderLayout.NORTH);
        main.add(scrollPhim, BorderLayout.CENTER);

        return main;
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

    private JPanel createMovieCard(String tenPhim, String description, String imageFileName) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(55, 55, 55));
        card.setPreferredSize(new Dimension(200, 350));

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

        JLabel lblDescription = new JLabel("<html><div style='text-align: center;'>" + description + "</div></html>", SwingConstants.CENTER);
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescription.setForeground(Color.LIGHT_GRAY);
        lblDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(lblTen);
        card.add(Box.createVerticalStrut(5));
        card.add(lblDescription);

        return card;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TrangChuRapChieuPhim_GUI().setVisible(true));
    }
}
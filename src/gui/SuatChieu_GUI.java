package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import dao.LichChieuPhim_DAO;
import dao.Phim_DAO;
import dao.PhongChieuPhim_DAO;
import entity.LichChieuPhim;
import entity.Phim;
import entity.PhongChieuPhim;

public class SuatChieu_GUI extends JFrame {
    private JTable tblSuatChieu;
    private DefaultTableModel modelSuatChieu;
    private JTextField txtTenPhim, txtNgayChieu;
    private JCheckBox chkTenPhim, chkNgayChieu;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
    private LichChieuPhim_DAO lichChieuDAO = new LichChieuPhim_DAO();
    private Phim_DAO phimDAO = new Phim_DAO();
    private PhongChieuPhim_DAO phongDAO = new PhongChieuPhim_DAO();

    public SuatChieu_GUI() {
        setTitle("Suất chiếu - Quản lý Rạp Chiếu Phim");
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

        loadDataToTable();
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
            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
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

            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        // Center - Table
        String[] cols = {"Mã lịch chiếu", "Mã phim", "Tên phim", "Thời gian chiếu", "Ngày chiếu", "Ngôn ngữ", "Trạng thái", "Phòng"};
        modelSuatChieu = new DefaultTableModel(cols, 0);
        tblSuatChieu = new JTable(modelSuatChieu);

        // Customize table appearance
        tblSuatChieu.setBackground(new Color(60, 60, 60));
        tblSuatChieu.setForeground(Color.WHITE);
        tblSuatChieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSuatChieu.setRowHeight(22);
        tblSuatChieu.setSelectionBackground(new Color(0, 120, 215));
        tblSuatChieu.setSelectionForeground(Color.WHITE);
        tblSuatChieu.setGridColor(new Color(80, 80, 80));

        // Custom cell renderer to fix white border issue
        tblSuatChieu.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                c.setForeground(Color.WHITE);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tblSuatChieu);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách suất chiếu"));
        scroll.getViewport().setBackground(new Color(45, 45, 45));
        main.add(scroll, BorderLayout.CENTER);

        // East - Search Panel
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkTenPhim = new JCheckBox("Tìm theo tên phim");
        chkTenPhim.setBackground(new Color(45, 45, 45));
        chkTenPhim.setForeground(Color.WHITE);
        chkTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkTenPhim.setSelected(true);
        chkTenPhim.setMaximumSize(new Dimension(230, 30));
        chkTenPhim.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtTenPhim = new JTextField();
        txtTenPhim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
        txtTenPhim.setBackground(new Color(60, 60, 60));
        txtTenPhim.setForeground(Color.WHITE);
        txtTenPhim.setCaretColor(Color.WHITE);
        txtTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        chkNgayChieu = new JCheckBox("Tìm theo ngày chiếu");
        chkNgayChieu.setBackground(new Color(45, 45, 45));
        chkNgayChieu.setForeground(Color.WHITE);
        chkNgayChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkNgayChieu.setMaximumSize(new Dimension(230, 30));
        chkNgayChieu.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtNgayChieu = new JTextField();
        txtNgayChieu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtNgayChieu.setBorder(BorderFactory.createTitledBorder("Ngày chiếu (yyyy-MM-dd)"));
        txtNgayChieu.setBackground(new Color(60, 60, 60));
        txtNgayChieu.setForeground(Color.WHITE);
        txtNgayChieu.setCaretColor(Color.WHITE);
        txtNgayChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Make checkboxes mutually exclusive
        chkTenPhim.addActionListener(e -> {
            if (chkTenPhim.isSelected()) {
                chkNgayChieu.setSelected(false);
                txtNgayChieu.setText("");
            }
        });
        chkNgayChieu.addActionListener(e -> {
            if (chkNgayChieu.isSelected()) {
                chkTenPhim.setSelected(false);
                txtTenPhim.setText("");
            }
        });

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTimKiem.addActionListener(e -> searchShowtimes());

        btnXoaTrang = new JButton("Xoá trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        btnXoaTrang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoaTrang.addActionListener(e -> {
            txtTenPhim.setText("");
            txtNgayChieu.setText("");
            chkTenPhim.setSelected(true);
            chkNgayChieu.setSelected(false);
            loadDataToTable();
            tblSuatChieu.clearSelection();
        });

        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);

        east.add(lblTieuDe);
        east.add(Box.createVerticalStrut(10));
        east.add(chkTenPhim);
        east.add(txtTenPhim);
        east.add(Box.createVerticalStrut(10));
        east.add(chkNgayChieu);
        east.add(txtNgayChieu);
        east.add(Box.createVerticalStrut(15));
        east.add(actionPanel);

        main.add(east, BorderLayout.EAST);

        // South - Action Buttons
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        south.setBackground(new Color(45, 45, 45));

        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 120, 215));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.addActionListener(e -> showAddShowtimePopup());

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(200, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.addActionListener(e -> {
            int selectedRow = tblSuatChieu.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu để xoá!");
                return;
            }

            String maLichChieu = modelSuatChieu.getValueAt(selectedRow, 0).toString();
            String tenPhim = modelSuatChieu.getValueAt(selectedRow, 2).toString();
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xoá suất chiếu: " + tenPhim + " (" + maLichChieu + ")?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (lichChieuDAO.xoaLichChieu(maLichChieu)) {
                        JOptionPane.showMessageDialog(this, "Đã xoá suất chiếu: " + tenPhim);
                        loadDataToTable();
                        tblSuatChieu.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xoá suất chiếu thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xoá suất chiếu: " + ex.getMessage());
                }
            }
        });

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 120, 215));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.addActionListener(e -> showEditShowtimePopup());

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);
        btnHienTatCa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHienTatCa.addActionListener(e -> {
            txtTenPhim.setText("");
            txtNgayChieu.setText("");
            chkTenPhim.setSelected(true);
            chkNgayChieu.setSelected(false);
            loadDataToTable();
            tblSuatChieu.clearSelection();
        });

        south.add(btnThem);
        south.add(btnXoa);
        south.add(btnSua);
        south.add(btnHienTatCa);

        main.add(south, BorderLayout.SOUTH);

        return main;
    }

    private void searchShowtimes() {
        try {
            String tenPhim = txtTenPhim.getText().trim();
            String ngayChieuStr = txtNgayChieu.getText().trim();

            if (!chkTenPhim.isSelected() && !chkNgayChieu.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiêu chí tìm kiếm!");
                return;
            }

            if ((chkTenPhim.isSelected() && tenPhim.isEmpty()) || 
                (chkNgayChieu.isSelected() && ngayChieuStr.isEmpty())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
                return;
            }

            modelSuatChieu.setRowCount(0);

            boolean found = false;
            int rowIndex = 0;
            int selectIndex = -1;
            List<LichChieuPhim> list;

            if (chkTenPhim.isSelected()) {
                list = lichChieuDAO.layTatCaLichChieu();
                for (LichChieuPhim lc : list) {
                    if (lc.getPhim().getTenPhim().toLowerCase().contains(tenPhim.toLowerCase())) {
                        modelSuatChieu.addRow(new Object[]{
                            lc.getMaLichChieu(),
                            lc.getPhim().getMaPhim(),
                            lc.getPhim().getTenPhim(),
                            lc.getThoiGianChieu(),
                            lc.getNgayChieu().toString(),
                            lc.getPhim().getNgonNgu(),
                            lc.getTrangThai(),
                            lc.getPhong().getTenPhong()
                        });
                        if (lc.getPhim().getTenPhim().equalsIgnoreCase(tenPhim)) {
                            selectIndex = rowIndex;
                        }
                        found = true;
                        rowIndex++;
                    }
                }
            } else if (chkNgayChieu.isSelected()) {
                LocalDate ngayChieu = LocalDate.parse(ngayChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                list = lichChieuDAO.layLichChieuTheoNgay(ngayChieu);
                for (LichChieuPhim lc : list) {
                    modelSuatChieu.addRow(new Object[]{
                        lc.getMaLichChieu(),
                        lc.getPhim().getMaPhim(),
                        lc.getPhim().getTenPhim(),
                        lc.getThoiGianChieu(),
                        lc.getNgayChieu().toString(),
                        lc.getPhim().getNgonNgu(),
                        lc.getTrangThai(),
                        lc.getPhong().getTenPhong()
                    });
                    found = true;
                    rowIndex++;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu phù hợp!");
                loadDataToTable();
            } else if (selectIndex >= 0) {
                tblSuatChieu.setRowSelectionInterval(selectIndex, selectIndex);
                tblSuatChieu.scrollRectToVisible(tblSuatChieu.getCellRect(selectIndex, 0, true));
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày chiếu phải có định dạng yyyy-MM-dd!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private String generateNextMaLichChieu() {
        try {
            int currentCount = lichChieuDAO.layTatCaLichChieu().size();
            return String.format("LC%02d", currentCount + 1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể tạo mã lịch chiếu mới: " + e.getMessage());
            return "LC01";
        }
    }

    private void showAddShowtimePopup() {
        JDialog dialog = new JDialog(this, "Thêm Suất Chiếu", true);
        dialog.setSize(550, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // North: Title
        JLabel lblTitle = new JLabel("THÊM SUẤT CHIẾU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        // Center: Input Fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(new Color(45, 45, 45));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Mã lịch chiếu
        JTextField txtMaLichChieu = new JTextField(generateNextMaLichChieu());
        txtMaLichChieu.setBorder(BorderFactory.createTitledBorder("Mã lịch chiếu"));
        txtMaLichChieu.setBackground(new Color(60, 60, 60));
        txtMaLichChieu.setForeground(Color.WHITE);
        txtMaLichChieu.setCaretColor(Color.WHITE); // Set caret color to white
        txtMaLichChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaLichChieu.setEditable(false);

        // Mã phim (non-editable)
        JTextField txtMaPhim = new JTextField();
        txtMaPhim.setBorder(BorderFactory.createTitledBorder("Mã phim"));
        txtMaPhim.setBackground(new Color(60, 60, 60));
        txtMaPhim.setForeground(Color.WHITE);
        txtMaPhim.setCaretColor(Color.WHITE); // Set caret color to white
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaPhim.setEditable(false);

        // Tên phim (JComboBox)
        JComboBox<Phim> cboTenPhim = new JComboBox<>();
        cboTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
        cboTenPhim.setBackground(new Color(60, 60, 60));
        cboTenPhim.setForeground(Color.WHITE);
        cboTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Custom renderer to ensure proper display
        cboTenPhim.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Phim phim) {
                    setText(phim.getTenPhim());
                }
                setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                setForeground(Color.WHITE);
                return c;
            }
        });
        try {
            List<Phim> phimList = phimDAO.layTatCaPhim();
            for (Phim phim : phimList) {
                cboTenPhim.addItem(phim);
            }
            if (phimList.size() > 0) {
                cboTenPhim.setSelectedIndex(0); // Select the first item by default
                txtMaPhim.setText(((Phim) cboTenPhim.getSelectedItem()).getMaPhim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách phim: " + e.getMessage());
        }
        cboTenPhim.addActionListener(e -> {
            Phim selectedPhim = (Phim) cboTenPhim.getSelectedItem();
            if (selectedPhim != null) {
                txtMaPhim.setText(selectedPhim.getMaPhim());
            }
        });

        // Thời gian chiếu
        JTextField txtThoiGianChieu = new JTextField();
        txtThoiGianChieu.setBorder(BorderFactory.createTitledBorder("Thời gian chiếu (HH:mm)"));
        txtThoiGianChieu.setBackground(new Color(60, 60, 60));
        txtThoiGianChieu.setForeground(Color.WHITE);
        txtThoiGianChieu.setCaretColor(Color.WHITE); // Set caret color to white
        txtThoiGianChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Ngày chiếu
        JTextField txtNgayChieu = new JTextField();
        txtNgayChieu.setBorder(BorderFactory.createTitledBorder("Ngày chiếu (yyyy-MM-dd)"));
        txtNgayChieu.setBackground(new Color(60, 60, 60));
        txtNgayChieu.setForeground(Color.WHITE);
        txtNgayChieu.setCaretColor(Color.WHITE); // Set caret color to white
        txtNgayChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Phòng (JComboBox)
        JComboBox<PhongChieuPhim> cboPhong = new JComboBox<>();
        cboPhong.setBorder(BorderFactory.createTitledBorder("Phòng"));
        cboPhong.setBackground(new Color(60, 60, 60));
        cboPhong.setForeground(Color.WHITE);
        cboPhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Custom renderer to ensure proper display
        cboPhong.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PhongChieuPhim phong) {
                    setText(phong.getTenPhong());
                }
                setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                setForeground(Color.WHITE);
                return c;
            }
        });
        try {
            List<PhongChieuPhim> phongList = phongDAO.layTatCaPhongChieu();
            for (PhongChieuPhim phong : phongList) {
                cboPhong.addItem(phong);
            }
            if (phongList.size() > 0) {
                cboPhong.setSelectedIndex(0); // Select the first item by default
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách phòng: " + e.getMessage());
        }

        inputPanel.add(txtMaLichChieu);
        inputPanel.add(txtMaPhim);
        inputPanel.add(cboTenPhim);
        inputPanel.add(txtThoiGianChieu);
        inputPanel.add(txtNgayChieu);
        inputPanel.add(cboPhong);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // South: Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maLichChieu = txtMaLichChieu.getText().trim();
                Phim phim = (Phim) cboTenPhim.getSelectedItem();
                String thoiGianChieu = txtThoiGianChieu.getText().trim();
                String ngayChieuStr = txtNgayChieu.getText().trim();
                PhongChieuPhim phong = (PhongChieuPhim) cboPhong.getSelectedItem();

                if (maLichChieu.isEmpty() || phim == null || thoiGianChieu.isEmpty() || ngayChieuStr.isEmpty() || phong == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                if (!thoiGianChieu.matches("\\d{2}:\\d{2}")) {
                    JOptionPane.showMessageDialog(dialog, "Thời gian chiếu phải có định dạng HH:mm!");
                    return;
                }

                LocalDate ngayChieu = LocalDate.parse(ngayChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                LichChieuPhim lc = new LichChieuPhim(maLichChieu, phim, phong, thoiGianChieu, "Đang chiếu", ngayChieu);

                if (lichChieuDAO.themLichChieu(lc)) {
                    JOptionPane.showMessageDialog(dialog, "Đã thêm suất chiếu: " + phim.getTenPhim());
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm suất chiếu thất bại!");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày chiếu phải có định dạng yyyy-MM-dd!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditShowtimePopup() {
        int selectedRow = tblSuatChieu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa Suất Chiếu", true);
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // North: Title
        JLabel lblTitle = new JLabel("SỬA SUẤT CHIẾU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        // Center: Input Fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(new Color(45, 45, 45));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Mã lịch chiếu (non-editable)
        JTextField txtMaLichChieu = new JTextField(modelSuatChieu.getValueAt(selectedRow, 0).toString());
        txtMaLichChieu.setBorder(BorderFactory.createTitledBorder("Mã lịch chiếu"));
        txtMaLichChieu.setBackground(new Color(60, 60, 60));
        txtMaLichChieu.setForeground(Color.WHITE);
        txtMaLichChieu.setCaretColor(Color.WHITE);
        txtMaLichChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaLichChieu.setEditable(false);

        // Mã phim (non-editable)
        JTextField txtMaPhim = new JTextField(modelSuatChieu.getValueAt(selectedRow, 1).toString());
        txtMaPhim.setBorder(BorderFactory.createTitledBorder("Mã phim"));
        txtMaPhim.setBackground(new Color(60, 60, 60));
        txtMaPhim.setForeground(Color.WHITE);
        txtMaPhim.setCaretColor(Color.WHITE);
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaPhim.setEditable(false);

        // Tên phim (editable JComboBox)
        JComboBox<Phim> cboTenPhim = new JComboBox<>();
        cboTenPhim.setBorder(BorderFactory.createTitledBorder("Tên phim"));
        cboTenPhim.setBackground(new Color(60, 60, 60));
        cboTenPhim.setForeground(Color.WHITE);
        cboTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboTenPhim.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Phim phim) {
                    setText(phim.getTenPhim());
                }
                setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                setForeground(Color.WHITE);
                return c;
            }
        });
        try {
            List<Phim> phimList = phimDAO.layTatCaPhim();
            for (Phim phim : phimList) {
                cboTenPhim.addItem(phim);
                if (phim.getMaPhim().equals(txtMaPhim.getText())) {
                    cboTenPhim.setSelectedItem(phim);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách phim: " + e.getMessage());
        }
        // Add ActionListener to update txtMaPhim when a new movie is selected
        cboTenPhim.addActionListener(e -> {
            Phim selectedPhim = (Phim) cboTenPhim.getSelectedItem();
            if (selectedPhim != null) {
                txtMaPhim.setText(selectedPhim.getMaPhim());
            }
        });

        // Thời gian chiếu
        JTextField txtThoiGianChieu = new JTextField(modelSuatChieu.getValueAt(selectedRow, 3).toString());
        txtThoiGianChieu.setBorder(BorderFactory.createTitledBorder("Thời gian chiếu (HH:mm)"));
        txtThoiGianChieu.setBackground(new Color(60, 60, 60));
        txtThoiGianChieu.setForeground(Color.WHITE);
        txtThoiGianChieu.setCaretColor(Color.WHITE);
        txtThoiGianChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Ngày chiếu
        JTextField txtNgayChieu = new JTextField(modelSuatChieu.getValueAt(selectedRow, 4).toString());
        txtNgayChieu.setBorder(BorderFactory.createTitledBorder("Ngày chiếu (yyyy-MM-dd)"));
        txtNgayChieu.setBackground(new Color(60, 60, 60));
        txtNgayChieu.setForeground(Color.WHITE);
        txtNgayChieu.setCaretColor(Color.WHITE);
        txtNgayChieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Ngôn ngữ (non-editable)
        JTextField txtNgonNgu = new JTextField(modelSuatChieu.getValueAt(selectedRow, 5).toString());
        txtNgonNgu.setBorder(BorderFactory.createTitledBorder("Ngôn ngữ"));
        txtNgonNgu.setBackground(new Color(60, 60, 60));
        txtNgonNgu.setForeground(Color.WHITE);
        txtNgonNgu.setCaretColor(Color.WHITE);
        txtNgonNgu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNgonNgu.setEditable(false);

        // Trạng thái
        JTextField txtTrangThai = new JTextField(modelSuatChieu.getValueAt(selectedRow, 6).toString());
        txtTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        txtTrangThai.setBackground(new Color(60, 60, 60));
        txtTrangThai.setForeground(Color.WHITE);
        txtTrangThai.setCaretColor(Color.WHITE);
        txtTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Phòng (JComboBox)
        JComboBox<PhongChieuPhim> cboPhong = new JComboBox<>();
        cboPhong.setBorder(BorderFactory.createTitledBorder("Phòng"));
        cboPhong.setBackground(new Color(60, 60, 60));
        cboPhong.setForeground(Color.WHITE);
        cboPhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboPhong.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PhongChieuPhim phong) {
                    setText(phong.getTenPhong());
                }
                setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                setForeground(Color.WHITE);
                return c;
            }
        });
        try {
            List<PhongChieuPhim> phongList = phongDAO.layTatCaPhongChieu();
            for (PhongChieuPhim phong : phongList) {
                cboPhong.addItem(phong);
                if (phong.getTenPhong().equals(modelSuatChieu.getValueAt(selectedRow, 7).toString())) {
                    cboPhong.setSelectedItem(phong);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Không thể tải danh sách phòng: " + e.getMessage());
        }

        inputPanel.add(txtMaLichChieu);
        inputPanel.add(txtMaPhim);
        inputPanel.add(cboTenPhim);
        inputPanel.add(txtThoiGianChieu);
        inputPanel.add(txtNgayChieu);
        inputPanel.add(txtNgonNgu);
        inputPanel.add(txtTrangThai);
        inputPanel.add(cboPhong);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // South: Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String maLichChieu = txtMaLichChieu.getText().trim();
                Phim phim = (Phim) cboTenPhim.getSelectedItem();
                String thoiGianChieu = txtThoiGianChieu.getText().trim();
                String ngayChieuStr = txtNgayChieu.getText().trim();
                String trangThai = txtTrangThai.getText().trim();
                PhongChieuPhim phong = (PhongChieuPhim) cboPhong.getSelectedItem();

                if (maLichChieu.isEmpty() || phim == null || thoiGianChieu.isEmpty() || ngayChieuStr.isEmpty() || trangThai.isEmpty() || phong == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                    return;
                }

                if (!thoiGianChieu.matches("\\d{2}:\\d{2}")) {
                    JOptionPane.showMessageDialog(dialog, "Thời gian chiếu phải có định dạng HH:mm!");
                    return;
                }

                LocalDate ngayChieu = LocalDate.parse(ngayChieuStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                LichChieuPhim lc = new LichChieuPhim(maLichChieu, phim, phong, thoiGianChieu, trangThai, ngayChieu);

                if (lichChieuDAO.capNhatLichChieu(lc)) {
                    JOptionPane.showMessageDialog(dialog, "Đã cập nhật suất chiếu: " + phim.getTenPhim());
                    loadDataToTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật suất chiếu thất bại!");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày chiếu phải có định dạng yyyy-MM-dd!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage());
            }
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
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

    private void loadDataToTable() {
        try {
            modelSuatChieu.setRowCount(0);
            List<LichChieuPhim> list = lichChieuDAO.layTatCaLichChieu();
            for (LichChieuPhim lc : list) {
                modelSuatChieu.addRow(new Object[]{
                    lc.getMaLichChieu(),
                    lc.getPhim().getMaPhim(),
                    lc.getPhim().getTenPhim(),
                    lc.getThoiGianChieu(),
                    lc.getNgayChieu().toString(),
                    lc.getPhim().getNgonNgu(),
                    lc.getTrangThai(),
                    lc.getPhong().getTenPhong()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách suất chiếu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new SuatChieu_GUI().setVisible(true));
    }
}


package gui;

import com.toedter.calendar.JDateChooser;
import dao.HoaDon_DAO;
import entity.HoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class QuanLyThongKe_GUI extends JFrame {
    private JTable tblHoaDon;
    private DefaultTableModel modelHoaDon;
    private JRadioButton rdoNgay, rdoKhoangNgay, rdoThang;
    private JDateChooser dateChooser1, dateChooser2;
    private JComboBox<String> cboThang, cboNam;
    private JButton btnThongKe;
    private JLabel lblTongDoanhThu;
    private HoaDon_DAO hoaDonDAO = new HoaDon_DAO();

    public QuanLyThongKe_GUI() {
        setTitle("Thống kê - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(45, 45, 45));
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        rdoNgay = new JRadioButton("Theo ngày");
        rdoKhoangNgay = new JRadioButton("Khoảng ngày");
        rdoThang = new JRadioButton("Theo tháng");
        ButtonGroup group = new ButtonGroup();
        group.add(rdoNgay);
        group.add(rdoKhoangNgay);
        group.add(rdoThang);

        rdoNgay.setBackground(new Color(45, 45, 45));
        rdoNgay.setForeground(Color.WHITE);
        rdoKhoangNgay.setBackground(new Color(45, 45, 45));
        rdoKhoangNgay.setForeground(Color.WHITE);
        rdoThang.setBackground(new Color(45, 45, 45));
        rdoThang.setForeground(Color.WHITE);

        filterPanel.add(rdoNgay);
        filterPanel.add(rdoKhoangNgay);
        filterPanel.add(rdoThang);

        dateChooser1 = new JDateChooser();
        dateChooser1.setDateFormatString("yyyy-MM-dd");
        dateChooser1.setPreferredSize(new Dimension(130, 25));
        dateChooser2 = new JDateChooser();
        dateChooser2.setDateFormatString("yyyy-MM-dd");
        dateChooser2.setPreferredSize(new Dimension(130, 25));

        cboThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) cboThang.addItem(String.valueOf(i));
        cboNam = new JComboBox<>();
        for (int i = 2020; i <= 2030; i++) cboNam.addItem(String.valueOf(i));

        filterPanel.add(dateChooser1);
        filterPanel.add(new JLabel("đến"));
        filterPanel.add(dateChooser2);
        filterPanel.add(cboThang);
        filterPanel.add(cboNam);

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setBackground(new Color(0, 120, 215));
        btnThongKe.setForeground(Color.WHITE);
        filterPanel.add(btnThongKe);

        main.add(filterPanel, BorderLayout.NORTH);

        String[] cols = {"Mã HĐ", "Ngày lập", "Nhân viên", "Tổng tiền", "Ghi chú"};
        modelHoaDon = new DefaultTableModel(cols, 0);
        tblHoaDon = new JTable(modelHoaDon);
        tblHoaDon.setRowHeight(22);
        tblHoaDon.setBackground(new Color(60, 60, 60));
        tblHoaDon.setForeground(Color.WHITE);
        tblHoaDon.setSelectionBackground(new Color(0, 120, 215));

        tblHoaDon.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 3) setHorizontalAlignment(SwingConstants.RIGHT);
                c.setBackground(isSelected ? new Color(0, 120, 215) : new Color(60, 60, 60));
                c.setForeground(Color.WHITE);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tblHoaDon);
        scroll.getViewport().setBackground(new Color(45, 45, 45));
        main.add(scroll, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottom.setBackground(new Color(45, 45, 45));
        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTongDoanhThu.setForeground(Color.WHITE);
        pnlBottom.add(lblTongDoanhThu);
        main.add(pnlBottom, BorderLayout.SOUTH);

        ActionListener updateFilter = e -> updateFilterUI();
        rdoNgay.addActionListener(updateFilter);
        rdoKhoangNgay.addActionListener(updateFilter);
        rdoThang.addActionListener(updateFilter);
        updateFilterUI();

        btnThongKe.addActionListener(e -> thongKeHoaDon());

        return main;
    }

    private void updateFilterUI() {
        boolean ngay = rdoNgay.isSelected();
        boolean khoangNgay = rdoKhoangNgay.isSelected();
        boolean thang = rdoThang.isSelected();

        dateChooser1.setEnabled(ngay || khoangNgay);
        dateChooser2.setEnabled(khoangNgay);
        cboThang.setEnabled(thang);
        cboNam.setEnabled(thang);
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

    private void thongKeHoaDon() {
        try {
            modelHoaDon.setRowCount(0);
            double tongDoanhThu = 0;
            List<HoaDon> dsHoaDon;

            if (rdoNgay.isSelected()) {
                Date ngay = dateChooser1.getDate();
                LocalDate localDate = ngay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                dsHoaDon = hoaDonDAO.getHoaDonTheoNgay(localDate.toString());
            } else if (rdoKhoangNgay.isSelected()) {
                Date tu = dateChooser1.getDate();
                Date den = dateChooser2.getDate();
                LocalDate tuNgay = tu.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate denNgay = den.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                dsHoaDon = hoaDonDAO.getHoaDonTheoKhoangNgay(tuNgay, denNgay);
            } else {
                int thang = cboThang.getSelectedIndex() + 1;
                int nam = Integer.parseInt((String) cboNam.getSelectedItem());
                dsHoaDon = hoaDonDAO.getHoaDonTheoThang(thang, nam);
            }

            for (HoaDon hd : dsHoaDon) {
                modelHoaDon.addRow(new Object[]{
                        hd.getMaHoaDon(),
                        hd.getNgayLap(),
                        hd.getTenNV(),
                        String.format("%,.0f VNĐ", hd.getTongTien()),
                        hd.getGhiChu()
                });
                tongDoanhThu += hd.getTongTien();
            }

            lblTongDoanhThu.setText("Tổng doanh thu: " + String.format("%,.0f", tongDoanhThu) + " VNĐ");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thống kê: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyThongKe_GUI().setVisible(true));
    }
}

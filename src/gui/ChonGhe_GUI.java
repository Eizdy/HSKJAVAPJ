package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChonGhe_GUI extends JFrame {
    private Set<String> gheDaChon = new HashSet<>();
    private final int soHang = 5;
    private final int soCot = 10;

    public ChonGhe_GUI(String tenPhim) {
        setTitle("Chọn ghế - " + tenPhim);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelGhe = new JPanel(new GridLayout(soHang, soCot, 5, 5));
        panelGhe.setBorder(BorderFactory.createTitledBorder("Sơ đồ ghế"));

        for (char hang = 'A'; hang < 'A' + soHang; hang++) {
            for (int cot = 1; cot <= soCot; cot++) {
                String maGhe = hang + String.valueOf(cot);
                JButton btn = new JButton(maGhe);
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setFocusPainted(false);

                // Giả lập ghế đã bán
                boolean daBan = Math.random() < 0.1;
                if (daBan) {
                    btn.setEnabled(false);
                    btn.setBackground(Color.DARK_GRAY);
                } else {
                    btn.addActionListener(e -> toggleGhe(btn, maGhe));
                }

                panelGhe.add(btn);
            }
        }

        JButton btnDatVe = new JButton("Đặt vé");
        btnDatVe.addActionListener(e -> datVe());

        add(panelGhe, BorderLayout.CENTER);
        add(btnDatVe, BorderLayout.SOUTH);
    }

    private void toggleGhe(JButton btn, String maGhe) {
        if (gheDaChon.contains(maGhe)) {
            gheDaChon.remove(maGhe);
            btn.setBackground(Color.LIGHT_GRAY);
        } else {
            gheDaChon.add(maGhe);
            btn.setBackground(Color.GREEN);
        }
    }

    private void datVe() {
        if (gheDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn ghế nào.");
        } else {
            JOptionPane.showMessageDialog(this, "Đặt vé thành công cho ghế: " + String.join(", ", gheDaChon));
            dispose(); // hoặc tiếp tục mở giao diện thanh toán
        }
    }
}

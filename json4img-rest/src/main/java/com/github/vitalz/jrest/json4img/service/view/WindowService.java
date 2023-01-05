package com.github.vitalz.jrest.json4img.service.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class WindowService implements IWindowService {
    private static final Logger log = LoggerFactory.getLogger(WindowService.class);
    private volatile JFrame jFrame; // a Singleton instance, volatile is to sync cached instances by every core of a multi-core CPU

    public WindowService() {
        log.info("WindowsService is initializing...");
    }

    private JFrame getJFrame() {
        if (this.jFrame == null) {
            synchronized (this) {
                if (jFrame == null) {
                    log.info("Creating JFrame...");
                    this.jFrame = new JFrame();
                    this.jFrame.setLocationRelativeTo(null);
                    log.info("JFrame has been created.");
                }
            }
        }
        return jFrame;
    }

    @Override
    public void display(BufferedImage image) {

        JFrame frame = getJFrame();
        frame.getContentPane().removeAll(); // clear up previous (image)
        frame.getContentPane().revalidate();

        frame.setTitle("Json4Img");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Dimension frameDimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize()); // display at a full screen
//        Dimension frameDimension =  new Dimension(image.getWidth(), image.getHeight() + 20);
        log.debug("Setting '{}' frame size to [{} x {}].", frame.getTitle(), frameDimension.getWidth(), frameDimension.getHeight());
        frame.setSize(frameDimension);
        log.debug("When the image size is [{} x {}].", image.getWidth(), image.getHeight());

        JPanel imgJPanel = new JPanel();
        imgJPanel.setLayout(new BorderLayout());
        imgJPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(imgJPanel);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

        log.debug("JFrame '{}' has been repainted", frame.getTitle());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

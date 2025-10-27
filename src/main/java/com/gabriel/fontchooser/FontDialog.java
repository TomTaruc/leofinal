package com.gabriel.fontchooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import com.gabriel.fontchooser.util.ResourceBundleUtil;

/**
 * A dialog containing a {@code FontChooser} as well as OK and Cancel buttons.
 *
 * @author Christos Bohoris
 */
public class FontDialog extends JDialog {

    private static final long serialVersionUID = -5545636367279574840L;

    public static void showDialog(Component component) {
        FontDialog dialog = new FontDialog((Frame) null, "Select Font", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSelectedFont(component.getFont());
        dialog.setVisible(true);
        if (!dialog.cancelSelected) {
            component.setFont(dialog.getSelectedFont());
        }
    }

    private final com.gabriel.fontchooser.FontChooser chooser = new FontChooser();

    private final JButton cancelButton = new JButton();

    private final JButton okButton = new JButton();

    private final ResourceBundle bundle = ResourceBundle.getBundle("FontDialog");

    private final ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil(bundle);

    public FontDialog() {
        initDialog();
    }

    public FontDialog(Frame owner) {
        super(owner);
        initDialog();
    }

    public FontDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initDialog();
    }

    public FontDialog(Frame owner, String title) {
        super(owner, title);
        initDialog();
    }

    public FontDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initDialog();
    }

    public FontDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initDialog();
    }

    public FontDialog(Dialog owner) {
        super(owner);
        initDialog();
    }

    public FontDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        initDialog();
    }

    public FontDialog(Dialog owner, String title) {
        super(owner, title);
        initDialog();
    }

    public FontDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initDialog();
    }

    public FontDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initDialog();
    }

    public FontDialog(Window owner) {
        super(owner);
        initDialog();
    }

    public FontDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        initDialog();
    }

    public FontDialog(Window owner, String title) {
        super(owner, title);
        initDialog();
    }

    public FontDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        initDialog();
    }

    public FontDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        initDialog();
    }

    private boolean cancelSelected;

    private void initDialog() {
        initComponents();
        getRootPane().setDefaultButton(okButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelSelected = true;
            }
        });
        pack();
    }

    private void initComponents() {

        JPanel chooserPanel = new JPanel();
        chooserPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 11));
        chooserPanel.setLayout(new BorderLayout(0, 12));
        chooserPanel.add(chooser);
        add(chooserPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 6, 6));
        controlPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        add(controlPanel, BorderLayout.PAGE_END);

        okButton.setMnemonic(resourceBundleUtil.getFirstChar("action.ok.mnemonic"));
        okButton.setText(bundle.getString("action.ok"));
        okButton.addActionListener(event -> dispose());
        controlPanel.add(okButton);

        cancelButton.setMnemonic(resourceBundleUtil.getFirstChar("action.cancel.mnemonic"));
        cancelButton.setText(bundle.getString("action.cancel"));
        cancelButton.addActionListener(event -> {
            cancelSelected = true;
            dispose();
        });
        controlPanel.add(cancelButton);
    }

    public Font getSelectedFont() {
        return chooser.getSelectedFont();
    }

    public void setSelectedFont(Font font) {
        chooser.setSelectedFont(font);
    }

    public boolean isCancelSelected() {
        return cancelSelected;
    }

    FontChooser getFontChooser(){
        return chooser;
    }

    public  void setPreviewText(String text) {
        chooser.setPreviewText(text);
    }

    public String getPreviewText(){
        return chooser.getPreviewText();
    }
}

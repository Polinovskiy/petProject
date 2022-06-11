package com.mypackage.helpers;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooser {
    public String fileChoose(){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HyperText Markup Language (*.html)", "html"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(null);
        String path="";
        if (result == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Выбран файл:"+path);
        }
        else {
            JOptionPane.showMessageDialog(null, "Файл не выбран");
            System.out.println("Файл не выбран");
            System.exit(1);
        }
        return path;
    }

}

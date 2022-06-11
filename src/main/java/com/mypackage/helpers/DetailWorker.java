package com.mypackage.helpers;

import com.mypackage.model.Detail;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DetailWorker {
    //объединяем детали по материалу и размерам
    public void combineEquals(ArrayList<Detail> detailsList) {
        for (int current = 0; current < detailsList.size(); current++) {
            Detail curDetail = detailsList.get(current);
            for (int next = current + 1; next < detailsList.size(); next++) {
                Detail nextDetail = detailsList.get(next);
                if (curDetail.equals(nextDetail)) {
                    curDetail.combine(nextDetail);
                    detailsList.remove(next);
                    next--;
                }
            }
            System.out.println(curDetail.toString());
        }
    }

    private void deleteFileIfExist(ArrayList<Detail> detailsList){
            File file = new File("Детали.xlsx");
            try {
                boolean result = Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
            catch (IOException ex){
                JOptionPane.showMessageDialog(null, "Не могу удалить файл!\n"+file.getName()+" занят другой программой");
                System.out.println("Ошибка удаления файла - "+file.getName());
                ex.printStackTrace();
            }
    }


    public void writeDetailsByFiles(ArrayList<Detail> detailsList) {
        deleteFileIfExist(detailsList);
        String tempMaterial="";
        int tempSize=0;
        File file;
        XSSFSheet sheet=null;
        Workbook wbook=new Workbook();
        file = createFile(tempMaterial+"-"+tempSize);
        for (Detail detail:detailsList){
            if (!detail.getMaterial().equals(tempMaterial)||detail.getSize()!=tempSize){
                tempMaterial=detail.getMaterial();
                tempSize=detail.getSize();
                sheet= wbook.newSheet(wbook,tempMaterial+"-"+tempSize);
            }
            wbook.addLine(sheet,detail);
            try (FileOutputStream outFile=new FileOutputStream(file)) {
                wbook.write(outFile);
            } catch (IOException e) {
                System.out.println("An error occurred.");
                JOptionPane.showMessageDialog(null, "Ошибка записи в файл");
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "Обработка прошла успешно\nФайл "+file.getName()+" создан");
    }


    private void closeFileWriter(FileWriter fileWriter){
        if (fileWriter!=null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    private File createFile(String name) {
        File file=null;
        try {
            file = new File("Детали.xlsx");
            if (file.createNewFile()) {
                System.out.println("Файл создан: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания файла");
            JOptionPane.showMessageDialog(null, "Ошибка создания файла "+file.getName());
            e.printStackTrace();
        }
            return file;
    }


}

package com.mypackage;

import com.mypackage.helpers.DetailWorker;
import com.mypackage.helpers.FileChooser;
import com.mypackage.helpers.HtmlReader;
import com.mypackage.model.Detail;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        HtmlReader htmlReader= new HtmlReader();
        String pathToFile=new FileChooser().fileChoose();
        Document doc= htmlReader.read(pathToFile);
        doc = htmlReader.changeFile(pathToFile,doc);


        ArrayList<Detail> detailsList= new ArrayList<Detail>();
        Elements elements= doc.getElementsByClass("cellule_menu");
        for (Element element : elements) {
            Detail detail = new Detail();
            detail.setName(htmlReader.findInTable(element,0,0));
            detail.setMaterial(htmlReader.findInTable(element,4,1));
            detail.setSize(htmlReader.findInTable(element,3,3));
            detail.setLength(htmlReader.findInTable(element,3,1));
            detail.setWidth(htmlReader.findInTable(element,3,2));
            detail.setCount(htmlReader.findInTable(element,1,1));
            detailsList.add(detail);
            System.out.println(detail.toString());
        }
        detailsList.sort(Detail::compareTo);
        if (detailsList.size()>0){
            System.out.println("============================== Объединяю одинаковые детали =======================================");
            new DetailWorker().combineEquals(detailsList);
        }
        else {
            JOptionPane.showMessageDialog(null, "Неверная структура файла");
            System.out.println("Неверная структура файла");
            System.exit(1);
        }

        //запись деталей по файлам
        new DetailWorker().writeDetailsByFiles(detailsList);
    }
}

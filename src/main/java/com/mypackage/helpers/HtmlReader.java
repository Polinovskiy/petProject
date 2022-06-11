package com.mypackage.helpers;

import com.mypackage.model.Detail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class HtmlReader {

    public Document read(String path){
        File htmlFile = new File(path);
        Document doc=null;
        try {
            doc = Jsoup.parse(htmlFile, "UTF-8");
        }
        catch (IOException ioe){
            JOptionPane.showMessageDialog(null, "Ошибка при чтении файла\npath");
            System.out.println(ioe);
        }

        return doc;
    }

    public String findInTable(Element element, int tr, int td){
       return element.parent().getElementsByTag("tr").get(tr).getElementsByTag("td").get(td).text();
    }

    public Document changeFile(String path,Document doc){
        Boolean changeSuccess=detailNameChanger(doc);
        String comstr=commentingHrefs(doc.toString());
        comstr=renaming(comstr);
        if (changeSuccess||!doc.toString().equals(comstr)) {
            documentWriter(path, comstr);
        }
        return Jsoup.parse(comstr);
    }

    public String commentingHrefs(String strHtml){
        return strHtml.replaceAll("(<a href.*>)(<img.*>)","<!--$1-->$2");
    }
    public String renaming(String strHtml){
        return strHtml.replaceAll("\\^.*\\("," (");
    }

    public boolean detailNameChanger(Document doc) {
        Boolean success=false;
        Elements elements = doc.getElementsByClass("cellule_menu");
        HtmlReader htmlReader = new HtmlReader();
        String detailName;
        for (Element element : elements) {
            detailName = htmlReader.findInTable(element, 0, 0);
            element.parent().getElementsByTag("tr").get(0).getElementsByTag("td").get(0).text(detailName);
        }
        return success;
    }

    public Boolean documentWriter(String pathToFile, String strHtml){
        Boolean success=true;
        OpenOption[] oo4 = new OpenOption[] {StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE};
        Path path4 = Paths.get(pathToFile);
        try (OutputStream out = Files.newOutputStream(path4, oo4);
             PrintStream outps = new PrintStream(out,true,"UTF-8");) {
            outps.print(strHtml);
        } catch (IOException e) {
            success=false;
            e.printStackTrace();
        }
        return success;
    }
}

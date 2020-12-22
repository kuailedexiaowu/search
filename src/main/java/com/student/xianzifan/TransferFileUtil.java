package com.student.xianzifan;

import java.io.*;

public class TransferFileUtil {

    public static Article read(File file){
        Article article = new Article();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                line =line.trim();
                if(line.startsWith("<DOC")){
                    article.setId(parseId(line));
                }else if(line.startsWith("<HEADLINE>")){
                    StringBuilder head = new StringBuilder();
                    head.append(line);
                    if(!line.contains("</HEADLINE>")){
                        while ((line = bufferedReader.readLine()) != null){
                            line =line.trim();
                            head.append(line);
                            if(line.contains("</HEADLINE>")){
                                break;
                            }
                        }
                    }
                    article.setHeadLine(parseHeadLine(head.toString()));
                }else if(line.startsWith("<DATELINE>")){
                    StringBuilder date = new StringBuilder();
                    date.append(line);
                    if(!line.contains("</DATELINE>")){
                        while ((line = bufferedReader.readLine()) != null){
                            line =line.trim();
                            date.append(line);
                            if(line.contains("</DATELINE>")){
                                break;
                            }
                        }
                    }
                    article.setDateLine(parseDateLine(date.toString()));
                }else if(line.startsWith("<STNO>")){
                    line = bufferedReader.readLine();
                    content.append(line);
                }
            }
            article.setContent(content.toString());
        }catch (IOException e){

        }
        return article;

    }

    private static String parseId(String line){
        return line.replace("<DOC id=\"", "").replace("\" type=\"story\">", "");
    }

    private static String parseHeadLine(String line){
        return line.replace("</HEADLINE>", "").replace("<HEADLINE>", "").trim();
    }

    private static String parseDateLine(String line){
        return line.replace("</DATELINE>", "").replace("<DATELINE>", "").trim();
    }

    public static void main(String[] args){
        Article article = read(new File("C:\\Users\\kai.kai\\Desktop\\客户3\\TrainingReleased_CS\\STNO-UNICODE\\N02\\za2001_001865.stno"));
        System.out.println(article);
    }

}

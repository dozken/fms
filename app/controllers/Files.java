package controllers;

import org.apache.commons.io.FileUtils;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.upload_example;

import java.io.*;

/**
 * Created by RSA on 18.07.2014.
 */
public class Files extends Controller {
    public static Result showExample(){
        return ok(upload_example.render());
    }
    public static Result saveUploadedFile(){
        try{
            DynamicForm form = Form.form().bindFromRequest();
            //int count = Integer.parseInt(form.get("count"));
            //for(int i=0;i<count;i++){
                Http.MultipartFormData body = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart picture = body.getFile("file");
                if (picture != null) {
                    String fileName = picture.getFilename();
                    String ext = getExtension(fileName);
                    String contentType = picture.getContentType();
                    File file = picture.getFile();
                    String Root = Play.application().path().getPath();
                    //System.out.println("project root: "+projectRoot);
                    String myUploadPath = Play.application().configuration().getString("myUploadPath");
                    File f = new File(Root +"/" + myUploadPath);

                    f.mkdirs();

                    myUploadPath = f.getPath()+"/";
                    File number = new File(myUploadPath, "DO NOT EDIT!.txt");
                    if(!number.exists()){
                        PrintWriter pw2 = new PrintWriter(new FileWriter(number));
                        pw2.print(0);
                        pw2.flush();
                        pw2.close();
                    }
                    BufferedReader bf = new BufferedReader(new FileReader(number));
                    int howmany = Integer.parseInt(bf.readLine());
                    String newName = "file"+(++howmany);
                    PrintWriter pw = new PrintWriter(new FileWriter(number));
                    pw.print(howmany);
                    pw.flush();
                    pw.close();
                    file.renameTo(new File(myUploadPath, newName+"."+ext));

                    bf.close();
                    return ok("File uploaded");
                } else {
                    flash("error", "Missing file");
                    return ok();//redirect(routes.Application.index());
                }
            //}
        }catch(Exception e){
            e.printStackTrace();
            return ok();
        }
    }
    public static String getExtension(String filename){
        String extension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
        return extension;
    }
}
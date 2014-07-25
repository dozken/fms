package controllers;

import models.AuthorisedUser;
import models.File;
import models.Folder;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.upload_example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RSA on 18.07.2014.
 */
public class Drive extends Controller {
    /*
        These functions are already done:
        * create folder
        * move file
        * delete file
        * rename file
        * download file
        * download files
        * attach file from drive
        * upload file
     */
    //public static Result showExample(){ return ok(upload_example.render()); }
    public static Result createFolder(String name){
        try{
            Application.restrict();
            AuthorisedUser user = Users.getConnectedUser();
            Folder search = Folder.findByInfo(user, name);
            if(search!=null){
                Folder folder = new Folder();
                folder.name = name;
                folder.time = new Date().getTime();
                folder.user = user;
                folder.save();
                return ok("success");
            }
            else
                return ok("exists");
        }catch(Exception e){
            e.printStackTrace();
            return ok("exception");
        }
    }
    /*public static Result moveFileToTrash(String pathFrom, String pathTo, long fileId){
        try{
            Application.restrict();
            AuthorisedUser user = Users.getConnectedUser();
            File file = File.find.byId(fileId);
            java.io.File toMove = new java.io.File(pathFrom+"/"+file.pName);
            String Root = Play.application().path().getPath();
            boolean res = addFileToDir(toMove, Root+user.email+pathTo);
            if(res == true)
                return ok("success");
            else
                return ok("false");
        }catch(Exception e){
            e.printStackTrace();
            return ok("exception");
        }
    }*/
    public static Result moveFile(long folderTo, long fileId, String option){
        try{
            Application.restrict();
            AuthorisedUser user = Users.getConnectedUser();
            File toMove = File.find.byId(fileId);
            Folder to = Folder.find.byId(folderTo);
            toMove.folder = to;
            File search = File.findByInfo(user, to, toMove.name);
            if(search!=null) {
                if (option.equalsIgnoreCase("replace")) {
                    Drive.delete(search);
                    search.delete();
                }
                else if (option.equalsIgnoreCase("new"))
                    toMove.name += "*";
            }
            toMove.save();
            return ok("success");
        }catch(Exception e){
            e.printStackTrace();
            return ok("exception");
        }
    }
    public static void delete(File file){
        AuthorisedUser user = Users.getConnectedUser();
        java.io.File toDelete = new java.io.File(Application.getRootPath()+"/"+file.user.email+"/"+file.path);
        toDelete.delete();
    }

    public static Result saveUploadedFile(/*long toFolder*/){
        try{
            Application.restrict();
            AuthorisedUser user = Users.getConnectedUser();
            //DynamicForm form = Form.form().bindFromRequest();
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("file");
            if (picture != null) {
                java.io.File file = picture.getFile();
                String Root = Application.getRootPath();
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String current = sdf.format(now);
                String ext = getExtension(file.getName());
                boolean res = addFileToDir(file, Root+"/"+user.email+"/"+current+"/", now.getTime()+"."+ext);
                if(res == true) {
                    Folder folder = new Folder();//Folder.find.byId(toFolder);
                    File ff = new File();
                    ff.folder = folder;
                    ff.name = file.getName();
                    ff.path = current+"/"+now.getTime()+"."+ext;
                    ff.time = new Date().getTime();
                    ff.type = picture.getContentType();
                    ff.user = user;
                    ff.trashed = false;
                    ff.save();
                    return ok("success");
                }
                else
                    return ok("error");
            } else {
                return ok("missing");
            }
        }catch(Exception e){
            e.printStackTrace();
            return ok();
        }
    }
    public static Result saveAttachedFile(long toMail){

        return TODO;
    }
    public static Result attachFromDrive(long file){
        return TODO;
    }
    public static Result shareFile(){
        DynamicForm form = Form.form().bindFromRequest();
        return TODO;
    }
    public static Result delete(long file){
        try{
            File toDelete = File.find.byId(file);
            toDelete.trashed = true;
            return ok("success");
        }catch (Exception e){
            e.printStackTrace();
            return ok("exception");
        }
    }
    public static Result deleteFiles(){
        try{

        }catch(Exception e){
            e.printStackTrace();
            return ok("exception");
        }
        DynamicForm form = Form.form().bindFromRequest();
        int count = Integer.parseInt(form.get("count"));
        File [] file = new File [count];
        for(int i=0;i<count;i++)
            file[i] = File.find.byId(Long.parseLong(form.get("file_"+i)));

        return TODO;
    }
    public static String getExtension(String filename){
        String extension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
        return extension;
    }
    public static boolean addFileToDir(java.io.File file, String path, String newName){
        try{
            java.io.File f = new java.io.File(path);
            f.mkdirs();
            file.renameTo(new java.io.File(path, newName));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
package com.zb.jnlxc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.*;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class MouldFileGenerate {

    public static void main(String[] args) throws Exception {

        VelocityEngine ve = new VelocityEngine();
        Properties p =new Properties();
        p.put(Velocity.FILE_RESOURCE_LOADER_PATH, "D:\\workspaces\\services\\zlhw-parent\\JNLXC2.0\\src\\test\\resources");
        ve.init(p);

        Template t = ve.getTemplate("mouldTemplate.vm","utf-8");
        String path1 = "D:\\workspaces\\services\\zlhw-parent\\JNLXC2.0\\src\\main\\webapp\\content\\newmould";
        File dir1 = new File(path1);
        String path2 = "D:\\workspaces\\services\\zlhw-parent\\JNLXC2.0\\src\\main\\webapp\\content\\mouldprocess";
        File dir2 = new File(path2);
        for(File file:dir1.listFiles()){
           change(file,t);
        }
        for(File file:dir2.listFiles()){
            change(file,t);
        }
    }

    public static void change(File file,Template t)throws Exception{
        VelocityContext context = new VelocityContext();
        context.put("fileName", file.getName().replaceAll("\\.vm",""));
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        System.out.println("" +
                "    @ResponseBody\n" +
                "    @RequestMapping(\"/"+file.getName().replaceAll("\\.vm","")+"\")\n" +
                "    public void "+file.getName().replaceAll("\\.vm","")+"(String taskId, Remarks remarks,@ModelAttribute(\"user\") Admin user)  {\n" +
                "        mouldService."+file.getName().replaceAll("\\.vm","")+"(taskId, remarks,user);\n" +
                "    }\n");
        FileOutputStream of = new FileOutputStream(file);
        of.write(writer.toString().getBytes("utf-8"));
        of.flush();
        of.close();
    }

    public void getSQL(){

    }


}

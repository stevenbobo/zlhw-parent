package com.zb.jnlxc.miniui;
import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-9-26
 * Time: 上午12:03
 * To change this template use File | Settings | File Templates.
 */
public class MiniuiCrash {
    public String prase(String str,int i){
            String[] args = str.split("\\|");
        StringBuilder builder = new StringBuilder();
            for (String s:args){
                int value = Integer.parseInt(s);
                char real = (char)(value-i);
                builder.append(real);
            }
        String result = builder.toString();
        if(result.contains("到期")){
            System.err.println(str);
            System.out.println(builder.toString());
        }
        return builder.toString();
    }




    public String dePrase(String str){

        String result ="";
        for(Byte bb:str.getBytes()){
            result+=bb+"|";
        }
        return result;
    }
    @Test
    public void testDePrase(){
        String str = "setTimeout(function(){}, 600000);";
        String result = dePrase(str);
        Assert.assertEquals("115|101|116|84|105|109|101|111|117|116|40|102|117|110|99|116|105|111|110|40|41|123|125|44|32|54|48|48|48|48|48|41|59|",result);
        System.out.println(result);
        prase(result, 0);
    }

    @Test
    public void test1(){
       String str= "70|119|119|90|60|119|72|113|128|121|110|127|116|122|121|43|51|52|43|134|125|112|127|128|125|121|43|127|115|116|126|57|115|112|108|111|112|125|94|127|132|119|112|70|24|21|43|43|43|43|136|21";
       prase(str,11);
    }


    @Test
    public void praceMiniUiJS(){
        try {
            List<AA> list = new ArrayList();
            Pattern pattern = Pattern.compile("\\|\\d{1,3}\\|");
                InputStreamReader read = new InputStreamReader(
                        MiniuiCrash.class.getResource("/miniui_zb.js").openStream(),"utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                boolean printNext = false;
                AA lastobj =null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if (printNext){
                        lastobj.num= getNum(lineTxt);
                        printNext = false;
                    }
                    if (pattern.matcher(lineTxt).find()){
                        if(lineTxt.contains("="))
                            printNext = true;
                        int index = lineTxt.indexOf("\"");
                        int last = lineTxt.lastIndexOf("\"");
                        lastobj = new AA(getNum(lineTxt),lineTxt.substring(index+1,last));
                        list.add(lastobj);
                    }
                }
            for (AA aa:list){
                prase(aa.val, aa.getNum());
            }
                read.close();
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public String getNum(String str){
        int start = str.indexOf(",");
        int end = str.indexOf("))");
        if (start>-1&&end>-1)
            return str.substring(start+2,end);
        else
            return "";
    }
    class AA{
       public String num;
       public String val;

        public int getNum(){
             return Integer.parseInt(num);
        }

        AA(String num, String val) {
            this.num = num;
            this.val = val;
        }
    }

    @Test
    public void testMatch(){
        Pattern pattern = Pattern.compile("\\|\\d*\\|");
        String str = "60|80|80|112|80|49|62|103|118|111|100|117|106|112|111|33|41|119|98|109|118|102|42|33|124|96|96|110|106|111|106|96|116|102|117|68|112|111|117|115|112|109|116|41|119|98|109|118|102|45|117|105|106|116|47|80|80|49|80|49|109|45|117|105|106|116|42|60|14|11|33|33|33|33|126|11";
        System.out.print(pattern.matcher(str).find());
    }

}

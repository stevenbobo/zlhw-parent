package com.zb.util;
/*
 * BMP文件由文件头、位图信息头、颜色信息和图形数据四部分组成。
 * BMP文件头数据结构含有BMP文件的类型、文件大小和位图起始位置等信息。
 * 
 */

import java.io.*;
import java.net.URL;
import java.util.Vector;


public class DwgToBitmap {
	
	private String dwgFilePath="";
	private String outFilePath="";
	private FileOutputStream outfs=null;
	//
	private byte bfType [] = {	(byte)'B', (byte)'M'}; //位图文件的类型，必须为BM
	private int bfSize = 0; // 位图文件的大小，以字节为单位
	private int bfReserved1 = 0; // 位图文件保留字，必须为
	private int bfReserved2 = 0; // 位图文件保留字，必须为0
	private final static int BITMAPFILEHEADER_SIZE = 14; 
	private final static int BITMAPINFOHEADER_SIZE = 40; 
	private int bfOffBits = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE+1024; 
	// 位图数据的起始位置，以相对于位图,文件头的偏移量表示，以字节为单位

	//位图文件头 
	//BMP位图信息头数据用于说明位图的尺寸等信息。
	private byte bitmapInfoHeader [] = new byte [40]; 
	private int biSize = BITMAPINFOHEADER_SIZE; // 本结构所占用字节数
//	private int biWidth = 0; //位图的宽度，以像素为单位
//	private int biHeight = 0; // 位图的高度，以像素为单位
	private int biPlanes = 1; // 目标设备的级别，必须为1
	private int biBitCount = 24; // 每个像素所需的位数，必须是1(双色),
	// 4(16色)，8(256色)或24(真彩色)之一

	private int biCompression = 0; 
	// 位图压缩类型，必须是 0(不压缩),
	// 1(BI_RLE8压缩类型)或2(BI_RLE4压缩类型)之一

	private int biSizeImage = 0x030000; 
	// 位图的大小，以字节为单位
	private int biXPelsPerMeter = 0x0; // 位图水平分辨率，每米像素数
	private int biYPelsPerMeter = 0x0; // 位图垂直分辨率，每米像素数
	private int biClrUsed = 0; 
	private int biClrImportant = 0; 
	private int linepad = 0; 
	
	//--- Bitmap raw data
	private int bitmap []; 
	public DwgToBitmap(String filePath,String oFilePath){
		dwgFilePath=filePath;
		outFilePath=oFilePath;
//		biWidth=width;
//		biHeight=height;
	}

	public static void main(String[] args) {
		DwgToBitmap test=new DwgToBitmap("D:/tomcat-6.0/webapps/JNLXC/map/f的分工.dwg","1.bmp");
		test.convertToBitmap();
	}
	public void convertToBitmap(){
		
		//增加解码的速度，同时对于的DWG文件，防止内存溢出
		try{
			//取得DWG文件,并得到文件流
			File file = new File(dwgFilePath);
			Vector v= new Vector();
			File file2 = new File(outFilePath);
			
			FileInputStream fs = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fs);
			BufferedInputStream bis = new BufferedInputStream(fs);
			
			outfs = new FileOutputStream(file2);
			boolean eof=false;
			long count=0;
			int bmpDesc=0;//缩略图描述块的位置
			int bmpType=0;
			int bmpPos=0;
			int bmpLen=0;
			int bmpBitCount=0;
			Integer[] IntValue32 = new Integer[4];
			Integer[] IntValue16 = new Integer[2];
			
			//读取位图的文件头信息，并标示位图的开始位置
			while(!eof){
				int input = bis.read();
				//System.out.println(input);
				if(input == -1)
					eof = true;
				else{
					if(count>=13&&count<17){
						// 第13到17字节指示缩略图描述块的位置 
						Integer IntTmp= new Integer(input);
						IntValue32[(int) (count-13)]=IntTmp;
					}
					else if(count==17){
						bmpDesc=readIntBit(IntValue32,4);
						//System.out.println(bmpDesc+ " "+ count);
					}else if(bmpDesc!=0&&count==(bmpDesc+30)){
						//第31字节为缩略图格式信息，2   为BMP格式，3为WMF格式
						bmpType=input;
						//System.out.println(bmpType+ " "+ count);
					}else if(bmpDesc!=0&&count>=(bmpDesc+31)&&count<(bmpDesc+35)){
						//从第31数据取出4个字节，31，32，33，34，DWG文件保存的位图所在位置 
						 
						IntValue32[(int) (count-bmpDesc-31)]=new Integer(input);
					}
					else if(bmpDesc!=0&&count==(bmpDesc+35)){
						bmpPos=readIntBit(IntValue32,4);
						
						//System.out.println(bmpPos+ " "+ count);
						//从35，36，37，38，
						IntValue32[(int) (count-bmpDesc-35)]=new Integer(input);
						}
					else if(bmpDesc!=0&&count>=(bmpDesc+36)&&count<(bmpDesc+39)){
						IntValue32[(int) (count-bmpDesc-35)]=new Integer(input);
						
					}
					else if((bmpDesc!=0&&count==(bmpDesc+39))){
						bmpLen=readIntBit(IntValue32,4);
						//System.out.println(bmpLen+ " "+ count);
					}
					else if(bmpPos!=0&&count>=(bmpPos+14)&&count<(bmpPos+16)){
						IntValue16[(int) (count-bmpPos-14)]=new Integer(input);
					}
					else if(bmpPos!=0&&count==(bmpPos+16)){
						bmpBitCount=readIntBit(IntValue16,2);
						//System.out.println(bmpBitCount+" "+count);
					}
					else if(bmpPos!=0&&count==(bmpPos-1)){
						bis.mark(1);
					}
					else if(bmpPos!=0&&count>(bmpPos+16)){
						eof=true;
					}
				}
				count++;
			}
			if(bmpBitCount<9){
				//bfSize=54 + 4 * 256 + bmpLen;
				bfSize=(int) (54 + 4 * Math.pow(2, bmpBitCount) + bmpLen);
			
			}
			else{
				bfSize=54+bmpLen;
			}
			
			int pos=13;
			//开始读取文件，如果读取完缩略图后，系统将会停止阅读
			byte[] b=new byte[bmpLen];
			bis.reset();
			bis.read(b);
			outfs.write(bfType);
			outfs.write(intToDWord(bfSize));
			outfs.write(intToWord(bfReserved2));
			outfs.write(intToWord(bfReserved2));
			outfs.write(intToDWord(bfOffBits));
			outfs.write(b);
			dis.close();
			fs.close();
			outfs.close();
			//file.delete();
			
		}catch(FileNotFoundException fnfe){
			System.out.println("文件没有发现!"+fnfe.toString());
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("发生未知错误！"+e.toString());
		}
		
		
	}
	
	public int readIntBit(Integer[] IntByte,int intBit){
		int returnValue=0;
		byte[] bitTmp= new byte[intBit];
		
		for(int i=0;i<intBit;i++){
			bitTmp[i]=IntByte[i].byteValue();
		}
		if(intBit==2){
			returnValue=bytesToInt16(bitTmp);
		}else{
			returnValue=bytesToInt32(bitTmp);
		}
		return returnValue;
	}
	
	public  static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}
	
	public static int bytesToInt16(byte[] bytes){
		int num=0;
		num=((bytes[1] << 8) & 0xFF00) +(bytes[0] & 0xFF);

		return num;
	}
	 public static int bytesToInt32(byte[] bytes) {
		 int num = bytes[0] & 0xFF;
			num |= ((bytes[1] << 8) & 0xFF00);
			num |= ((bytes[2] << 16) & 0xFF0000);
			num |= ((bytes[3] << 24) & 0xFF000000);
			return num;
	}

	public byte [] intToWord (int parValue) {
			
			byte retValue [] = new byte [2]; 
			
			retValue [0] = (byte) (parValue & 0x00FF); 
			retValue [1] = (byte) ((parValue >> 8) & 0x00FF); 
			
			return (retValue); 
			
	}
		

	public byte [] intToDWord (int parValue) {
			
			byte retValue [] = new byte [4]; 
			
			retValue [0] = (byte) (parValue & 0x00FF); 
			retValue [1] = (byte) ((parValue >> 8) & 0x000000FF); 
			retValue [2] = (byte) ((parValue >> 16) & 0x000000FF); 
			retValue [3] = (byte) ((parValue >> 24) & 0x000000FF); 
			
			return (retValue); 
			
	}
//	public void writeBitmapInfoHeader () {
//
//			try {
//				outfs.write (intToDWord (biSize));
//				outfs.write (intToDWord (biWidth));
//				outfs.write (intToDWord (biHeight));
//				outfs.write (intToWord (biPlanes));
//				outfs.write (intToWord (biBitCount));
//				outfs.write (intToDWord (biCompression));
//				outfs.write (intToDWord (biSizeImage));
//				outfs.write (intToDWord (biXPelsPerMeter));
//				outfs.write (intToDWord (biYPelsPerMeter));
//				outfs.write (intToDWord (biClrUsed));
//				outfs.write (intToDWord (biClrImportant));
//			}
//			catch (Exception wbih) {
//				wbih.printStackTrace ();
//			}
//	}
//	private void writeBitmap (){
//			int i,j,k;
//			int value;
//			byte rgb [] = new byte [3];
//
//			try
//			{	for (i=biHeight-1; i>=0; i--)
//				{	for (j=0; j<biWidth; j++)
//					{
//						value = bitmap [i*biWidth+j];
//						rgb [0] = (byte) (value & 0x000000FF);
//						rgb [1] = (byte) ((value >> 8) & 0x000000FF);
//						rgb [2] = (byte) ((value >> 16) & 0x000000FF);
//						outfs.write (rgb);
//					}
//					for (k=0; k<linepad; k++) outfs.write(0x00);
//				}
//			}
//			catch (Exception wb) {
//				wb.printStackTrace ();
//			}
//
//	}
//	public boolean convertImage ()
//	{
//			bitmap = new int [biWidth * biHeight];
//
//
//			linepad =  4 - ((biWidth * 3) % 4);
//			if (linepad==4) linepad=0;
//			biSizeImage = (biWidth * 3 +linepad) * biHeight;
//			bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;
//
//			return (true);
//	}
	
//	public void convertBigAttToBitmap(TelnetInputStream tis){
//			//FtpDBInfo ftpdb = new FtpDBInfo(session,curdb,docid);
//		try {
//			//取得文件流
//			
//			File file2 = new File(outFilePath);
//			BufferedInputStream bis = new BufferedInputStream(tis);
//			
//			outfs = new FileOutputStream(file2);
//			boolean eof=false;
//			long count=0;
//			int bmpDesc=0;//缩略图描述块的位置
//			int bmpType=0;
//			int bmpPos=0;
//			int bmpLen=0;
//			int bmpBitCount=0;
//			Integer[] IntValue32 = new Integer[4];
//			Integer[] IntValue16 = new Integer[2];
//			//读取位图的文件头信息，并标示位图的开始位置
//			while(!eof){
//				int input = bis.read();
//				//System.out.println(input);
//				if(input == -1)
//					eof = true;
//				else{
//					if(count>=13&&count<17){
//						// 第13到17字节指示缩略图描述块的位置 
//						IntValue32[(int) (count-13)]=new Integer(input);
//					}
//					else if(count==17){
//						bmpDesc=readIntBit(IntValue32,4);
//						//System.out.println(bmpDesc+ " "+ count);
//					}else if(bmpDesc!=0&&count==(bmpDesc+30)){
//						//第31字节为缩略图格式信息，2   为BMP格式，3为WMF格式
//						bmpType=input;
//						//System.out.println(bmpType+ " "+ count);
//					}else if(bmpDesc!=0&&count>=(bmpDesc+31)&&count<(bmpDesc+35)){
//						//从第31数据取出4个字节，31，32，33，34，DWG文件保存的位图所在位置  
//						IntValue32[(int) (count-bmpDesc-31)]=new Integer(input);
//					}
//					else if(bmpDesc!=0&&count==(bmpDesc+35)){
//						bmpPos=readIntBit(IntValue32,4);
//						
//						//System.out.println("开始位置"+bmpPos);
//						//从35，36，37，38，
//						IntValue32[(int) (count-bmpDesc-35)]=new Integer(input);
//						}
//					else if(bmpDesc!=0&&count>=(bmpDesc+36)&&count<(bmpDesc+39)){
//						IntValue32[(int) (count-bmpDesc-35)]=new Integer(input);
//						
//					}
//					else if((bmpDesc!=0&&count==(bmpDesc+39))){
//						bmpLen=readIntBit(IntValue32,4);
//						//System.out.println(bmpLen+ " "+ count);
//					}
//					else if(bmpPos!=0&&count>=(bmpPos+14)&&count<(bmpPos+16)){
//						IntValue16[(int) (count-bmpPos-14)]=new Integer(input);
//					}
//					else if(bmpPos!=0&&count==(bmpPos+16)){
//						bmpBitCount=readIntBit(IntValue16,2);
//						//System.out.println(bmpBitCount+" "+count);
//					}
//					else if(bmpPos!=0&&count==(bmpPos-1)){
//						bis.mark(1);
//					}
//					else if(bmpPos!=0&&count>(bmpPos+16)){
//						//System.out.println("长度："+count);
//						eof=true;
//					}
//				}
//				count++;
//			}
//			if(bmpBitCount<9){
//				//bfSize=54 + 4 * 256 + bmpLen;
//				bfSize=(int) (54 + 4 * Math.pow(2, bmpBitCount) + bmpLen);
//			
//			}
//			else{
//				bfSize=54+bmpLen;
//			}
//			
//			int pos=13;
//			//开始读取文件，如果读取完缩略图后，系统将会停止阅读
//			byte[] b=new byte[bmpLen];
//			//System.out.println(bmpLen);
//			bis.reset();
//			bis.read(b);
//			outfs.write(bfType);
//			outfs.write(intToDWord(bfSize));
//			outfs.write(intToWord(bfReserved2));
//			outfs.write(intToWord(bfReserved2));
//			outfs.write(intToDWord(bfOffBits));
//			outfs.write(b);
//			tis.close();
//			bis.close();
//			outfs.close();	
//		}catch(IOException ioe){
//			System.out.println(ioe.toString());
//		}catch(Exception e){
//		
//			System.out.println(e.toString());
//		}finally{
//			//System.out.println()
//		}	
//	}
}

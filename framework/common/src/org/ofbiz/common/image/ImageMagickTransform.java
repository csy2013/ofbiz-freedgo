package org.ofbiz.common.image;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by tusm on 15/9/10.
 */
public class ImageMagickTransform {


    public static final String module = ImageTransform.class.getName();
    public static final String resource = "CommonErrorUiLabels";

    public ImageMagickTransform() {
    }

    /**
     * 裁剪图片
     *
     * @param imagePath 源图片路径
     * @param newPath 处理后图片路径
     * @param x 起始X坐标
     * @param y 起始Y坐标
     * @param width 裁剪宽度
     * @param height 裁剪高度
     * @return 返回true说明裁剪成功,否则失败
     */
    public static boolean cutImage(String imagePath, String newPath, int x, int y,
                            int width, int height,boolean isGraphicsMagick) {
        boolean flag = false;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            /** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
            op.crop(width, height, x, y);
            op.addImage(newPath);
            ConvertCmd convert = null;
            if(isGraphicsMagick) {
                convert = new ConvertCmd(true);
            }else{
                convert = new ConvertCmd();
            }
            convert.run(op);
            flag = true;
        } catch (IOException e) {
            System.out.println("文件读取错误!");
            flag = false;
        } catch (InterruptedException e) {
            flag = false;
        }  catch (IM4JavaException e) {
            e.printStackTrace();
            flag = false;
        } finally {

        }
        return flag;
    }

    /**
     * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
     *
     * @param imagePath 源图片路径
     * @param newPath 处理后图片路径
     * @param width 缩放后的图片宽度
     * @param height 缩放后的图片高度
     * @return 返回true说明缩放成功,否则失败
     */
    public static void zoomImage(String imagePath, String newPath, Integer width,
                             Integer height,boolean isGraphicsMagick) throws InterruptedException, IOException, IM4JavaException {



            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            if (width == null) {// 根据高度缩放图片
                op.resize(null, height);
            } else if (height == null) {// 根据宽度缩放图片
                op.resize(width);
            } else {
                op.resize(width, height);
              }
                ConvertCmd convert = null;
                op.addImage(newPath);
            if(isGraphicsMagick) {
                  convert = new ConvertCmd(true);
            }else{
                  convert = new ConvertCmd();
            }
            convert.run(op);

    }


    /**
     * 图片旋转
     *
     * @param imagePath 源图片路径
     * @param newPath 处理后图片路径
     * @param degree 旋转角度
     */
    public static boolean rotate(String imagePath, String newPath, double degree,boolean isGraphicsMagick) {
        boolean flag = false;
        try {
            // 1.将角度转换到0-360度之间
            degree = degree % 360;
            if (degree <= 0) {
                degree = 360 + degree;
            }
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.rotate(degree);
            op.addImage(newPath);
            ConvertCmd cmd = null ;
            if(isGraphicsMagick) {
                cmd = new ConvertCmd(true);
            }else{
                cmd = new ConvertCmd();
            }
            cmd.run(op);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 按九宫格位置添加水印
     * @param srcPath       原图片路径
     * @param distPath      新图片路径
     * @param watermarkImg  水印图片路径
     * @param position      九宫格位置[1-9],从上往下,从左到右排序
     * @param x         横向边距
     * @param y         纵向边距
     * @param alpha     透明度
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */
    public static void WatermarkImg(String srcPath,String distPath,String watermarkImg, int position, int x, int y, int alpha,boolean isGraphicsMagick) throws IOException, InterruptedException, IM4JavaException{
        int[] watermarkImgSide = getImageSide(watermarkImg);
        int[] srcImgSide = getImageSide(srcPath);
        int[] xy = getXY(srcImgSide, watermarkImgSide, position, y, x);
        watermarkImg(srcPath,distPath,watermarkImg,watermarkImgSide[0],watermarkImgSide[1],xy[0],xy[1],alpha,isGraphicsMagick);
    }


    private static int[] getImageSide(String imgPath) throws IOException {
        int[] side = new int[2];
        Image img = ImageIO.read(new File(imgPath));
        side[0] = img.getWidth(null);
        side[1] =img.getHeight(null);
        return side;
    }


    /**
     * 添加图片水印
     * @param srcPath       原图片路径
     * @param distPath      新图片路径
     * @param watermarkImg      水印图片路径
     * @param width     水印宽度（可以于水印图片大小不同）
     * @param height    水印高度（可以于水印图片大小不同）
     * @param x     水印开始X坐标
     * @param y     水印开始Y坐标
     * @param alpha     透明度[0-100]
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */
    private static synchronized  void watermarkImg(String srcPath,String distPath,String watermarkImg, int width, int height, int x, int y, int alpha,boolean isGraphicsMagick) throws IOException, InterruptedException, IM4JavaException{
        CompositeCmd cmd = null;
        if(isGraphicsMagick) {
            cmd = new CompositeCmd(true);
        }else{
            cmd = new CompositeCmd(true);
        }
        IMOperation op = new IMOperation();
        op.dissolve(alpha);
        op.geometry(width, height, x, y);
        op.addImage(watermarkImg);
        op.addImage(srcPath);
        op.addImage(distPath);
        cmd.run(op);
    }

    private static  int[] getXY(int[] image, int[] watermark, int position, int x, int y) {
        int[] xy = new int[2];
        if(position==1){
            xy[0] = x;
            xy[1] = y;
        }else if(position==2){
            xy[0] = (image[0]-watermark[0])/2;          //横向边距
            xy[1] = y;  //纵向边距
        }else if(position==3){
            xy[0] = image[0]-watermark[0]-x;
            xy[1] = y;
        }else if(position==4){
            xy[0] = x;
            xy[1] = (image[1]-watermark[1])/2;
        }else if(position==5){
            xy[0] = (image[0]-watermark[0])/2;
            xy[1] =  (image[1]-watermark[1])/2;
        }else if(position==6){
            xy[0] = image[0]-watermark[0]-x;
            xy[1] = (image[1] - watermark[1])/2;
        }else if(position==7){
            xy[0] = x;
            xy[1] = image[1] - watermark[1] - y;
        }else if(position==8){
            xy[0] =  (image[0]-watermark[0])/2;
            xy[1] = image[1] - watermark[1] - y;
        }else{
            xy[0] = image[0]-watermark[0]-x;
            xy[1] = image[1] - watermark[1] - y;
        }
        return xy;
    }

    /**
     * 把文字转化为一张背景透明的png图片
     * @param str 文字的内容
     * @param fontType 字体，例如宋体
     * @param fontSize 字体大小
     * @param colorStr 字体颜色，不带#号，例如"990033"
     * @param outfile  png图片的路径
     * @throws Exception
     */
    public static void converFontToImage(String str,String fontType,int fontSize,String colorStr, String outfile) throws Exception{
        Font font=new Font(fontType,Font.BOLD,fontSize);
        File file=new File(outfile);
        //获取font的样式应用在str上的整个矩形
        Rectangle2D r=font.getStringBounds(str, new FontRenderContext(AffineTransform.getScaleInstance(1, 1),false,false));
        int unitHeight=(int)Math.floor(r.getHeight());//获取单个字符的高度
        //获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width=(int)Math.round(r.getWidth())+1;
        int height=unitHeight+3;//把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        //创建图片

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(Integer.parseInt(colorStr, 16)));//在换成所需要的字体颜色
        g2d.setFont(font);
        g2d.drawString(str, 0,font.getSize());

        ImageIO.write(image, "png", file);//输出png图片
    }


 }

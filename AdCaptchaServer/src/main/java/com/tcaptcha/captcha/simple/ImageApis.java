package com.tcaptcha.captcha.simple;

//Advertisement Captcha Image Generation APIs
//Suhas
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
*/

public class ImageApis extends JPanel {

    private BufferedImage image;

    public ImageApis() throws IOException {
        try {
            image = ImageIO.read(new File("D:/1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(
            image.getWidth(), image.getHeight()));
    //    String s = "\""+"Casuals By Zovi"+"\"";
        String s = "Zovi";
        image =  UnderlinedText(image,s);
//        LongAnimatedTypeinText1();
        try {
            
      	  String fileName = "example25.png";
            String drive = "D:/";
            ImageIO.write(image, "gif", new File( drive + fileName));
        } catch (IOException e) {
            return;
        }
    
    }
/* File is being saved in .png format as JPEG compression is not lossless and hence, might deteriorate quality of image before saving it to the disk 
    
   
    /*  Create Quoted text on the image provided by the advertiser
     *  Quoted text is the answer to Captcha
     *  Type in the Quoted text message gets displayed at the bottom of image
     */
    
    
    
    public static BufferedImage QuotedText(BufferedImage old, String message) {
        
    	
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        System.out.println("Dimensions: " + w+ ","+ h);
        
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        
        String s = "\""+message+"\"";
  //      String s= message;
        AttributedString as = new AttributedString(s);
       
        as.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 18), 0, s.length());
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 30;
        int y = fm.getHeight()+ 20;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        String s1 = "Please Enter the "+"\""+"Quoted"+"\""+" Text";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 17 , 25);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 17, 25);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 25, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 25, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }

    
    
    /*  Create Underlined text on the image provided by the advertiser
     *  Underlined text is the answer to Captcha
     *  Type in the Underlined text message gets displayed at the bottom of image
     */
    
    
    
    public static BufferedImage UnderlinedText(BufferedImage old, String message) {
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
     //   String s = "Zovi";
        String s= message;
        AttributedString as = new AttributedString(s);
        as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0,
                s.length());
        as.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 20), 0, s.length());
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 30;
        int y = fm.getHeight()+ 20;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        String s1 = "Please Enter the Underlined Text";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 17 , 27);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 17, 27);
        as1.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 17 , 27);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 27, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 27, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }

    
    
    /*  Create Colored text on the image provided by the advertiser
     *  Colored text is the answer to Captcha
     *  Type in the Colored text message gets displayed at the bottom of image
     */
    
    
    
    
    public static BufferedImage ColoredText(BufferedImage old, String message) {
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.blue);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
 //       String s = "Zovi";
        String s = message;
        AttributedString as = new AttributedString(s);
        as.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 20), 0, s.length());
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 30;
        int y = fm.getHeight()+ 20;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        String s1 = "Please Enter the "+ "Blue" + " Text";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 17 , 21);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLUE, 17, 21);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 21, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 21, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }
    
    
    /*  Create Italic text on the image provided by the advertiser
     *  Italic text is the answer to Captcha
     *  Type in the Italic text message gets displayed at the bottom of image
     */
    
    
    
    
    public static BufferedImage ItalicText(BufferedImage old, String message) {
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.blue);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
     //   String s = "Zovi";
        String s = message;
        AttributedString as = new AttributedString(s);
        as.addAttribute(TextAttribute.FONT,new Font("Serif",Font.ITALIC | Font.BOLD, 20), 0, s.length());
        
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 30;
        int y = fm.getHeight()+ 20;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        String s1 = "Please Enter the "+"Italicised" + " Text";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.ITALIC | Font.BOLD, 15), 17 , 27);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLUE, 17, 27);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 27, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 27, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }
    
    
    /*  Create Quoted text on the image provided by the advertiser in the left
     *  Quoted text is the answer to Captcha
     *  Type in the Quoted text message gets displayed at the bottom of image
     */
    
    
    
    
    public static BufferedImage QuotedLongTextLeft(BufferedImage old, String message) {
        int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        
        String s = "\""+message+"\"";
     //   String s = message;
        AttributedString as = new AttributedString(s);
        
        as.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 20), 0, s.length());
        FontMetrics fm = g2d.getFontMetrics();
        int x = 10;
        int y = fm.getHeight()+ 40;
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d.setPaint(tp);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        String s1 = "Please Enter the "+"\""+"Quoted"+"\""+" Text in the Left";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 17 , 25);
        as1.addAttribute(TextAttribute.FOREGROUND, tp , 17, 25);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 25, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 25, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }

    
   
    /*  Create TYPE-IN text on the image provided by the advertiser analogous to Solve Media's Design
     *  TYPE-IN text is the answer to Captcha
     *  This captcha design is suitable for longer brand messages - (3 - 4 words) which are answer to Captcha
     */
    
   
    
    public static BufferedImage LongTypeinText(BufferedImage old, String Message) {
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
 
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d1.setPaint(tp);
      //  String message = "Smart Casuals by Zovi";
        String message = Message;
        String s1 = "Please Enter: " + message;
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 14);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 14);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 14 , 14+message.length());
        as1.addAttribute(TextAttribute.FOREGROUND,tp, 14, 14+message.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm1.getHeight())/2 + 4;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }
   
    
    /* API for Custom question to be added to Pre-Finalised Image to the Advertiser 
     * 
     */
    
    
    public static BufferedImage CustomQuestion(BufferedImage old, String question) {
    	old=cropImage(old);
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
 
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d1.setPaint(tp);
       
   //     String s1 = "Which is the Brand shown in the image?";
        String s1=question;
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, tp, 0, s1.length());
 //       as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 14 , 14+message.length());
 //       as1.addAttribute(TextAttribute.FOREGROUND, Color.BLUE, 14, 14+message.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm1.getHeight())/2 + 4;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0, h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    }
   
   
    
    
    
    
    public static BufferedImage QuotedLongTextOnImage(BufferedImage old, String message) {
        int w = old.getWidth();
        int h = old.getHeight();
        System.out.println("Dimensions: " + w+ ","+ h);
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        
        String s = "\""+message+"\"";
     //   String s=message;
        AttributedString as = new AttributedString(s);
        
        as.addAttribute(TextAttribute.FONT,new Font("Arial Narrow", Font.BOLD, 20), 0, s.length());
        FontMetrics fm = g2d.getFontMetrics();
        int x = 20;
        int y = h - 35;
        /*
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d.setPaint(tp);
        */
        g2d.setColor(Color.RED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(as.getIterator(), x, y);
  //      g2d.dispose();
        BufferedImage img1 = new BufferedImage(
                w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.GRAY);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        String s1 = "Please Enter the "+"\""+"Quoted"+"\""+" Text in the Left";
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 17);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, 17);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 17 , 25);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.RED , 17, 25);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 25, s1.length());
        as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 25, s1.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm.getHeight())/2;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d.drawImage(img1, 0 , h - 20, null);
        g2d1.dispose();
        g2d.dispose();
        return img;
    } 
    
   
    // Create Animated Advertisement Captcha
   
 /*   
    private void LongAnimatedTypeinText1( ) throws IOException {
    	BufferedImage image = ImageIO.read(new File("D:/1.gif"));
    	int w = image.getWidth();
        int h = image.getHeight();
    	BufferedImage img1 = new BufferedImage(w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.BLACK);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d1.setPaint(tp);
        String message = "Smart Casuals by Zovi";
        String s1 = "Please Enter: " + message;
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 14);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 14);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 14 , 14+message.length());
        as1.addAttribute(TextAttribute.FOREGROUND,Color.RED, 14, 14+message.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm1.getHeight())/2 + 4;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d1.dispose();
        String fileName = "a.gif";
        String drive = "D:/";
        ImageIO.write(img1, "gif", new File( drive + fileName));
    	
    	String original_filename = "D:\\1.gif";
    	String watermark_filename = "D:\\a.gif";
    	String result_filename = "D:\\b.gif";
    	try {
    	ImageInfo ii_orig = new ImageInfo();
    	ii_orig.setFileName(original_filename);
    	MagickImage mi_orig = new MagickImage(ii_orig);

    	ImageInfo ii_wm = new ImageInfo();
    	ii_wm.setFileName(watermark_filename);
    	MagickImage mi_wm = new MagickImage(ii_wm);

    	
    	

    	// Dissolving the watermark at position 10,10.
    	// But I don't have a way to tell IM the amount it
    	//should be dissolved.

    	
    	mi_orig.compositeImage(CompositeOperator.HardLightCompositeOp, mi_wm, 0,h-20);
    //	mi_orig.setMagick("jpg");
    	
//    	final ImageInfo newInfo = new ImageInfo();
    	mi_orig.setFileName(result_filename);
    	mi_orig.writeImage(ii_orig);
    	mi_orig.destroyImages();
    	mi_wm.destroyImages();
    	} catch (MagickException me) {}
       
    }
        
        
// Create Animated Advertisement Captcha with static image
   
    
    private void LongAnimatedTypeinText2( ) throws IOException {
    	BufferedImage image = ImageIO.read(new File("D:/1.gif"));
    	int w = image.getWidth();
        int h = image.getHeight();
    	BufferedImage img1 = new BufferedImage(w, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d1 = img1.createGraphics();
    //    g2d1.drawImage(old, 0, 0, null);
        g2d1.setColor(Color.BLACK);
        g2d1.fillRect(0, 0, w, h);
        g2d1.setPaint(Color.BLUE);
        g2d1.setFont(new Font("Serif", Font.BOLD, 15));
        BufferedImage bi = getTextureImage();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        TexturePaint tp = new TexturePaint(bi, r);
        g2d1.setPaint(tp);
        String message = "Smart Casuals by Zovi";
        String s1 = "Please Enter: " + message;
        AttributedString as1 = new AttributedString(s1);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 0, 14);
        as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 14);
        as1.addAttribute(TextAttribute.FONT,new Font("Serif", Font.BOLD, 15), 14 , 14+message.length());
        as1.addAttribute(TextAttribute.FOREGROUND,Color.RED, 14, 14+message.length());
        FontMetrics fm1 = g2d1.getFontMetrics();
        int x1 = 20;
        int y1 = (fm1.getHeight())/2 + 4;
        g2d1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d1.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d1.drawString(as1.getIterator(), x1, y1);
        g2d1.dispose();
        String fileName = "a.gif";
        String drive = "D:/";
        ImageIO.write(img1, "gif", new File( drive + fileName));
    	
    	String original_filename = "D:\\1.gif";
    	String watermark_filename = "D:\\a.gif";
   /* 	
    	String result_filename = "D:\\b.gif";
    	try {
    	ImageInfo ii_orig = new ImageInfo();
    	ii_orig.setFileName(original_filename);
    	MagickImage mi_orig = new MagickImage(ii_orig);

    	ImageInfo ii_wm = new ImageInfo();
    	ii_wm.setFileName(watermark_filename);
    	MagickImage mi_wm = new MagickImage(ii_wm);

    	
    	

    	// Dissolving the watermark at position 10,10.
    	// But I don't have a way to tell IM the amount it
    	//should be dissolved.

    	
    	mi_orig.compositeImage(CompositeOperator.HardLightCompositeOp, mi_wm, 0,h-20);
    //	mi_orig.setMagick("jpg");
    	
//    	final ImageInfo newInfo = new ImageInfo();
    	mi_orig.setFileName(result_filename);
    	mi_orig.writeImage(ii_orig);
    	mi_orig.destroyImages();
    	mi_wm.destroyImages();
    	} catch (MagickException me) {}
     */  
    
    
    
    
    
    
    
    
  //  }
        
        
    
   /* Load color/texture from Color/Texture Image Sample
     * Can be used to customize color of the Brand message to be displayed on the image by the Advertiser
     * Color/Texture Sample is to be uploaded by the advertiser 
     */
    
    
    public static BufferedImage getTextureImage() {
        // Create the test image.
        int size = 8;
        BufferedImage bi = new BufferedImage(size, size,
            BufferedImage.TYPE_INT_RGB);
        Random randGen = new Random();
        int tex_id;
        tex_id = randGen.nextInt(5) + 1;
        String fileName = "tex6"+".jpg";
        String drive = "D:/";
        
        try {
            bi = ImageIO.read(new File(drive+fileName));
        }
        catch (IOException e) {
        }
        
        return bi;
      }

    
    public static BufferedImage cropImage(BufferedImage src) {
    	
    	int k = src.getWidth();
    	int l = src.getHeight();
    	BufferedImage dest = src.getSubimage(170, 0, k-170 ,l);
        return dest; 
     }
    
   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    private static void create() throws IOException {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ImageApis());
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new ImageApis();
    }
}

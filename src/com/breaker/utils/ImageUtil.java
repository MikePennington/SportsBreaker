package com.breaker.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageUtil {

    private static Logger log = Logger.getLogger(ImageUtil.class);

    public static void toJPG(InputStream in, File outputFile) throws Exception {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(in);
            if (outputFile.exists())
                outputFile.delete();
            outputFile.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, "jpg", outputFile);
        } catch (Exception e) {
            log.error("Error converting image to JPG", e);
            throw e;
        } finally {
            bufferedImage = null;
        }
    }

    public static void toJPG(InputStream in, File outputFile, int maxWidth, int maxHeight) throws Exception {
        BufferedImage originalImage = null;
        BufferedImage resizedImage = null;
        try {
            originalImage = ImageIO.read(in);

            if (outputFile.exists())
                outputFile.delete();
            outputFile.getParentFile().mkdirs();
            resizedImage = calculateProportionsAndResize(originalImage, maxWidth, maxHeight);
            ImageIO.write(resizedImage, "jpg", outputFile);

            // Save new or delete old saved original
            String originalFileName = outputFile.getAbsolutePath().replace(".jpg", "") + "_original.jpg";
            File originalImageFile = new File(originalFileName);
            if (resizedImage != originalImage) {
                ImageIO.write(originalImage, "jpg", originalImageFile);
            } else if (originalImageFile.exists()) {
                try {
                    originalImageFile.delete();
                } catch (Exception e) {
                    log.error("Error deleting old original image file", e);
                }
            }
        } catch (Exception e) {
            log.error("Error converting image to JPG", e);
            throw e;
        } finally {
            originalImage = null;
            resizedImage = null;
        }
    }

    public static BufferedImage calculateProportionsAndResize(BufferedImage bufferedImage, int maxWidth, int maxHeight) {
        int currentWidth = bufferedImage.getWidth();
        int currentHeight = bufferedImage.getHeight();

        boolean resizeByWidth = false;
        boolean resizeByHeight = false;
        int newWidth = 0;
        int newHeight = 0;
        if (currentWidth > maxWidth && currentHeight > maxHeight) {
            double widthRatio = (double) currentWidth / maxWidth;
            double heightRatio = (double) currentHeight / maxHeight;
            if (widthRatio > heightRatio)
                resizeByWidth = true;
            else
                resizeByHeight = true;
        } else if (currentWidth > maxWidth) {
            resizeByWidth = true;
        } else if (currentHeight > maxHeight) {
            resizeByHeight = true;
        }

        if (resizeByWidth) {
            newWidth = maxWidth;
            newHeight = (currentHeight * newWidth) / currentWidth;
        } else if (resizeByHeight) {
            newHeight = maxHeight;
            newWidth = (currentWidth * newHeight) / currentHeight;
        }

        if (resizeByWidth || resizeByHeight) {
            bufferedImage = resize(bufferedImage, newWidth, newHeight);
        }

        return bufferedImage;
    }

    public static BufferedImage resize(BufferedImage bufferedImage, int newWidth, int newHeight) {
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImage.createGraphics();
        double xScale = (double) newWidth / bufferedImage.getWidth();
        double yScale = (double) newHeight / bufferedImage.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
        g2d.drawRenderedImage(bufferedImage, at);
        g2d.dispose();
        return newImage;
    }

}

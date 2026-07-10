/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.GifDecoder
 *  com.cosmeticsmod.morecosmetics.utils.GifDecoder$ImageFrame
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.utils.GifDecoder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GifDecoder {
    public static ImageFrame[] readGIF(InputStream stream) throws IOException {
        IIOMetadataNode screenDescriptor;
        IIOMetadataNode globalRoot;
        NodeList globalScreenDescriptor;
        ArrayList<ImageFrame> frames = new ArrayList<ImageFrame>(2);
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        reader.setInput(ImageIO.createImageInputStream(stream));
        int width = -1;
        int height = -1;
        IIOMetadata metadata = reader.getStreamMetadata();
        if (metadata != null && (globalScreenDescriptor = (globalRoot = (IIOMetadataNode)metadata.getAsTree(metadata.getNativeMetadataFormatName())).getElementsByTagName("LogicalScreenDescriptor")) != null && globalScreenDescriptor.getLength() > 0 && (screenDescriptor = (IIOMetadataNode)globalScreenDescriptor.item(0)) != null) {
            width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
            height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
        }
        BufferedImage master = null;
        Graphics masterGraphics = null;
        int frameIndex = 0;
        while (true) {
            BufferedImage image;
            try {
                image = reader.read(frameIndex);
            }
            catch (IndexOutOfBoundsException io) {
                break;
            }
            if (width == -1 || height == -1) {
                width = image.getWidth();
                height = image.getHeight();
            }
            IIOMetadataNode root = (IIOMetadataNode)reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
            IIOMetadataNode gce = (IIOMetadataNode)root.getElementsByTagName("GraphicControlExtension").item(0);
            int delay = Integer.valueOf(gce.getAttribute("delayTime"));
            String disposal = gce.getAttribute("disposalMethod");
            int x = 0;
            int y = 0;
            if (master == null) {
                master = new BufferedImage(width, height, 2);
                masterGraphics = master.createGraphics();
                ((Graphics2D)masterGraphics).setBackground(new Color(0, 0, 0, 0));
            } else {
                NodeList children = root.getChildNodes();
                for (int nodeIndex = 0; nodeIndex < children.getLength(); ++nodeIndex) {
                    Node nodeItem = children.item(nodeIndex);
                    if (!nodeItem.getNodeName().equals("ImageDescriptor")) continue;
                    NamedNodeMap map = nodeItem.getAttributes();
                    x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                    y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                }
            }
            masterGraphics.drawImage(image, x, y, null);
            BufferedImage copy = new BufferedImage(master.getColorModel(), master.copyData(null), master.isAlphaPremultiplied(), null);
            frames.add(new ImageFrame(copy, delay, disposal));
            if (disposal.equals("restoreToPrevious")) {
                BufferedImage from = null;
                for (int i = frameIndex - 1; i >= 0; --i) {
                    if (((ImageFrame)frames.get(i)).getDisposal().equals("restoreToPrevious") && frameIndex != 0) continue;
                    from = ((ImageFrame)frames.get(i)).getImage();
                    break;
                }
                master = new BufferedImage(from.getColorModel(), from.copyData(null), from.isAlphaPremultiplied(), null);
                masterGraphics = master.createGraphics();
                ((Graphics2D)masterGraphics).setBackground(new Color(0, 0, 0, 0));
            } else if (disposal.equals("restoreToBackgroundColor")) {
                masterGraphics.clearRect(x, y, image.getWidth(), image.getHeight());
            }
            ++frameIndex;
        }
        if (masterGraphics == null) {
            reader.dispose();
            stream.close();
            throw new IOException("Failed to parse gif: Graphics is null!");
        }
        masterGraphics.dispose();
        reader.dispose();
        stream.close();
        return frames.toArray(new ImageFrame[frames.size()]);
    }

    public static class ImageFrame {
        private java.awt.image.BufferedImage image;
        private int delay;
        private String disposal;
        public ImageFrame(java.awt.image.BufferedImage image, int delay, String disposal) {
            this.image = image; this.delay = delay; this.disposal = disposal;
        }
        public java.awt.image.BufferedImage getImage() { return image; }
        public int getDelay() { return delay; }
        public String getDisposal() { return disposal; }
    }
}


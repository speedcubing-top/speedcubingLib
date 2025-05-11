package top.speedcubing.lib.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

//rgb >> 24 != 0x00
public class ImageRotator {

    enum RotateType {
        ROTATE_90,
        ROTATE_180,
        ROTATE_270,
        FLIP_HORIZONTAL,
        FLIP_VERTICAL
    }

    public static void rotate(File input, File output, RotateType type) {
        try {
            ImageIO.write(rotate(ImageIO.read(input), type), FilenameUtils.getExtension(input.getName()), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rotate(File input, RotateType type) {
        rotate(input, input, type);
    }

    public static BufferedImage rotate(BufferedImage image, RotateType type) {
        int w = image.getWidth();
        int h = image.getHeight();
        int[][] c = new int[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                c[i][j] = image.getRGB(i, j);
        switch (type) {
            case ROTATE_90: {
                image = new BufferedImage(h, w, image.getType());
                for (int i = 0; i < w; i++)
                    for (int j = 0; j < h; j++)
                        image.setRGB(j, i, c[w - i - 1][j]);
            }
            break;
            case ROTATE_270: {
                image = new BufferedImage(h, w, image.getType());
                for (int i = 0; i < w; i++)
                    for (int j = 0; j < h; j++)
                        image.setRGB(j, i, c[i][h - j - 1]);
            }
            break;
            case ROTATE_180: {
                for (int i = 0; i < w; i++)
                    for (int j = 0; j < h; j++)
                        image.setRGB(i, j, c[w - i - 1][h - j - 1]);
            }
            break;
            case FLIP_HORIZONTAL: {
                for (int i = 0; i < w; i++)
                    for (int j = 0; j < h; j++)
                        image.setRGB(i, j, c[w - i - 1][j]);
            }
            break;
            case FLIP_VERTICAL: {
                for (int i = 0; i < w; i++)
                    for (int j = 0; j < h; j++)
                        image.setRGB(i, j, c[i][h - j - 1]);
            }
            break;
        }
        return image;
    }
}

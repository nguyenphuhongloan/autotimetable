package classes;
import java.awt.Image;
import javax.swing.ImageIcon;
public class ImageIconConverter {
    public static ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        Image img = srcImg.getImage();
        Image newImg = img.getScaledInstance(w, h, Image.SCALE_REPLICATE);
        ImageIcon icon = new ImageIcon(newImg);
        return icon;
    }
}

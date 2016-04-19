package backup;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class FontTest {

    public static void main(String[] args) throws Exception {

        final float fontsize = 25.0f;
        final String text = "Shimazaki Haruka, 012345, こんにちは, 안녕하세요";
        final int imageMargin = 10;
        final Font defaultFont = Font.decode("Monospaced").deriveFont(fontsize);

        // 利用可能なすべてのフォントを取得
        GraphicsEnvironment genv = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        Font[] font = genv.getAllFonts();
        int nameMaxWidth = getFontNameMaxWidth(defaultFont, font);
        int textMaxWidth = getMaxWidth(font, fontsize, text);
        int imageWidth = imageMargin + nameMaxWidth + imageMargin
                + textMaxWidth + imageMargin;
        BufferedImage image = new BufferedImage(imageWidth, (int) (font.length
                * fontsize * 2), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.GREEN);
        for (int i = 0; i < font.length; i++) {
            // フォント名描画
            g.setFont(defaultFont);
            g.drawString(getDispFontName(font[i]), imageMargin, i * 2
                    * fontsize + fontsize);
            // フォント描画
            g.setFont(font[i].deriveFont(fontsize));
            g.drawString(text, imageMargin + nameMaxWidth + imageMargin, i * 2
                    * fontsize + fontsize);
        }
        ImageIO.write(image, "png", new File("img/fonttest.png"));
    }

    private static int getMaxWidth(Font[] font, float fontsize, String str) {
        BufferedImage image = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int maxWidth = getMaxWidth(g, font, fontsize, str);
        g.dispose();
        return maxWidth;
    }

    private static int getMaxWidth(Graphics2D g, Font[] font, float fontsize,
            String str) {
        int maxWidth = 0;
        for (int i = 0; i < font.length; i++) {
            Font f = font[i].deriveFont(fontsize);
            FontMetrics fm = g.getFontMetrics(f);
            int w = fm.stringWidth(str);
            maxWidth = Math.max(maxWidth, w);
        }
        return maxWidth;
    }

    private static int getFontNameMaxWidth(Font font, Font[] fonts) {
        BufferedImage image = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int maxWidth = getFontNameMaxWidth(g, font, fonts);
        g.dispose();
        return maxWidth;
    }

    private static int getFontNameMaxWidth(Graphics2D g, Font font, Font[] fonts) {
        int maxWidth = 0;
        FontMetrics fm = g.getFontMetrics(font);
        for (int i = 0; i < fonts.length; i++) {
            int w = fm.stringWidth(getDispFontName(fonts[i]));
            maxWidth = Math.max(maxWidth, w);
        }
        return maxWidth;
    }

    private static String getDispFontName(Font f) {
        if (f.getName().equals(f.getFontName())) {
            return f.getName();
        } else {
            // 論理名(フォントフェース名)
            return f.getName() + " (" + f.getFontName() + ")";
        }
    }
}
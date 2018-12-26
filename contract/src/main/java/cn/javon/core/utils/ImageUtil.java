package cn.javon.core.utils;

import com.itextpdf.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    /**
     * base64字符串转化成图片
     *
     * @param imgStr
     * @param path
     * @return
     */
    public static boolean base64GeneratePng(String imgStr, String path) { //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path));
            byte[] decoderBytes = decoder.decodeBuffer(imgStr.split(",")[1]);
            write.write(decoderBytes);
            write.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * pdf文件转png格式
     *
     * @param pdfPath
     * @return
     */
    public static String[] pdf2Png(String pdfPath) {
        File file = new File(pdfPath);
        PDDocument pdDocument;
        String[] pngs = null;
        try {
            String imgFolderPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
            PdfReader reader = new PdfReader(pdfPath);
            int pages = reader.getNumberOfPages();
            StringBuffer imgFilePath = null;
            pngs = new String[pages];
            for (int i = 0; i < pages; i++) {
                String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                imgFilePath = new StringBuffer();
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append("_");
                imgFilePath.append(String.valueOf(i + 1));
                imgFilePath.append(".png");
                File dstFile = new File(imgFilePath.toString());
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                ImageIO.write(image, "png", dstFile);
                pngs[i] = imgFilePath.toString();
            }
            pdDocument.close();
            reader.close();

            logger.info("PDF文档转PNG图片成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pngs;
    }

}

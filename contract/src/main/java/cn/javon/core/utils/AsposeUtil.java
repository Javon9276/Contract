package cn.javon.core.utils;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * 使用Aspose Java版把Word或Excel转换PDF
 * 官方地址：https://www.aspose.com/
 * 此类包含了Word和Excel转PDF的方法
 * <p>
 * 版本说明：
 * Excel使用：aspose-cells-8.5.2.jar
 * Word使用：aspose-words-15.8.0-jdk16.jar
 * <p>
 * 关于license.xml版权和破解问题，该license.xml仅用于学习，不可用于商业
 **/
public class AsposeUtil {

    private final static String LICENSE_PATH = "config\\license.xml";
    private static Logger logger = LoggerFactory.getLogger(AsposeUtil.class);

    /**
     * 验证Word License 是否正确
     *
     * @return
     */
    private static boolean getWordLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream(LICENSE_PATH); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            logger.error("无法获取License，请联系管理员检查", e);
        }
        return result;
    }

    /**
     * word文档转换成pdf
     * <p>
     * 该方法可能会存在样式问题，进行转换的时候最后做细微调整
     *
     * @param wordPath word文档路径
     * @param pdfPath  生成pdf路径
     */
    public static void doc2Pdf(String wordPath, String pdfPath) {
        if (!getWordLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(wordPath);                    //Address是将要被转化的word文档
            doc.save(pdfPath, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            logger.info("开始进行Word:" + wordPath + "转换PDF:" + pdfPath + " , 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            logger.error("Word转换Pdf存在异常:", e);
        }
    }

    /**
     * 验证Excel License 是否正确
     *
     * @return
     */
    private static boolean getExcelLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream(LICENSE_PATH); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            logger.error("无法获取License，请联系管理员检查", e);
        }
        return result;
    }

    /**
     * Excel文档转换成pdf
     * <p>
     * 该方法可能会存在样式问题，进行转换的时候最后做细微调整
     *
     * @param excelPath Excel文档路径
     * @param pdfPath   生成pdf路径
     */
    public static void excel2Pdf(String excelPath, String pdfPath) {
        if (!getExcelLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(excelPath);
            wb.save(pdfPath, com.aspose.cells.SaveFormat.PDF);
            long now = System.currentTimeMillis();
            logger.info("开始进行Excel:" + excelPath + "转换PDF:" + pdfPath + " , 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            logger.error("Excel转换Pdf存在异常:", e);
        }
    }

}

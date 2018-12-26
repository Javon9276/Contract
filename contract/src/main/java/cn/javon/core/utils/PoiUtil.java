package cn.javon.core.utils;

import cn.javon.core.common.Constant;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;

public class PoiUtil {

    private static final Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    /**
     * 替换Word模板文件内容
     *
     * @param obj            对象
     * @param sourceFilePath Excel模板文件路径
     * @param targetFilePath Excel生成文件路径
     */
    public static boolean replaceWordModel(Object obj, String sourceFilePath, String targetFilePath) {
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean bool = true;
        try {
            InputStream inputStream = new FileInputStream(sourceFilePath);
            HWPFDocument document = new HWPFDocument(inputStream);
            Range range = document.getRange();
            for (Field field : fields) {
                // 对于每个属性，获取属性名
                // 获取原来的访问控制权限
                String varName = field.getName();
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                String object = "";
                if (field.get(obj) != null) {
                    object = String.valueOf(field.get(obj));
                }
                range.replaceText("#" + varName + "#", object);
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
            }
            OutputStream outputStream = new FileOutputStream(targetFilePath);
            document.write(outputStream);
            document.close();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            bool = false;
            logger.error(Constant.ERROR_MESSAGE, e);
        }
        return bool;
    }


    /**
     * 替换Excel模板文件内容
     *
     * @param obj            对象
     * @param sourceFilePath Excel模板文件路径
     * @param targetFilePath Excel生成文件路径
     */
    public static boolean replaceExcelModel(Object obj, String sourceFilePath, String targetFilePath) {
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean bool = true;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(sourceFilePath));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                if (row != null) {
                    int num = row.getLastCellNum();
                    for (int i = 0; i < num; i++) {
                        HSSFCell cell = row.getCell(i);
                        if (cell != null) {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        }
                        if (cell == null || cell.getStringCellValue() == null) {
                            continue;
                        }
                        String value = cell.getStringCellValue();
                        if (!"".equals(value)) {
                            for (Field field : fields) {
                                // 对于每个属性，获取属性名
                                String varName = field.getName();
                                try {
                                    // 获取原来的访问控制权限
                                    boolean accessFlag = field.isAccessible();
                                    // 修改访问控制权限
                                    field.setAccessible(true);
                                    // 获取在对象f中属性fields[i]对应的对象中的变量
                                    try {
                                        if (value.toLowerCase().indexOf("#" + varName.toLowerCase() + "#") >= 0) {
                                            String object = "";
                                            if (field.get(obj) != null) {
                                                object = String.valueOf(field.get(obj));
                                            }
                                            value = value.replaceAll("(?i)#" + varName + "#", object);
                                            cell.setCellValue(value);
                                        }
                                    } catch (IllegalAccessException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    // 恢复访问控制权限
                                    field.setAccessible(accessFlag);
                                } catch (IllegalArgumentException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }

            // 输出文件
            File file = new File(targetFilePath).getParentFile();
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOut = new FileOutputStream(targetFilePath);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
            fs.close();
        } catch (Exception e) {
            bool = false;
            logger.error(Constant.ERROR_MESSAGE, e);
        }
        return bool;
    }


    /**
     * 把签名弄到Pdf上
     *
     * @param templatePath
     * @param targetPath
     * @param imagePath
     * @param params
     */
    public static boolean signaturePdf(String templatePath, String targetPath, String imagePath, int[] params) {
        boolean result = false;
        try {
            PdfReader reader = new PdfReader(templatePath);
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(targetPath));
            PdfContentByte under = stamp.getOverContent(params[0]);
            Image img = Image.getInstance(imagePath);
            img.scaleToFit(params[1], params[2]);
            img.setAbsolutePosition(params[3], params[4]);
            under.addImage(img);
            stamp.close();
            reader.close();
            result = true;
        } catch (Exception e) {
            logger.error("PDF签名异常", e);
        }
        return result;
    }


    public static void main(String[] args) throws Exception {

        String contractPdfPath = "D:\\code\\self\\contract\\target\\contract\\pdf\\20181225161140869.pdf";
        String signatureFilePath = "D:\\code\\self\\contract\\target\\contract\\pdf\\20181225161140869签名.png";
        String signaturePdfPath = "D:\\code\\self\\contract\\target\\contract\\pdf\\20181225161140869（已签名）.pdf";

        PdfReader reader = new PdfReader(contractPdfPath);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(signaturePdfPath));
        PdfContentByte under = stamp.getOverContent(5);

        Image img = Image.getInstance(signatureFilePath);
        img.scaleToFit(120, 120);
        img.setAbsolutePosition(210, 550);
        under.addImage(img);
        stamp.close();
        reader.close();
    }

}

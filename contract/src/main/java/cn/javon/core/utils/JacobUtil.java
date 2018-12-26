package cn.javon.core.utils;

import cn.javon.core.common.Constant;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;


/**
 * 使用Apache开源的Jacob把Word、Excel或PPT转换PDF
 * <p>
 * Jacob的使用比较苛刻，只能在Windows系统中使用
 * 并且系统需要安装Office或者Wps应用程序才能进行转换
 * <p>
 * 前提条件：
 * 1. 把配置文件中的dll文件复制到系统的{JAVA_HOME}/bin里面
 * 2. dll的版本和Jar的版本要一致
 * <p>
 * 系统的ProgramId
 * Office:
 * Word : Word.Application
 * Excel : Excel.Application
 * PPT : PowerPoint.Application
 * <p>
 * WPS:
 * Word : KWPS.Application
 * Excel : KET.Application
 * PPT : KWPP.Application
 * <p>
 * 问题：
 * 转换内容消耗比较厉害，要注意内存问题
 */
public class JacobUtil {

    private static Logger logger = LoggerFactory.getLogger(JacobUtil.class);

    /**
     * Method Description word文档转换成PDF
     *
     * @param wordPath Word文档路径
     * @param pdfPath  PDF文档路径
     * @return 是否转换成功
     */
    public static boolean doc2Pdf(String wordPath, String pdfPath) {
        try {
            long old = System.currentTimeMillis();
            ActiveXComponent activeXComponent = new ActiveXComponent("KWPS.Application");
            activeXComponent.setProperty("Visible", false);
            activeXComponent.setProperty("AutomationSecurity", 3);
            Dispatch docs = activeXComponent.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", wordPath, false, true).toDispatch();
            Dispatch.call(doc, "ExportAsFixedFormat", pdfPath, 17);// word保存为pdf格式宏，值为17
            Dispatch.call(doc, "Close", false);
            activeXComponent.invoke("Quit");
            long now = System.currentTimeMillis();
            logger.info("PPT:" + wordPath + "转换PDF:" + pdfPath + " , 共耗时：" + ((now - old) / 1000.0) + "秒");
            return true;
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
        }
        return false;
    }

    /**
     * Method Description Excel文档转换成PDF
     *
     * @param excelPath Excel文档路径
     * @param pdfPath   PDF文档路径
     * @return 是否转换成功
     */
    public static boolean excel2Pdf(String excelPath, String pdfPath) {
        try {
            long old = System.currentTimeMillis();
            ActiveXComponent activeXComponent = new ActiveXComponent("KET.Application");
            activeXComponent.setProperty("Visible", false);
            Dispatch excels = activeXComponent.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Open", excelPath, false, true).toDispatch();
            Dispatch currentSheet = Dispatch.get(excel, "ActiveSheet").toDispatch();
            Dispatch pageSetup = Dispatch.get(currentSheet, "PageSetup").toDispatch();
            Dispatch.put(pageSetup, "Orientation", 1);
            Dispatch.put(pageSetup, "Zoom", false); // 值为100或false
            Dispatch.put(pageSetup, "FitToPagesTall", false);
            Dispatch.put(pageSetup, "FitToPagesWide", 1);
            Dispatch.call(excel, "ExportAsFixedFormat", 0, pdfPath);
            Dispatch.call(excel, "Close", false);
            activeXComponent.invoke("Quit");
            long now = System.currentTimeMillis();
            logger.info("PPT:" + excelPath + "转换PDF:" + pdfPath + " , 共耗时：" + ((now - old) / 1000.0) + "秒");
            return true;
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
        }
        return false;
    }

    /**
     * Method Description PPT文档转换成PDF
     *
     * @param pptPath PPT文档路径
     * @param pdfPath PDF文档路径
     * @return 是否转换成功
     */
    public static boolean ppt2Pdf(String pptPath, String pdfPath) {
        try {
            long old = System.currentTimeMillis();
            ActiveXComponent activeXComponent = new ActiveXComponent("KWPP.Application");
            Dispatch ppts = activeXComponent.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", pptPath, false, true, true).toDispatch();
            Dispatch.call(ppt, "SaveAs", pdfPath, 32);
            Dispatch.call(ppt, "Close");
            activeXComponent.invoke("Quit");
            long now = System.currentTimeMillis();
            logger.info("PPT:" + pptPath + "转换PDF:" + pdfPath + " , 共耗时：" + ((now - old) / 1000.0) + "秒");
            return true;
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
        }
        return false;
    }


    /**
     * 把dll文件复制到{JAVA_HOME}/bin里面
     */
    public static void initDllFile() {
        try {
            File files = ResourceUtils.getFile("classpath:dll");
            String jdkBinPath = new File(System.getProperty("java.home")).getParent() + File.separator + "bin";
            for (File file : files.listFiles()) {
                String filePath = file.getAbsolutePath().replace(files.getAbsolutePath(), jdkBinPath);
                if (file.isFile()) {
                    copyFile(file.getAbsolutePath(), filePath);
                }
            }
        } catch (IOException e) {
            logger.error(Constant.ERROR_MESSAGE, e);
        }
    }

    /**
     * 复制文件
     */
    private static void copyFile(String copyFilePath, String transferFilePath) throws IOException {
        InputStream input = new FileInputStream(copyFilePath);
        OutputStream output = new FileOutputStream(transferFilePath);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }
        input.close();
        output.close();
    }
}

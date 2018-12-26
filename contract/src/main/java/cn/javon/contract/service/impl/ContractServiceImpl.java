package cn.javon.contract.service.impl;

import cn.javon.contract.model.Contract;
import cn.javon.contract.service.ContractService;
import cn.javon.core.common.Constant;
import cn.javon.core.exception.ContractException;
import cn.javon.core.utils.ImageUtil;
import cn.javon.core.utils.JacobUtil;
import cn.javon.core.utils.PoiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    /**
     * 创建PDF合同
     * <p>
     * 1.对合同模版进行数据替换，并生成新的合同文件
     * 2.把合同文件从excel或word转成pdf文件
     *
     * @return
     */
    @Override
    public String createContract() {
        // TODO: 合同对象的封装
        Contract contract = new Contract();
        // 封装数据
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String contractNo = df.format(new Date());
        contract.setContractNo(contractNo);
        contract.setPartyA("敌敌畏");
        contract.setPartyB("万宝路");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        // 开始时间
        contract.setStartYear(String.valueOf(calendar.get(Calendar.YEAR)));
        contract.setStartMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        contract.setStartDay(String.valueOf(calendar.get(Calendar.DATE)));

        // 结束时间
        contract.setEndYear(String.valueOf(calendar.get(Calendar.YEAR) + 1));
        contract.setEndMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        contract.setEndDay(String.valueOf(calendar.get(Calendar.DATE)));

        df = new SimpleDateFormat("yyyy年MM月dd日");
        contract.setNowDate(df.format(new Date()));

        // TODO: 检验合同模版是否存在
        File file = null;
        try {
            file = ResourceUtils.getFile(Constant.TEMPLATE_PATH);
        } catch (FileNotFoundException e) {
            logger.error("合同模版不存在");
            return null;
        }

        // TODO: 替换模版，并生成新的
        String replaceResultName = contractNo + ".doc";
        PoiUtil.replaceWordModel(contract, file.getAbsolutePath(), this.getExportPath() + replaceResultName);
        // TODO: 把合同转成PDF显示
        String contractName = contractNo + ".pdf";
        JacobUtil.doc2Pdf(this.getExportPath() + replaceResultName, this.getExportPath() + contractName);
        // 返回合同文件名+后缀
        return contractName;
    }

    /**
     * 签名
     *
     * @param pdfName pdf名称
     * @param base64  签名的Base64数据
     * @return
     */
    @Override
    public String signature(String pdfName, String base64) {
        String contractPdfPath = this.getExportPath() + pdfName;
        String name = pdfName.substring(0, pdfName.indexOf("."));
        // TODO: 把base64转换成图片
        String signaturePngName = name + "签名.png";
        String signatureFilePath = this.getExportPath() + signaturePngName;
        boolean flag = ImageUtil.base64GeneratePng(base64, signatureFilePath);
        if (!flag) {
            throw new ContractException("签名保存失败");
        }
        // TODO: 把签名插入PDF，并且生成新的PDF
        String signaturePdfName = name + "（已签名）.pdf";
        String signaturePdfPath = this.getExportPath() + signaturePdfName;
        // 页码，图片宽，图片高，PDF座标X，PDF座标Y
        int[] local = new int[]{5, 120, 120, 210, 550};
        flag = PoiUtil.signaturePdf(contractPdfPath, signaturePdfPath, signatureFilePath, local);
        if (!flag) {
            throw new ContractException("Pdf文件插入签名失败");
        }
        // 返回签名后合同文件名+后缀
        return signaturePdfName;
    }

    /**
     * 生成合同的PDF转成Png图片
     *
     * @return
     */
    @Override
    public Map<String, Object> createContractImage() {
        Map<String, Object> result = new HashMap<String, Object>();
        String fileName = this.createContract();
        String pdfFilePath = this.getExportPath() + fileName;
        result.put("fileName", fileName.substring(0, fileName.indexOf(".")));
        result.put("filePngs", ImageUtil.pdf2Png(pdfFilePath));
        return result;
    }

    /**
     * 签名并且转换成PND
     *
     * @param fileName
     * @param base64
     * @return
     */
    @Override
    public Map<String, Object> signatureImage(String fileName, String base64) {
        // TODO: 调用上述签名方法生成签名PDF
        Map<String, Object> result = new HashMap<String, Object>();
        String signaturePdfName = this.signature(fileName + ".pdf", base64);
        String signaturePdfPath = this.getExportPath() + signaturePdfName;
        result.put("fileName", signaturePdfName.substring(0, signaturePdfName.indexOf(".")));
        // TODO: 把PDF转成图片
        result.put("filePngs", ImageUtil.pdf2Png(signaturePdfPath));
        return result;
    }

    private String getExportPath() {
        String webroot = System.getProperty("web.root");
        String exportPath = webroot + Constant.CONTRACT_FOLDER + File.separatorChar;
        return exportPath;
    }


}

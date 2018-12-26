package cn.javon.contract.controller;

import cn.javon.contract.service.ContractService;
import cn.javon.core.common.Constant;
import cn.javon.core.result.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ContractService contractService;

    /**
     * 创建PDF合同
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("createContract")
    public AjaxResult createContract() {
        AjaxResult ajaxResult = AjaxResult.success();
        try {
            String pdfPath = contractService.createContract();
            ajaxResult.setData(pdfPath);
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 对Pdf进行签名
     *
     * @param pdfName pdf名称
     * @param base64  签名的Base64数据
     * @return
     */
    @ResponseBody
    @RequestMapping("signature")
    public AjaxResult signature(String pdfName, String base64) {
        AjaxResult ajaxResult = AjaxResult.success();
        try {
            pdfName = contractService.signature(pdfName, base64);
            ajaxResult.setData(pdfName);
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 创建PNG合同
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("createContractImage")
    public AjaxResult createContractImage() {
        AjaxResult ajaxResult = AjaxResult.success();
        try {
            Map<String, Object> result = contractService.createContractImage();
            ajaxResult.setData(result);
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 对Pdf进行签名
     *
     * @param fileName 文件名称
     * @param base64  签名的Base64数据
     * @return
     */
    @ResponseBody
    @RequestMapping("signatureImage")
    public AjaxResult signatureImage(String fileName, String base64) {
        AjaxResult ajaxResult = AjaxResult.success();
        try {
            Map<String, Object> result = contractService.signatureImage(fileName, base64);
            ajaxResult.setData(result);
        } catch (Exception e) {
            logger.error(Constant.ERROR_MESSAGE, e);
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }
}

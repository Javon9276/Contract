package cn.javon.contract.service;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ContractService {

    String createContract();

    String signature(String pdfName, String base64);

    Map<String, Object> createContractImage();

    Map<String, Object> signatureImage(String fileName, String base64);
}

package cn.javon.core.exception;

/**
 * 异常处理
 *
 * @author Javon
 * @since 2018-12-11
 **/
public class ContractException extends RuntimeException {


    public ContractException() {
    }

    public ContractException(String message) {
        super(message);
    }

}

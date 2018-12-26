<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title>合同生成与签名</title>
    <link rel="stylesheet" href="css/weui.min.css">
    <link rel="stylesheet" href="css/jquery-weui.min.css">
    <style>
        .page__hd {
            padding: 40px;
        }
    </style>
</head>
<body ontouchstart>
<div class="page">
    <div class="page__hd">
        <h1 class="page__title">欢迎</h1>
        <p class="page__desc">请选择合同生成方式</p>
    </div>
    <div class="weui-btn-area">
        <a class="weui-btn weui-btn_primary" href="javascript:create()">创建PDF合同</a>
        <a class="weui-btn weui-btn_primary" href="javascript:createImage()">创建Image合同</a>
    </div>
</div>

</body>
<script type="text/javascript" src="js/jquery/jquery.js"></script>
<script type="text/javascript" src="js/jquery-weui.js"></script>
<script type="text/javascript">
    // 生成PDF合同
    function create() {
        $.showLoading("合同生成中");
        $.post("/createContract", {}, function (re) {
            if (re.code == 0) {
                window.location.href = "/jsp/contract.jsp?pdf=" + re.data;
            } else {
                $.alert("合同生成失败");
            }
            $.hideLoading();
        });
    }

    // 生成png合同
    function createImage() {
        $.showLoading("合同生成中");
        $.post("/createContractImage", {}, function (re) {
            if (re.code == 0) {
                window.location.href = "/jsp/contractImage.jsp?fileName=" + re.data.fileName + "&length=" + re.data.filePngs.length;
            } else {
                $.alert("合同生成失败");
            }
            $.hideLoading();
        });
    }
</script>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- HTML5文件 -->
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta content="telephone=no,email=no" name="format-detection">
    <title>查看合同</title>
    <!-- ui框架引入css -->
    <link rel="stylesheet" href="../css/weui.min.css">
    <link rel="stylesheet" href="../css/jquery-weui.min.css">
    <style>

        body {
            background-color: #f8f8f8;
        }

        .all_list_brands_title_details_btn {
            width: 80px;
            height: 80px;
            text-align: center;
            font-size: 20px;
            border-radius: 40px;
            position: fixed;
            border: 1px solid #d5d5d5;
            right: 10px;
            bottom: 10px;
            display: none;
            margin: 0 auto;
        }

        .weui-btn:after {
            border: 0;
        }

        .weui-mask.mask--visible {
            opacity: 1;
            visibility: visible;
            z-index: 80;
        }

        #signatureDialog .weui-dialog.dialog--visible {
            opacity: 1;
            visibility: visible;
            z-index: 90;
            top: 5%;
            transform: rotate(90deg);
            -ms-transform: rotate(90deg);
            -moz-transform: rotate(90deg);
            -webkit-transform: rotate(90deg);
            -o-transform: rotate(90deg);
            left: 90%;
        }

        #signatureDialog .dialog--visible .weui-dialog__bd {
            padding: 10px 0;
        }

        #tipDialog .weui-dialog.dialog--visible {
            opacity: 1;
            visibility: visible;
            -webkit-transform: scale(1) translate(-50%, -50%);
            transform: scale(1) translate(-50%, -50%);
            z-index: 90;
        }

        .canvas {
            border: 1px solid #e7ecef;
        }

        .toolbar {
            z-index: 70;
        }

        .stop {
            height: 100%;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
        }

    </style>

</head>

<body tabindex="1" class="loadingInProgress">
<div class="page article js_show">
    <div class="page__bd">
        <article class="weui-article">
            <p id="imgs">
                <img id="page1" src="" alt="">
                <img id="page2" src="" alt="">
            </p>
        </article>
    </div>
</div>
<div>
    <button id="signatureBtn" class="weui-btn weui-btn_primary all_list_brands_title_details_btn" style="width: 80px">
        签名
    </button>
</div>

<div id="signatureDialog" style="display: none;">
    <div class="weui-mask mask--visible"></div>
    <div class="weui-dialog dialog--visible">
        <div></div>
        <%--<div class="weui-dialog__hd"><strong class="weui-dialog__title">签名</strong></div>--%>
        <div class="weui-dialog__bd">
            <canvas class="canvas">您当前浏览器不支持canvas，建议更换浏览器！</canvas>
            <img id="signature"/>
        </div>
        <div class="weui-dialog__ft">
            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default" id="btn_clear">重置</a>
            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" id="btn_view">查看</a>
            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" id="btn_submit"
               style="display: none">提交</a>
        </div>
    </div>
</div>

<div id="tipDialog" style="display: none;">
    <div class="weui-mask mask--visible"></div>
    <div class="weui-dialog dialog--visible">
        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
        <div class="weui-dialog__bd">
            <p>由于手机屏幕较小，签名需要把屏幕转向到左边，详细操作如下：</p>
            <img style="width: 200px;" src="../img/sample.gif">
            <p>该提示6秒后消失</p>
        </div>
    </div>
</div>
</body>
<!-- jquery引入 -->
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/jquery-weui.js"></script>
<script>

    function getRequestParams() {
        var url = location.search; // 获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    }

    var params = getRequestParams();

    $(function () {
        var fileName = unescape(params.fileName);
        var length = unescape(params.length);
        $("title").html(fileName);
        var html = "";
        for (var i = 0; i < length; i++) {
            html += '<img src="/pdf/' + fileName + '_' + (i + 1) + '.png" alt="">';
        }
        $("#imgs").html(html);
        if (fileName.indexOf('（已签名）') < 0) {
            $('#signatureBtn').show();
        }
    });

    function initDrow() {
        var body = $('body');
        var dialog = $('#signatureDialog .dialog--visible');
        var btnHeight = 48;
        var canvas = document.querySelector('canvas'),
            ctx = canvas.getContext('2d');

        var _x = 0,
            _y = 0,
            x = 0,
            y = 0;
        var dialogWidth = body.height() * 0.9,
            dialogHeight = body.width() * 0.8;
        dialog.css("width", dialogWidth);
        dialog.css("max-width", dialogWidth);
        dialog.css("height", dialogHeight);
        dialog.css("max-height", dialogHeight);
        // 设置画布大小
        canvas.width = dialogWidth - 20;
        canvas.height = dialogHeight - 25 - btnHeight;
        // 开始绘制
        canvas.addEventListener('touchstart', function (e) {
            e.preventDefault();
            // 获取开始的x,y座标
            _x = e.touches[0].pageX,
                _y = e.touches[0].pageY;
            // 路径开始
            ctx.beginPath();
            ctx.moveTo(_y - canvas.offsetTop - dialog.offset().top, (canvas.height - (_x - canvas.offsetLeft - dialog.offset().left - btnHeight)));

            // 路径移动
            this.addEventListener('touchmove', function (e) {
                x = e.touches[0].pageX,
                    y = e.touches[0].pageY;
                ctx.lineTo(y - canvas.offsetTop - dialog.offset().top, (canvas.height - (x - canvas.offsetLeft - dialog.offset().left - btnHeight)));
                ctx.stroke();
            });
        });
        $("#btn_clear").click(function () {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            $("#signature").hide();
            $(".canvas").show();
            $("#signature").attr("src", '');
            $("#btn_view").show();
            $("#btn_submit").hide();
        });
        $("#btn_view").click(function () {
            $(".canvas").hide();
            $("#signature").attr("src", canvas.toDataURL("image/png"));
            $("#signature").show();
            $("#btn_submit").show();
            $(this).hide();
        });
        $("#btn_submit").click(function () {
            var base64 = $("#signature").attr("src");
            if (base64 == '') {
                $.alert('请签名后在点击提交');
                return;
            }
            var postObj = {
                fileName: unescape(params.fileName),
                base64: base64
            };
            $.showLoading("签名加载中");
            $.post("/signatureImage", postObj, function (re) {
                if (re.code == 0) {
                    window.location.href = "/jsp/contractImage.jsp?fileName=" + encodeURIComponent(escape(re.data.fileName)) + "&length=" + re.data.filePngs.length;
                } else {
                    $.alert(re.message);
                }
                $.hideLoading();
            });
        });
    }

    $('#signatureBtn').click(function () {
        $('body').addClass('stop');
        $('#viewerContainer').addClass('stop');
        $('#viewer').addClass('stop');
        $('#tipDialog').show();
        initDrow();
        setTimeout(function () {
            $('#tipDialog').hide();
            $('#signatureDialog').show();
        }, 6000);
    });
</script>
</html>


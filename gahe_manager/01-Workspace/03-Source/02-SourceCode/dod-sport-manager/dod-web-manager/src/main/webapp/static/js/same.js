
//设置 服务器域名地址
url = "http://192.168.1.168:8081/dodmanager";

var height = $('.right_side').height();
 var height1 =  $(window).height();
var height2 =  $(document).height();
console.log(height1)
console.log(height)
console.log(height2)
$('.left_side').height(height)

/**
* 获取token
* @param id
* @param password
*/
function getToken(id,password) {
    $.ajax({
        type: "POST",
        url: "http://192.168.1.146:8081/api/common/v1/getToken",
        async:true,//设为同步 可以让获取到的token在函数外使用
        dataType: "json",
        data: {
            'id':id,
            'password':password
        },
        success: function(data){
            token =data.datas.token;
            console.log(token)
        }
    });
}
function isLogin() {

    if(!sessionStorage.getItem('isLogin')){
        window.location.href = '../login.html';
    }
}
// isLogin();
//iframe从父页面获取子页面元素方法
function iframeIsLoad(iframe,callback){

    if(iframe.attachEvent) {

        iframe.attachEvent('onload',function(){

            callback && callback();

        });

    }else {

        iframe.onload = function(){

            callback && callback();

        }

    }

}


/**
 * 分页组件共用
 * @param func 获取分页数据的接口
 */
function pagination(func) {
    //        定义一个分页方法，可多次调用
    function paginationNick(opt) {
//            参数设置
        var pager = {
            paginationBox: '',//分页容器-- 必填
            mainBox: '',//内容盒子--必填
            numBtnBox: '',//数字按钮盒子-- 必填
            btnBox: '',//按钮盒子 --必填
            ipt: '',//input class-- 必填
            goBtn: '',//go btn class --必填
            currentBtn: '',//当前按钮class name --必填
            pageCount: 10,//每页显示几条数据
            numBtnCount: 4,//当前页左右两边各多少个数字按钮
            currentPage: 0,//当前页码data-page，首屏默认值
            maxCount: 0,//ajax请求数据分成的最大页码
            data: [],//ajax请求的数据
            explain: ''//对分页的总条数总页数的说明--必填
        };
        pager = $.extend(pager, opt);

        //请求数据页面跳转方法
        function goPage(btn) {
            //obj为ajax请求数据
            // var obj={other:{},value:[11,22,33,44,55,66,77,88,99,0,11,22,33,44,55,66,77,88,99,0,11,22,33,44,55,66,77,88,99,0,11,22,33,44,55,66,77,88,99,0]};
            //将展示的数据赋值给pager.data  (array)
            //pager.data=obj.value;
//                var num = 51;

//                设置当前页码
            if (!isNaN(btn)) {//数字按钮
                pager.currentPage = parseInt(btn);
                func(pager.currentPage + 1)
                //listBillInfo(pager.currentPage + 1);
                createExplain(pager.currentPage + 1)
            } else {
                switch (btn) {
                    case 'first':
                        pager.currentPage = 0;
                        //listBillInfo(pager.currentPage + 1);
                        func(pager.currentPage + 1)
                        createExplain(pager.currentPage + 1)
                        break;
                    case 'prev':
                        if (pager.currentPage > 0) {
                            --pager.currentPage;
                            //listBillInfo(pager.currentPage + 1);
                            func(pager.currentPage + 1)
                            createExplain(pager.currentPage + 1)
                        }
                        break;
                    case 'next':
                        if (pager.currentPage + 1 < pager.maxCount) {
                            ++pager.currentPage;
                            //listBillInfo(pager.currentPage + 1);
                            func(pager.currentPage + 1)
                            createExplain(pager.currentPage + 1)
                        }
                        break;
                    case 'last':
                        pager.currentPage = pager.maxCount - 1;
                        //listBillInfo(pager.currentPage + 1);
                        func(pager.currentPage + 1)
                        createExplain(pager.currentPage + 1)
                        break;
                }
                //设置ajax请求数据分成的最大页码
                pager.maxCount = num % pager.pageCount ? parseInt(num / pager.pageCount) + 1 :
                    num / pager.pageCount;
            }
            //创建数字按钮
            createNumBtn(pager.currentPage);
            //赋值给页码跳转输入框的value，表示当前页码
            $('.' + pager.btnBox + ' .' + pager.ipt).val(pager.currentPage + 1);
//              内容区填充数据
//                var arr=[],str='',str1='';
//                arr=pager.data.slice(pager.pageCount*pager.currentPage,
//                    num - pager.pageCount*(pager.currentPage+1) > -1 ?
//                        pager.pageCount*(pager.currentPage+1) : num);
//                arr.forEach(function(){
//                    // str+='<div>'+v+'</div>';
//                    str += datalist;
//                    //str1+='<li><div class="datanumber">001</div><div class="datamodel">行政模块</div><div class="dataoperate"><span class="edit"><a href="">编辑</a></span><span class="delete"><a href="">删除</a></span></div></li>'
//                });
            // $('.'+pager.mainBox).html(str1);
            //$('.'+pager.mainBox).html(str);
        }

        //创建非数字按钮和数据内容区
        function createOtherBtn() {
            $('.' + pager.paginationBox).html('<div class="' + pager.explain + '"></div><div class="' + pager.btnBox + '"><button data-page="first" class="first-btn">首页</button><button data-page="prev" class="prev-btn">上一页</button><span class="' + pager.numBtnBox + '"></span><button data-page="next" class="next-btn">下一页</button><input type="text" placeholder="请输入页码" class="' + pager.ipt + '"><button class="' + pager.goBtn + '">go</button><button data-page="last" class="last-btn">尾页</button></div>');

            //ipt value变化并赋值给go btn data-page
            $('.' + pager.btnBox + ' .' + pager.ipt).change(function () {
                if (!isNaN($(this).val())) {//是数字
                    if ($(this).val() > pager.maxCount) {//限制value最大值，跳转尾页
                        $(this).val(pager.maxCount);
                    }
                    if ($(this).val() < 1) {//限制value最小值，跳转首页
                        $(this).val(1);
                    }
                } else {//非数字清空value
                    $(this).val('');
                }
                $('.' + pager.btnBox + ' .' + pager.goBtn).attr('data-page', $(this).val() ? $(this).val() - 1 : '');
            });
            //每个btn绑定请求数据页面跳转方法

            $('.' + pager.btnBox + ' button').each(function (i, v) {
                $(this).click(function () {
                    //有值且不是上一次的页码时才调用
                    if (v.getAttribute('data-page') && v.getAttribute('data-page') != pager.currentPage) {
                        goPage(v.getAttribute('data-page'));
                    }
                });
            });
        }

        //创建提示容器explain
        function createExplain(cunpage) {
            //设置ajax请求数据分成的最大页码
            pager.maxCount = num % pager.pageCount ? parseInt(num / pager.pageCount) + 1 :
                num / pager.pageCount;
            $('.' + pager.explain).html('当前显示第' + cunpage + '页,共' + num + '条(总计' + pager.maxCount + '页)')
        }

        //创建数字按钮
        function createNumBtn(page) {
            //page是页面index从0开始，这里的page加减一要注意
            var str = '';
            if (pager.maxCount > pager.numBtnCount * 2) {//若最大页码数大于等于固定数字按钮总数（pager.numBtnCount*2+1）时
                //此页左边右边各pager.numBtnCount个数字按钮
                if (page - pager.numBtnCount > -1) {//此页左边有pager.numBtnCount页 page页码从0开始
                    for (var m = pager.numBtnCount; m > 0; m--) {
                        str += '<button data-page="' + (page - m) + '">' + (page - m + 1) + '</button>';
                    }
                } else {
                    for (var k = 0; k < page; k++) {
                        str += '<button data-page="' + k + '">' + (k + 1) + '</button>';
                    }
                }
                str += '<button data-page="' + page + '" class="' + pager.currentBtn + '" disabled="disabled">' + (page + 1) + '</button>';//此页
                if (pager.maxCount - page > 3) {//此页右边有pager.numBtnCount页 page页码从0开始
                    for (var j = 1; j < pager.numBtnCount + 1; j++) {
                        str += '<button data-page="' + (page + j) + '">' + (page + j + 1) + '</button>';
                    }
                } else {
                    for (var i = page + 1; i < pager.maxCount; i++) {
                        str += '<button data-page="' + i + '">' + (i + 1) + '</button>';
                    }
                }
                /*数字按钮总数小于固定的数字按钮总数pager.numBtnCount*2+1时，
                 这个分支，可以省略*/
                if (str.match(/<\/button>/ig).length < pager.numBtnCount * 2 + 1) {
                    str = '';
                    if (page < pager.numBtnCount) {//此页左边页码少于固定按钮数时
                        for (var n = 0; n < page; n++) {//此页左边
                            str += '<button data-page="' + n + '">' + (n + 1) + '</button>';
                        }
                        str += '<button data-page="' + page + '" class="' + pager.currentBtn + '" disabled="disabled">' + (page + 1) + '</button>';//此页
                        for (var x = 1; x < pager.numBtnCount * 2 + 1 - page; x++) {//此页右边
                            str += '<button data-page="' + (page + x) + '">' + (page + x + 1) + '</button>';
                        }
                    }
                    if (pager.maxCount - page - 1 < pager.numBtnCount) {
                        for (var y = pager.numBtnCount * 2 - (pager.maxCount - page - 1); y > 0; y--) {//此页左边
                            str += '<button data-page="' + (page - y) + '">' + (page - y + 1) + '</button>';
                        }
                        str += '<button data-page="' + page + '" class="' + pager.currentBtn + '" disabled="disabled">' + (page + 1) + '</button>';//此页
                        for (var z = page + 1; z < pager.maxCount; z++) {//此页右边
                            str += '<button data-page="' + z + '">' + (z + 1) + '</button>';
                        }
                    }
                }
            } else {
                str = '';
                for (var n = 0; n < page; n++) {//此页左边
                    str += '<button data-page="' + n + '">' + (n + 1) + '</button>';
                }
                str += '<button data-page="' + page + '" class="' + pager.currentBtn + '" disabled="disabled">' + (page + 1) + '</button>';//此页
                for (var x = 1; x < pager.maxCount - page; x++) {//此页右边
                    str += '<button data-page="' + (page + x) + '">' + (page + x + 1) + '</button>';
                }
            }

            $('.' + pager.numBtnBox).html(str);

            //每个btn绑定请求数据页面跳转方法
            $('.' + pager.numBtnBox + ' button').each(function (i, v) {
                $(this).click(function () {
                    goPage(v.getAttribute('data-page'))
                });
            });

            //按钮禁用
            $('.' + pager.btnBox + ' button').not('.' + pager.currentBtn).attr('disabled', false);
            if (!page) {//首页时
                $('.' + pager.btnBox + ' .first-btn').attr('disabled', true);
                $('.' + pager.btnBox + ' .prev-btn').attr('disabled', 'disabled');
            }
            if (page == pager.maxCount - 1) {//尾页时
                $('.' + pager.btnBox + ' .last-btn').attr('disabled', true);
                $('.' + pager.btnBox + ' .next-btn').attr('disabled', true);
            }
        }

        //首屏加载
        createOtherBtn();//首屏加载一次非数字按钮即可
        goPage(0);//请求数据页面跳转满足条件按钮点击都执行，首屏默认跳转到currentPage
    }

    //调用
    paginationNick({
        paginationBox: 'pagination-nick',//分页容器--必填
        // mainBox:'main-box-nick',//内容盒子--必填
        numBtnBox: 'num-box-nick',//数字按钮盒子-- 必填
        btnBox: 'btn-box-nick',//按钮盒子 --必填
        ipt: 'page-ipt-nick',//input class-- 必填
        goBtn: 'go-btn-nick',//go btn class --必填
        currentBtn: 'active-nick',//当前按钮class name --必填
        explain: 'paexplain'//分页提示
    });
}



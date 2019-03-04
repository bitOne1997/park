(function ($) {
    $.fn.layout.hiddenall = function () {
        if ($.cookie("lunei") == undefined || $.cookie("lunei") == null) {
            $.cookie("lunei", true);
        }
        console.log($.cookie("lunei"));
        var a = $('.treeview-menu li');
        a.hide();
        $('#llgl').hide();
        $('#luneichange').on('click', function () {
            $('.treeview-menu li').hide();
            $('#llgl').hide();
            $.fn.layout.lunei();
        });
        $('#luwaichange').on('click', function () {
            $('.treeview-menu li').hide();
            $('#llgl').hide();
            $.fn.layout.luwai();
        });
        if ($.cookie("lunei") == "true") {
            $.fn.layout.lunei();
        } else if ($.cookie("lunei") == "false") {
            $.fn.layout.luwai();
        }
        $('#onlyforcss').click();
    };
    $.fn.layout.luwai = function () {
        $.cookie("lunei", "false");
        $('#tcdsj').show();// 停车大数据
        $('#presentData').show();// 今日数据
        $('#jrsj2').show();// 停车云数据
        $('#tcc2').show();// 停车场管理
        $('#jrgk2').show();// 今日概况
        $('#cwdz2').show();// 财务对账
        $('#bwgk').show();// 泊位概况
        $('#wzyhgl2').show();// 用户管理
        $('#gdgl2').show();// 工单管理
    };
    $.fn.layout.lunei = function () {
        $.cookie("lunei", "true");
        $('#tcdsj').show();// 停车大数据
        $('#presentData').show();// 今日数据
        $('#jrsj2').show();// 停车云数据
        $('#tcc2').show();// 停车场管理
        $('#jrgk2').show();// 今日概况
        $('#cwgl').show();// 车位管理
        $('#cwzt').show();// 车位状态
        $('#cwdz2').show();// 财务管理
        $('#crkgl2').show();// 硬件管理
        $('#wzyhgl2').show();// 用户管理
        $('#gdgl2').show();// 工单管理
    };

})(jQuery);

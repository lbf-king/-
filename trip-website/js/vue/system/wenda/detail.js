var vue = new Vue({
    el:"#app",
    data:{
        question:{}
    },
    methods:{
    },
    mounted:function () {
        var param = getParams();
        var id = param.id;

        var _this = this;
        ajaxGet("/wenda/hahah", {rid:id}, function (data) {
            _this.question = data.data;
        })
    }
});


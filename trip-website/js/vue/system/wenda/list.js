var vue = new Vue({
    el:"#app",
    data:{
        questions:[]
    },
    methods:{
    },
    mounted:function () {
        var _this = this;
        ajaxGet("/wenda/list", {}, function (data) {
            _this.questions = data.data;
        })
    }
});


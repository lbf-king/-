var user = getUserInfo();

var vue = new Vue({
    el:"#app",
    data:{
        //userId对应的用户--窝的主人
        owner:{},
        isFollow:false,  //是否关注， true已经关注  false 未关注
        follows:[ //关注列表前4个
        ],
        followNum:100
    },
    methods:{
        follow:function () {
            var param = getParams();
            var userId = param.userId;
            //关注与取消关注--接口实现
            ajaxGet("/user/Follow",{userId:param.userId},function (data) {
                vue.isFollow= !vue.isFollow;
            })
        }
    },
    filters:{

    },
    mounted:function () {
        var param = getParams();
        //userId对应的用户关注列表---follows
        ajaxGet("/user/follows",{userId:param.userId},function (data) {
            console.log(data)
            vue.follows=data.data;
        })
        ajaxGet("/user/get",{userId:param.userId},function (data) {
            vue.owner=data.data;

        })
        //当前登录用户是否关注该用户了？--isFollow
        ajaxGet("/user/isFollow",{userId:param.userId},function (data) {
            vue.isFollow=data.data;
            console.log(data)

        })
        //该用户关注人数--followNum
        ajaxGet("/user/followNum",{userId:param.userId},function(data) {
            vue.followNum=data.data;
        })

    }
});
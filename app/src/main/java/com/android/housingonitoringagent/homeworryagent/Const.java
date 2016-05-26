package com.android.housingonitoringagent.homeworryagent;

/**
 * Created by Administrator on 2016/2/27 0027.
 */
public class Const {
    // public static  String SERVER ="http://test.zhijia51.com";
    public static String SERVER = "http://192.168.1.233:9000/";


    public interface RequestCode {
        int REQUESTPHONE = 0;
    }

    public interface HeadImage {
        int SIZE = 320;
    }

    public interface serviceMethod {
        /*发送短信验证码*/
        String SENDSMSCAPTCHA = SERVER + "/append/common/sendSmsCaptcha";
        /*登录*/
        String LOGON = SERVER + "/append/common/logon";
        /*注册*/
        String REGISTER = SERVER + "/append/common/register";
        /*忘记密码*/
        String SAVEPASSWORD = SERVER + "/append/common/savePassword";
        /*获取小区列表*/
        String VILLAGELIST = SERVER + "/append/village/villagelist";

        /*更换手机*/
        String SECURITY_MODIFYMOBILEPHONE = SERVER + "/append/common/modifymobilephone";
        /*通过手机更换密码*/
        String SECURITY_CHANGEPASSWORDBYPHONE = SERVER + "/append/common/modifypasswordbycaptcha";
        /*通过旧密码更改密码*/
        String SECURITY_CHANGEPASSWRODBYOLDPASSWORD = SERVER + "/append/common/modifypasswordbyoldpassword";

        /*获取租房列表*/
        String RENTALHOUSELIST = SERVER + "/append/rentalhouse/rentalhouselist";

        /*获取小区项目经理人详情*/
        String PROJECTMANAGERINFO = SERVER + "/append/village/projectmanagerinfo";

        /*获取邻居列表*/
        String NEIGHBOR_LIST = SERVER + "/append/neighbour/info";
        /*获取邻居列表*/
        String NEIGHBOR_ADD = SERVER + "/append/neighbour/addNm";
        /*获取邻居列表*/
        String NEIGHBOR_ADD_COMMENT = SERVER + "/append/neighbour/addNc";
        /*获取邻居列表*/
        String NEIGHBOR_ADD_REPLY = SERVER + "/append/neighbour/addNr";
        /*获取邻居列表*/
        String NEIGHBOR_DELETE = SERVER + "/append/neighbour/delNm";
        /*获取邻居列表*/
        String NEIGHBOR_DELETE_COMMENT = SERVER + "/append/neighbour/delNc";
        /*获取邻居列表*/
        String NEIGHBOR_DELETE_REPLY = SERVER + "/append/neighbour/delNr";
        /*获取小区问题列表*/
        String QUESTIONLIST = SERVER + "/append/village/questionlist";
        /*获取问题详情*/
        String QUESTIONINFO = SERVER + "/append/village/questioninfo";
        /*获取小区投票列表*/
        String VOTELIST = SERVER + "/append/village/votelist";
        /*获取投票详情*/
        String VOTEINFO = SERVER + "/append/village/voteinfo";
        /*获取小区投诉列表*/
        String COMPLAINTLIST = SERVER + "/append/village/complaintlist";
        /*获取小区投诉详情*/
        String COMPLAINTINFO = SERVER + "/append/village/complaintinfo";
        /*获取新房详情*/
        String NEWHOUSEINFO = SERVER + "/append/newhouse/newhouseinfo?";
        /*获取租房详情*/
        String RENTALHOUSEINFO = SERVER + "/append/rentalhouse/rentalhouseinfo?";
        /*获取小区详情*/
        String VILLAGEINFO = SERVER + "/append/village/villageinfo?";
        /*获取二手房投诉详情*/
        String SECONDHANDHOUSEINFO = SERVER + "/append/secondhandhouse/secondhandhouseinfo?";
        /*获取二手房列表*/
        String SECONDHANDHOUSELIST = SERVER + "/append/secondhandhouse/secondhandhouselist";
        /*文件上传*/
        String MULTIFILEUPLOAD = SERVER + "/append/common/fileupload";
        /*多文件上传*/
        String MULTIFILEUPLOADS = SERVER + "/append/common/fileuploads";
        /*是否有物业绑定*/
        String HOME_BINDHOUSE = SERVER + "/append/home/haveHouse";
        /*获取家园小区列表*/
        String HOME_SELFVILLAGELIST = SERVER + "/append/uservillage/selfVillageList";

        /*获取家园投票列表*/
        String HOME_GETVILLAGEVOTELIST = SERVER + "/append/uservote/list";
        /*获取家园投票详情*/
        String HOME_GETVOTEDEATIL = SERVER + "/append/uservote/info";
        /*家园投票*/
        String HOME_VOTEPUSH = SERVER + "/append/uservote/votepush";
        /*家园问题列表*/
        String HOME_QUESTIONLIST = SERVER + "/append/userquestion/list";
        /*家园问题详情*/
        String HOME_QUESTION_DETAIL = SERVER + "/append/userquestion/info";
        /*家园提交问题*/
        String HOME_COMMIT_QUESTION = SERVER + "/append/userquestion/save";
        /*获取房源信息*/
        String HOME_GET_PROPERTY_INFO = SERVER + "/append/userhouse/info";
        /*获取小区公告列表*/
        String ANNOUNCEMENTLIST = SERVER + "/append/village/announcementList";
        /*获取小区公告详情*/
        String ANNOUNCEMENTINFO = SERVER + "/append/village/announcementinfo";
        /*获取小区评价列表*/
        String EVALUATIONLIST = SERVER + "/append/village/evaluationList";
        /*添加小区评价*/
        String ADDEVALUATION = SERVER + "/append/village/addevaluation";
        /*广告*/
        String ADLIST = SERVER + "/append/ad/adlist";
        /*家园投诉列表*/
        String HOME_GET_COMPLAINT_LIST = SERVER + "/append/usercomplaint/list";
        /*家园检查有没有权限发布投诉*/
        String HOME_CHECK_PERMISSIONS_POST_COMPLAINT = SERVER + "/append/usercomplaint/verify";
        /*家园保存投诉*/
        String HOME_POST_COMPLAINT = SERVER + "/append/usercomplaint/save";
        /*家园投诉详情*/
        String HOME_GET_COMPLAINT_DETAIL = SERVER + "/append/usercomplaint/info";
        /*家园投诉同意和一般按钮*/
        String HOME_COMPLINT_CHANGE_STATUS = SERVER + "/append/usercomplaint/submitresultstatus";
        /*家园不同意按钮后发表意见*/
        String HOME_COMPLINT_CHANGE_STATU = SERVER + "/append/usercomplaint/submitcomment";
        /*所有物业*/
        String HOME_PROPERTY_LIST = SERVER + "/append/userhouse/list";
        /*绑定房源*/
        String HOME_BINDING_HOUSE = SERVER + "/append/home/bindHouse";
        /*完成问题，选择满意或不满意*/
        String HOME_COMPLETEQUESTION = SERVER + "/append/userquestion/completequestion";
        /*添加问题评论*/
        String HOME_QUESTION_ADD_COMMENT = SERVER + "/append/userquestion/addcomment";
        /*中介列表*/
        String HOME_INTERMEDIARY_LIST = SERVER + "/append/store/list";
        /*知识库*/
        String KNOWLEDGEBASE = SERVER + "/append/knowledge/knowledgeBase";
        /*新房列表*/
        String NEWHOUSELIST = SERVER + "/append/newhouse/newhouselist";
        /*获取标签*/
        String HOME_GET_LABEL = SERVER + "/append/userhouse/housetaglist";
        /*房源委托*/
        String HOME_POST_PROPERTY_INFO = SERVER + "/append/userhouse/delegate";
        /*公共设施*/
        String HOME_PUBLIC_PROVIDING_LIST = SERVER + "/append/uservillagefacility/list";
        /*获取新房筛选条件*/
        String NEWHOUSE_SELECTCONDITION = SERVER + "/append/newhouse/selectcondition";
        /*获取二手房筛选条件*/
        String SECONDARYHOUSE_SELECTCONDITION = SERVER + "/append/secondhandhouse/selectcondition";
        /*获取租房筛选条件*/
        String RENTALHOUSE_SELECTCONDITION = SERVER + "/append/rentalhouse/selectcondition";
        /*获取小区筛选条件*/
        String VILLAGE_SELECTCONDITION = SERVER + "/append/village/selectcondition";
        /*收件箱未读数量*/
        String UNREADCOUNT = SERVER + "/append/message/unreadcount";
        /*收件箱数量*/
        String MESSAGE = SERVER + "/append/message/list";
        /*删除收件箱*/
        String MESSAGEDELETE = SERVER + "/append/message/delete";
        /*通知详情*/
        String MESSAGE_INFO = SERVER + "/append/message/info";
        /*检查sessionId是否过期*/
        String CHECKSESSIONID = SERVER + "/append/common/checksessionid";
        /*添加、修改个人资料*/
        String MODIFYINFO = SERVER + "/append/personal/modifyinfo";
        /*查询文章详情*/
        String KNOWLEDGEBASEINFO = SERVER + "/append/knowledge/knowledgeBaseInfo?";
        /*收藏列表*/
        String MY_COLLECTION = SERVER + "/append/personal/collectionslist";
        /*取消收藏*/
        String CANCEL_COLLECTION = SERVER + "/append/personal/deletecollection";
        /*获取房评列表*/
        String HOUSEEVALUATELIST = SERVER + "/append/secondhandhouse/houseEvaluateList";
        /*用户协议*/
        String USERREGISTERDEAL = SERVER + "/frontend/userRegisterDeal";


    }

    // 账号
    public interface Account {
        int PHONE_LENGTH = 11;
        int PASSWORD_MIN_LENGTH = 6;
        int PASSWORD_MAX_LENGTH = 16;
    }

    public interface RefreshType {
        int REFRESH = 1;
        int LOAD = 2;
    }
}

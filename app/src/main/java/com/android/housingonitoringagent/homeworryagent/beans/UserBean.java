package com.android.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class UserBean implements Serializable {
    /**
     * resultCode : 1
     * message : 登录成功
     * content : {"sessionId":"e3767e8a-5fbb-4b65-aa18-8404f16c2423","user":{"id":"6371ca31-da7a-458a-88a3-98600bff2753","name":"测试1","sex":1,"age":20,"avatar":"/resources/_backend/images/avatar.jpg","address":null,"nickname":"测试1","mobilephone":"18918918910","email":null,"owner":false,"renter":false}}
     */

    private int resultCode;
    private String message;
    /**
     * sessionId : e3767e8a-5fbb-4b65-aa18-8404f16c2423
     * user : {"id":"6371ca31-da7a-458a-88a3-98600bff2753","name":"测试1","sex":1,"age":20,"avatar":"/resources/_backend/images/avatar.jpg","address":null,"nickname":"测试1","mobilephone":"18918918910","email":null,"owner":false,"renter":false}
     */

    private ContentBean content;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String sessionId;
        /**
         * id : 6371ca31-da7a-458a-88a3-98600bff2753
         * name : 测试1
         * sex : 1
         * age : 20
         * avatar : /resources/_backend/images/avatar.jpg
         * address : null
         * nickname : 测试1
         * mobilephone : 18918918910
         * email : null
         * owner : false
         * renter : false
         */

        private UserInfoBean user;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public UserInfoBean getUser() {
            return user;
        }

        public void setUser(UserInfoBean user) {
            this.user = user;
        }

        public static class UserInfoBean {
            private String id;
            private String name;
            private int sex;
            private int age;
            private String avatar;
            private Object address;
            private String nickname;
            private String mobilephone;
            private Object email;
            private boolean owner;
            private boolean renter;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public boolean isOwner() {
                return owner;
            }

            public void setOwner(boolean owner) {
                this.owner = owner;
            }

            public boolean isRenter() {
                return renter;
            }

            public void setRenter(boolean renter) {
                this.renter = renter;
            }
        }
    }

   /* @JSONField(name = "sessionId")
    private String sessionId;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "mobilephone")
    private String mobilephone;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "nickname")
    private String nickname;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "account")
    private String account;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "avatar")
    private String avatar;
    @JSONField(name = "idCard")
    private String idCard;
    @JSONField(name = "yz")
    private boolean yz;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public boolean isYz() {
        return yz;
    }

    public void setYz(boolean yz) {
        this.yz = yz;
    }*/

}

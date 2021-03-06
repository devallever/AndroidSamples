package com.allever.mysimple.retrofit.been;

/**
 * Created by Allever on 2016/12/7.
 */

public class LoginUser {
    private boolean seccess;
    private String message;
    private String session_id;
    private User user;

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User{
        private String id;
        private String username;
        private  String nickname;
        private String imagepath;
        private double longitude;
        private       double latiaude;
        private      String phone;
        private String email;
        private  String user_head_path;
        private String signature;
        private String city;
        private String sex;
        private int age;
        private String occupation;
        private  String constellation;
        private String hight;
        private  String weight;
        private  String figure;
        private  String emotion;
        private  int is_vip;
        private int is_recommended;
        private  String autoreaction;
        private String onlinestate;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getImagepath() {
            return imagepath;
        }

        public void setImagepath(String imagepath) {
            this.imagepath = imagepath;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatiaude() {
            return latiaude;
        }

        public void setLatiaude(double latiaude) {
            this.latiaude = latiaude;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_head_path() {
            return user_head_path;
        }

        public void setUser_head_path(String user_head_path) {
            this.user_head_path = user_head_path;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getHight() {
            return hight;
        }

        public void setHight(String hight) {
            this.hight = hight;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getFigure() {
            return figure;
        }

        public void setFigure(String figure) {
            this.figure = figure;
        }

        public String getEmotion() {
            return emotion;
        }

        public void setEmotion(String emotion) {
            this.emotion = emotion;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getIs_recommended() {
            return is_recommended;
        }

        public void setIs_recommended(int is_recommended) {
            this.is_recommended = is_recommended;
        }

        public String getAutoreaction() {
            return autoreaction;
        }

        public void setAutoreaction(String autoreaction) {
            this.autoreaction = autoreaction;
        }

        public String getOnlinestate() {
            return onlinestate;
        }

        public void setOnlinestate(String onlinestate) {
            this.onlinestate = onlinestate;
        }
    }
}

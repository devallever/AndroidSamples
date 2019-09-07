package com.allever.mysimple.retrofit.been;

import java.util.List;

/**
 * Created by Allever on 2016/12/6.
 */

public class UserBeen {

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

    public  class User{
        private String id;
        private String username;
        private String nickname;
        private double longitude;
        private double latiaude;
        private double distance;
        private int is_friend;
        private String phone;
        private String user_head_path;
        private String email;
        private String signature;
        private String city;
        private  String sex;
        private int age;
        private String occupation;
        private String constellation;
        private String hight;
        private String weight;
        private String figure;
        private String emotion;
        private int is_vip;
        private int accept_video;
        private int video_fee;
        private int is_follow;
        private String second_name;
        private String friendgroup_name;
        private List<String> list_news_img;


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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getIs_friend() {
            return is_friend;
        }

        public void setIs_friend(int is_friend) {
            this.is_friend = is_friend;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUser_head_path() {
            return user_head_path;
        }

        public void setUser_head_path(String user_head_path) {
            this.user_head_path = user_head_path;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public int getAccept_video() {
            return accept_video;
        }

        public void setAccept_video(int accept_video) {
            this.accept_video = accept_video;
        }

        public int getVideo_fee() {
            return video_fee;
        }

        public void setVideo_fee(int video_fee) {
            this.video_fee = video_fee;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public String getSecond_name() {
            return second_name;
        }

        public void setSecond_name(String second_name) {
            this.second_name = second_name;
        }

        public String getFriendgroup_name() {
            return friendgroup_name;
        }

        public void setFriendgroup_name(String friendgroup_name) {
            this.friendgroup_name = friendgroup_name;
        }

        public List<String> getList_news_img() {
            return list_news_img;
        }

        public void setList_news_img(List<String> list_news_img) {
            this.list_news_img = list_news_img;
        }
    }
}

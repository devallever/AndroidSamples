package com.allever.mysimple.retrofit.been;

import java.util.List;

/**
 * Created by Allever on 2016/12/9.
 */

public class AddNewsBeen {
    private boolean success;
    private String message;
    private News news;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public class News{
        private String id;
        private String content;
        private String user_id;
        private String date;
        private String longitude;
        private String latitude;
        private String city;
        private int is_commented;
        private int commentcount;
        private int lickcount;
        private List<String> news_image_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getIs_commented() {
            return is_commented;
        }

        public void setIs_commented(int is_commented) {
            this.is_commented = is_commented;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
        }

        public int getLickcount() {
            return lickcount;
        }

        public void setLickcount(int lickcount) {
            this.lickcount = lickcount;
        }

        public List<String> getNews_image_path() {
            return news_image_path;
        }

        public void setNews_image_path(List<String> news_image_path) {
            this.news_image_path = news_image_path;
        }
    }
}

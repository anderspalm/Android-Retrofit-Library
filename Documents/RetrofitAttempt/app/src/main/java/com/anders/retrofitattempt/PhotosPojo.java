package com.anders.retrofitattempt;

import java.util.ArrayList;

/**
 * Created by anders on 11/11/2016.
 */

public class PhotosPojo {

    private Photos photos;

    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }



    public class Photos {
        private String total;

        private String page;

        private String pages;

        private ArrayList<Photo> photo;

        private String perpage;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public ArrayList<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(ArrayList<Photo> photo) {
            this.photo = photo;
        }

        public String getPerpage() {
            return perpage;
        }

        public void setPerpage(String perpage) {
            this.perpage = perpage;
        }

    }

    public class Photo {
        private String isfamily;

        private String farm;

        private String id;

        private String title;

        private String ispublic;

        private String owner;

        private String secret;

        private String server;

        private String isfriend;

        public String getIsfamily() {
            return isfamily;
        }

        public void setIsfamily(String isfamily) {
            this.isfamily = isfamily;
        }

        public String getFarm() {
            return farm;
        }

        public void setFarm(String farm) {
            this.farm = farm;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIspublic() {
            return ispublic;
        }

        public void setIspublic(String ispublic) {
            this.ispublic = ispublic;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getIsfriend() {
            return isfriend;
        }

        public void setIsfriend(String isfriend) {
            this.isfriend = isfriend;
        }

    }
}

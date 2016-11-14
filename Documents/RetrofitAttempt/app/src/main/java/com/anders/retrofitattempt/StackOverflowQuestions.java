package com.anders.retrofitattempt;

import java.util.List;

/**
 * Created by anders on 11/11/2016.
 */

public class StackOverflowQuestions {

    public List<Question> items;

    public class Question {

        String title, link;
        List<String> tags;
        Owner owner;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }
    }

    public class Owner{

        String reputation, user_id;

        public String getReputation() {
            return reputation;
        }

        public void setReputation(String rep) {
            reputation = rep;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String id) {
            user_id = id;
        }
    }
}

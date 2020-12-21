package com.maybank.reynard_mangatta_takehome.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created By reynard on 21/12/20.
 */
public class Items {
    String login;
    @SerializedName("avatar_url")
    String avatarUrl;

    public String getLogin() {
        return login;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
}

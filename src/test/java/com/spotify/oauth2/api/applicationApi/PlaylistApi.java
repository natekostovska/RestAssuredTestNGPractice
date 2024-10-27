package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.tests.BaseTest;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.applicationApi.TokenManager.getToken;

public class PlaylistApi extends BaseTest {
    @Step
    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/"+ ConfigLoader.getInstance().getUser() +PLAYLISTS, getToken(), requestPlaylist);
    }

    // koga prakjame invalid token
    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post(USERS + "/"+ ConfigLoader.getInstance().getUser()+PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS+"/" + playlistId, getToken());
    }

    public static Response update(Playlist requestPlaylist, String playlistId) {
        return RestResource.update(PLAYLISTS+"/" + playlistId, getToken(), requestPlaylist);
    }

}

package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.api.applicationApi.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;

public class PlaylistExpiredTokenTest extends BaseTest {
    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String invalid_token="12345";
        Playlist requestPlaylist=PlaylistTests.playlistBuilder(generateName(),generateDescription(),false);
        Response response= PlaylistApi.post(invalid_token,requestPlaylist);
        PlaylistTests.assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        PlaylistTests.assertError(response.as(Error.class),StatusCode.CODE_401);
    }
}

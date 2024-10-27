package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.api.applicationApi.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests {
    @Story("Create a playlist story")
    @Link(name = "Website", url = "https://dev.example.com/")
    @Issue("AUTH-123")
    @TmsLink("TMS-456")
    @Description("this is the description")
    @Test(description = "should be able to create a playlist")
    public void shouldBeAbleToCreateAPlaylist() {
        // builder pattern
        Playlist requestPlaylist=playlistBuilder(generateName(),generateDescription(),false);
        Response response= PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetAPlaylist() {
        Playlist requestPlaylist=playlistBuilder("Updated Playlist Name","Updated playlist description",true);
        Response response= PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist=playlistBuilder(generateName(),generateDescription(),false);
        Response response= PlaylistApi.update(requestPlaylist,DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
    }

    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist=playlistBuilder("",generateDescription(),false);
        Response response= PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_400);
        assertError(response.as(com.spotify.oauth2.pojo.Error.class),StatusCode.CODE_400);

    }

    @Step
    public static Playlist playlistBuilder(String name, String description, boolean _public){
        // Using builder pattern, the getters will remain same
        return Playlist.builder().name(name).description(description)._public(_public).build();
        // implement without builder
     /*   Playlist playlist=new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;*/
    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist,Playlist responsePlaylist)
    {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public static void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }

    @Step
    public static void assertError(Error responseErr, StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));

    }
}

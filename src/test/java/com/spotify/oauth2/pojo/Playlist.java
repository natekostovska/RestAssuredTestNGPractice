package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


// With Lombok we do not need to use those setter and getter methods, all we need to do is declare the variables
// https://projectlombok.org/  https://projectlombok.org/features/
// https://projectlombok.org/features/experimental/ @Jacksonized
//Bob, meet Jackson. Let's make sure you become fast friends. we need to add it as a dependency
// https://mvnrepository.com/artifact/org.projectlombok/lombok
// we need to install lombok plug in as well https://projectlombok.org/setup/intellij

@Value // we do not need to use the private access modifier for the variables
//@Data // apart getter and setter lombok will generate new useful methods as well
//@Getter @Setter
@Jacksonized
@Builder // will create builder method and pattern, // will create builder method and pattern if we set it,
// lombok will not work with jackson annotations we need to add @Jacksonized.
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Playlist {

 @JsonProperty("collaborative")
 Boolean collaborative;
 @JsonProperty("description")
 String description;
 @JsonProperty("external_urls")
 ExternalUrls externalUrls;
 @JsonProperty("followers")
 Followers followers;
 @JsonProperty("href")
 String href;
 @JsonProperty("id")
 String id;
 @JsonProperty("images")
 Object images;
 @JsonProperty("name")
 String name;
 @JsonProperty("owner")
 Owner owner;
 @JsonProperty("primary_color")
 Object primaryColor;
 @JsonProperty("public")
 Boolean _public;
 @JsonProperty("snapshot_id")
 String snapshotId;
 @JsonProperty("tracks")
 Tracks tracks;
 @JsonProperty("type")
 String type;
 @JsonProperty("uri")
 String uri;

}
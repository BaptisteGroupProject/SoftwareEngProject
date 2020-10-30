package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the UserLocation type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserLocations")
public final class UserLocation implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField USERNAME = field("username");
  public static final QueryField LATITUDE = field("latitude");
  public static final QueryField LONGITUDE = field("longitude");
  public static final QueryField LOCATION = field("location");
  public static final QueryField TIME = field("time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String username;
  private final @ModelField(targetType="Float") Float latitude;
  private final @ModelField(targetType="Float") Float longitude;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="String") String time;
  public String getId() {
      return id;
  }
  
  public String getUsername() {
      return username;
  }
  
  public Float getLatitude() {
      return latitude;
  }
  
  public Float getLongitude() {
      return longitude;
  }
  
  public String getLocation() {
      return location;
  }
  
  public String getTime() {
      return time;
  }
  
  private UserLocation(String id, String username, Float latitude, Float longitude, String location, String time) {
    this.id = id;
    this.username = username;
    this.latitude = latitude;
    this.longitude = longitude;
    this.location = location;
    this.time = time;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserLocation userLocation = (UserLocation) obj;
      return ObjectsCompat.equals(getId(), userLocation.getId()) &&
              ObjectsCompat.equals(getUsername(), userLocation.getUsername()) &&
              ObjectsCompat.equals(getLatitude(), userLocation.getLatitude()) &&
              ObjectsCompat.equals(getLongitude(), userLocation.getLongitude()) &&
              ObjectsCompat.equals(getLocation(), userLocation.getLocation()) &&
              ObjectsCompat.equals(getTime(), userLocation.getTime());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getLatitude())
      .append(getLongitude())
      .append(getLocation())
      .append(getTime())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserLocation {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("latitude=" + String.valueOf(getLatitude()) + ", ")
      .append("longitude=" + String.valueOf(getLongitude()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("time=" + String.valueOf(getTime()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static UserLocation justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UserLocation(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      latitude,
      longitude,
      location,
      time);
  }
  public interface BuildStep {
    UserLocation build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep username(String username);
    BuildStep latitude(Float latitude);
    BuildStep longitude(Float longitude);
    BuildStep location(String location);
    BuildStep time(String time);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String username;
    private Float latitude;
    private Float longitude;
    private String location;
    private String time;
    @Override
     public UserLocation build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserLocation(
          id,
          username,
          latitude,
          longitude,
          location,
          time);
    }
    
    @Override
     public BuildStep username(String username) {
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }
    
    @Override
     public BuildStep longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        this.time = time;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String username, Float latitude, Float longitude, String location, String time) {
      super.id(id);
      super.username(username)
        .latitude(latitude)
        .longitude(longitude)
        .location(location)
        .time(time);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder latitude(Float latitude) {
      return (CopyOfBuilder) super.latitude(latitude);
    }
    
    @Override
     public CopyOfBuilder longitude(Float longitude) {
      return (CopyOfBuilder) super.longitude(longitude);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
  }
  
}

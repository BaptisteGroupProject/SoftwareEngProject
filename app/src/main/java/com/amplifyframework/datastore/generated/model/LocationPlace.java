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

/** This is an auto generated class representing the LocationPlace type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LocationPlaces")
public final class LocationPlace implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField FIRST_POSTCODE = field("firstPostcode");
  public static final QueryField LOCATION_ITEMS = field("locationItems");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String firstPostcode;
  private final @ModelField(targetType="String", isRequired = true) List<String> locationItems;
  public String getId() {
      return id;
  }
  
  public String getFirstPostcode() {
      return firstPostcode;
  }
  
  public List<String> getLocationItems() {
      return locationItems;
  }
  
  private LocationPlace(String id, String firstPostcode, List<String> locationItems) {
    this.id = id;
    this.firstPostcode = firstPostcode;
    this.locationItems = locationItems;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LocationPlace locationPlace = (LocationPlace) obj;
      return ObjectsCompat.equals(getId(), locationPlace.getId()) &&
              ObjectsCompat.equals(getFirstPostcode(), locationPlace.getFirstPostcode()) &&
              ObjectsCompat.equals(getLocationItems(), locationPlace.getLocationItems());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstPostcode())
      .append(getLocationItems())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LocationPlace {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstPostcode=" + String.valueOf(getFirstPostcode()) + ", ")
      .append("locationItems=" + String.valueOf(getLocationItems()))
      .append("}")
      .toString();
  }
  
  public static LocationItemsStep builder() {
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
  public static LocationPlace justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new LocationPlace(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstPostcode,
      locationItems);
  }
  public interface LocationItemsStep {
    BuildStep locationItems(List<String> locationItems);
  }
  

  public interface BuildStep {
    LocationPlace build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep firstPostcode(String firstPostcode);
  }
  

  public static class Builder implements LocationItemsStep, BuildStep {
    private String id;
    private List<String> locationItems;
    private String firstPostcode;
    @Override
     public LocationPlace build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LocationPlace(
          id,
          firstPostcode,
          locationItems);
    }
    
    @Override
     public BuildStep locationItems(List<String> locationItems) {
        Objects.requireNonNull(locationItems);
        this.locationItems = locationItems;
        return this;
    }
    
    @Override
     public BuildStep firstPostcode(String firstPostcode) {
        this.firstPostcode = firstPostcode;
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
    private CopyOfBuilder(String id, String firstPostcode, List<String> locationItems) {
      super.id(id);
      super.locationItems(locationItems)
        .firstPostcode(firstPostcode);
    }
    
    @Override
     public CopyOfBuilder locationItems(List<String> locationItems) {
      return (CopyOfBuilder) super.locationItems(locationItems);
    }
    
    @Override
     public CopyOfBuilder firstPostcode(String firstPostcode) {
      return (CopyOfBuilder) super.firstPostcode(firstPostcode);
    }
  }
  
}

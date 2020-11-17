package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the LocationByFirstPostcode type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LocationByFirstPostcodes")
public final class LocationByFirstPostcode implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField FIRST_POSTCODE = field("firstPostcode");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String firstPostcode;
  private final @ModelField(targetType="LocationPlace") @HasMany(associatedWith = "firstPostcode", type = LocationPlace.class) List<LocationPlace> locationPlaces = null;
  public String getId() {
      return id;
  }
  
  public String getFirstPostcode() {
      return firstPostcode;
  }
  
  public List<LocationPlace> getLocationPlaces() {
      return locationPlaces;
  }
  
  private LocationByFirstPostcode(String id, String firstPostcode) {
    this.id = id;
    this.firstPostcode = firstPostcode;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LocationByFirstPostcode locationByFirstPostcode = (LocationByFirstPostcode) obj;
      return ObjectsCompat.equals(getId(), locationByFirstPostcode.getId()) &&
              ObjectsCompat.equals(getFirstPostcode(), locationByFirstPostcode.getFirstPostcode());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstPostcode())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LocationByFirstPostcode {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstPostcode=" + String.valueOf(getFirstPostcode()))
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
  public static LocationByFirstPostcode justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new LocationByFirstPostcode(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstPostcode);
  }
  public interface BuildStep {
    LocationByFirstPostcode build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep firstPostcode(String firstPostcode);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String firstPostcode;
    @Override
     public LocationByFirstPostcode build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LocationByFirstPostcode(
          id,
          firstPostcode);
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
    private CopyOfBuilder(String id, String firstPostcode) {
      super.id(id);
      super.firstPostcode(firstPostcode);
    }
    
    @Override
     public CopyOfBuilder firstPostcode(String firstPostcode) {
      return (CopyOfBuilder) super.firstPostcode(firstPostcode);
    }
  }
  
}

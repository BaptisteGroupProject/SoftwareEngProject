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

/** This is an auto generated class representing the PricePaidDataJson type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "PricePaidDataJsons")
public final class PricePaidDataJson implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField POSTCODE = field("postcode");
  public static final QueryField LOCATION_PAID_B = field("locationPaidB");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String postcode;
  private final @ModelField(targetType="String", isRequired = true) List<String> locationPaidB;
  public String getId() {
      return id;
  }
  
  public String getPostcode() {
      return postcode;
  }
  
  public List<String> getLocationPaidB() {
      return locationPaidB;
  }
  
  private PricePaidDataJson(String id, String postcode, List<String> locationPaidB) {
    this.id = id;
    this.postcode = postcode;
    this.locationPaidB = locationPaidB;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      PricePaidDataJson pricePaidDataJson = (PricePaidDataJson) obj;
      return ObjectsCompat.equals(getId(), pricePaidDataJson.getId()) &&
              ObjectsCompat.equals(getPostcode(), pricePaidDataJson.getPostcode()) &&
              ObjectsCompat.equals(getLocationPaidB(), pricePaidDataJson.getLocationPaidB());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPostcode())
      .append(getLocationPaidB())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("PricePaidDataJson {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("postcode=" + String.valueOf(getPostcode()) + ", ")
      .append("locationPaidB=" + String.valueOf(getLocationPaidB()))
      .append("}")
      .toString();
  }
  
  public static LocationPaidBStep builder() {
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
  public static PricePaidDataJson justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new PricePaidDataJson(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      postcode,
      locationPaidB);
  }
  public interface LocationPaidBStep {
    BuildStep locationPaidB(List<String> locationPaidB);
  }
  

  public interface BuildStep {
    PricePaidDataJson build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep postcode(String postcode);
  }
  

  public static class Builder implements LocationPaidBStep, BuildStep {
    private String id;
    private List<String> locationPaidB;
    private String postcode;
    @Override
     public PricePaidDataJson build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new PricePaidDataJson(
          id,
          postcode,
          locationPaidB);
    }
    
    @Override
     public BuildStep locationPaidB(List<String> locationPaidB) {
        Objects.requireNonNull(locationPaidB);
        this.locationPaidB = locationPaidB;
        return this;
    }
    
    @Override
     public BuildStep postcode(String postcode) {
        this.postcode = postcode;
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
    private CopyOfBuilder(String id, String postcode, List<String> locationPaidB) {
      super.id(id);
      super.locationPaidB(locationPaidB)
        .postcode(postcode);
    }
    
    @Override
     public CopyOfBuilder locationPaidB(List<String> locationPaidB) {
      return (CopyOfBuilder) super.locationPaidB(locationPaidB);
    }
    
    @Override
     public CopyOfBuilder postcode(String postcode) {
      return (CopyOfBuilder) super.postcode(postcode);
    }
  }
  
}

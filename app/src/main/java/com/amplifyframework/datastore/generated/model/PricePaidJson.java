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

/** This is an auto generated class representing the PricePaidJson type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "PricePaidJsons")
public final class PricePaidJson implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField POSTCODE = field("postcode");
  public static final QueryField LOCATION_PAID = field("locationPaid");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String postcode;
  private final @ModelField(targetType="String", isRequired = true) List<String> locationPaid;
  public String getId() {
      return id;
  }
  
  public String getPostcode() {
      return postcode;
  }
  
  public List<String> getLocationPaid() {
      return locationPaid;
  }
  
  private PricePaidJson(String id, String postcode, List<String> locationPaid) {
    this.id = id;
    this.postcode = postcode;
    this.locationPaid = locationPaid;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      PricePaidJson pricePaidJson = (PricePaidJson) obj;
      return ObjectsCompat.equals(getId(), pricePaidJson.getId()) &&
              ObjectsCompat.equals(getPostcode(), pricePaidJson.getPostcode()) &&
              ObjectsCompat.equals(getLocationPaid(), pricePaidJson.getLocationPaid());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPostcode())
      .append(getLocationPaid())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("PricePaidJson {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("postcode=" + String.valueOf(getPostcode()) + ", ")
      .append("locationPaid=" + String.valueOf(getLocationPaid()))
      .append("}")
      .toString();
  }
  
  public static LocationPaidStep builder() {
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
  public static PricePaidJson justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new PricePaidJson(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      postcode,
      locationPaid);
  }
  public interface LocationPaidStep {
    BuildStep locationPaid(List<String> locationPaid);
  }
  

  public interface BuildStep {
    PricePaidJson build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep postcode(String postcode);
  }
  

  public static class Builder implements LocationPaidStep, BuildStep {
    private String id;
    private List<String> locationPaid;
    private String postcode;
    @Override
     public PricePaidJson build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new PricePaidJson(
          id,
          postcode,
          locationPaid);
    }
    
    @Override
     public BuildStep locationPaid(List<String> locationPaid) {
        Objects.requireNonNull(locationPaid);
        this.locationPaid = locationPaid;
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
    private CopyOfBuilder(String id, String postcode, List<String> locationPaid) {
      super.id(id);
      super.locationPaid(locationPaid)
        .postcode(postcode);
    }
    
    @Override
     public CopyOfBuilder locationPaid(List<String> locationPaid) {
      return (CopyOfBuilder) super.locationPaid(locationPaid);
    }
    
    @Override
     public CopyOfBuilder postcode(String postcode) {
      return (CopyOfBuilder) super.postcode(postcode);
    }
  }
  
}

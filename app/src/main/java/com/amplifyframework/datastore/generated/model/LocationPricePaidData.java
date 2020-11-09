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

/** This is an auto generated class representing the LocationPricePaidData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LocationPricePaidData")
public final class LocationPricePaidData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField UNIQUE_IDENTIFIER = field("uniqueIdentifier");
  public static final QueryField PRICE = field("price");
  public static final QueryField TRANSFER_DATE = field("transferDate");
  public static final QueryField POSTCODE = field("postcode");
  public static final QueryField PROPERTY_TYPE = field("propertyType");
  public static final QueryField NEW_OR_OLD = field("newOrOld");
  public static final QueryField DURATION = field("duration");
  public static final QueryField PAON = field("paon");
  public static final QueryField SAON = field("saon");
  public static final QueryField STREES = field("strees");
  public static final QueryField LOCALITY = field("locality");
  public static final QueryField TOWN = field("town");
  public static final QueryField DISTRICT = field("district");
  public static final QueryField COUNTRY = field("country");
  public static final QueryField CATEGORY_TYPE = field("categoryType");
  public static final QueryField RECORD_STATUS = field("recordStatus");
  public static final QueryField LONGITUDE = field("longitude");
  public static final QueryField LATITUDE = field("latitude");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String uniqueIdentifier;
  private final @ModelField(targetType="Int") Integer price;
  private final @ModelField(targetType="String") String transferDate;
  private final @ModelField(targetType="String") String postcode;
  private final @ModelField(targetType="String") String propertyType;
  private final @ModelField(targetType="String") String newOrOld;
  private final @ModelField(targetType="String") String duration;
  private final @ModelField(targetType="String") String paon;
  private final @ModelField(targetType="String") String saon;
  private final @ModelField(targetType="String") String strees;
  private final @ModelField(targetType="String") String locality;
  private final @ModelField(targetType="String") String town;
  private final @ModelField(targetType="String") String district;
  private final @ModelField(targetType="String") String country;
  private final @ModelField(targetType="String") String categoryType;
  private final @ModelField(targetType="String") String recordStatus;
  private final @ModelField(targetType="Float") Float longitude;
  private final @ModelField(targetType="Float") Float latitude;
  public String getId() {
      return id;
  }
  
  public String getUniqueIdentifier() {
      return uniqueIdentifier;
  }
  
  public Integer getPrice() {
      return price;
  }
  
  public String getTransferDate() {
      return transferDate;
  }
  
  public String getPostcode() {
      return postcode;
  }
  
  public String getPropertyType() {
      return propertyType;
  }
  
  public String getNewOrOld() {
      return newOrOld;
  }
  
  public String getDuration() {
      return duration;
  }
  
  public String getPaon() {
      return paon;
  }
  
  public String getSaon() {
      return saon;
  }
  
  public String getStrees() {
      return strees;
  }
  
  public String getLocality() {
      return locality;
  }
  
  public String getTown() {
      return town;
  }
  
  public String getDistrict() {
      return district;
  }
  
  public String getCountry() {
      return country;
  }
  
  public String getCategoryType() {
      return categoryType;
  }
  
  public String getRecordStatus() {
      return recordStatus;
  }
  
  public Float getLongitude() {
      return longitude;
  }
  
  public Float getLatitude() {
      return latitude;
  }
  
  private LocationPricePaidData(String id, String uniqueIdentifier, Integer price, String transferDate, String postcode, String propertyType, String newOrOld, String duration, String paon, String saon, String strees, String locality, String town, String district, String country, String categoryType, String recordStatus, Float longitude, Float latitude) {
    this.id = id;
    this.uniqueIdentifier = uniqueIdentifier;
    this.price = price;
    this.transferDate = transferDate;
    this.postcode = postcode;
    this.propertyType = propertyType;
    this.newOrOld = newOrOld;
    this.duration = duration;
    this.paon = paon;
    this.saon = saon;
    this.strees = strees;
    this.locality = locality;
    this.town = town;
    this.district = district;
    this.country = country;
    this.categoryType = categoryType;
    this.recordStatus = recordStatus;
    this.longitude = longitude;
    this.latitude = latitude;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LocationPricePaidData locationPricePaidData = (LocationPricePaidData) obj;
      return ObjectsCompat.equals(getId(), locationPricePaidData.getId()) &&
              ObjectsCompat.equals(getUniqueIdentifier(), locationPricePaidData.getUniqueIdentifier()) &&
              ObjectsCompat.equals(getPrice(), locationPricePaidData.getPrice()) &&
              ObjectsCompat.equals(getTransferDate(), locationPricePaidData.getTransferDate()) &&
              ObjectsCompat.equals(getPostcode(), locationPricePaidData.getPostcode()) &&
              ObjectsCompat.equals(getPropertyType(), locationPricePaidData.getPropertyType()) &&
              ObjectsCompat.equals(getNewOrOld(), locationPricePaidData.getNewOrOld()) &&
              ObjectsCompat.equals(getDuration(), locationPricePaidData.getDuration()) &&
              ObjectsCompat.equals(getPaon(), locationPricePaidData.getPaon()) &&
              ObjectsCompat.equals(getSaon(), locationPricePaidData.getSaon()) &&
              ObjectsCompat.equals(getStrees(), locationPricePaidData.getStrees()) &&
              ObjectsCompat.equals(getLocality(), locationPricePaidData.getLocality()) &&
              ObjectsCompat.equals(getTown(), locationPricePaidData.getTown()) &&
              ObjectsCompat.equals(getDistrict(), locationPricePaidData.getDistrict()) &&
              ObjectsCompat.equals(getCountry(), locationPricePaidData.getCountry()) &&
              ObjectsCompat.equals(getCategoryType(), locationPricePaidData.getCategoryType()) &&
              ObjectsCompat.equals(getRecordStatus(), locationPricePaidData.getRecordStatus()) &&
              ObjectsCompat.equals(getLongitude(), locationPricePaidData.getLongitude()) &&
              ObjectsCompat.equals(getLatitude(), locationPricePaidData.getLatitude());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUniqueIdentifier())
      .append(getPrice())
      .append(getTransferDate())
      .append(getPostcode())
      .append(getPropertyType())
      .append(getNewOrOld())
      .append(getDuration())
      .append(getPaon())
      .append(getSaon())
      .append(getStrees())
      .append(getLocality())
      .append(getTown())
      .append(getDistrict())
      .append(getCountry())
      .append(getCategoryType())
      .append(getRecordStatus())
      .append(getLongitude())
      .append(getLatitude())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LocationPricePaidData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("uniqueIdentifier=" + String.valueOf(getUniqueIdentifier()) + ", ")
      .append("price=" + String.valueOf(getPrice()) + ", ")
      .append("transferDate=" + String.valueOf(getTransferDate()) + ", ")
      .append("postcode=" + String.valueOf(getPostcode()) + ", ")
      .append("propertyType=" + String.valueOf(getPropertyType()) + ", ")
      .append("newOrOld=" + String.valueOf(getNewOrOld()) + ", ")
      .append("duration=" + String.valueOf(getDuration()) + ", ")
      .append("paon=" + String.valueOf(getPaon()) + ", ")
      .append("saon=" + String.valueOf(getSaon()) + ", ")
      .append("strees=" + String.valueOf(getStrees()) + ", ")
      .append("locality=" + String.valueOf(getLocality()) + ", ")
      .append("town=" + String.valueOf(getTown()) + ", ")
      .append("district=" + String.valueOf(getDistrict()) + ", ")
      .append("country=" + String.valueOf(getCountry()) + ", ")
      .append("categoryType=" + String.valueOf(getCategoryType()) + ", ")
      .append("recordStatus=" + String.valueOf(getRecordStatus()) + ", ")
      .append("longitude=" + String.valueOf(getLongitude()) + ", ")
      .append("latitude=" + String.valueOf(getLatitude()))
      .append("}")
      .toString();
  }
  
  public static UniqueIdentifierStep builder() {
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
  public static LocationPricePaidData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new LocationPricePaidData(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      uniqueIdentifier,
      price,
      transferDate,
      postcode,
      propertyType,
      newOrOld,
      duration,
      paon,
      saon,
      strees,
      locality,
      town,
      district,
      country,
      categoryType,
      recordStatus,
      longitude,
      latitude);
  }
  public interface UniqueIdentifierStep {
    BuildStep uniqueIdentifier(String uniqueIdentifier);
  }
  

  public interface BuildStep {
    LocationPricePaidData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep price(Integer price);
    BuildStep transferDate(String transferDate);
    BuildStep postcode(String postcode);
    BuildStep propertyType(String propertyType);
    BuildStep newOrOld(String newOrOld);
    BuildStep duration(String duration);
    BuildStep paon(String paon);
    BuildStep saon(String saon);
    BuildStep strees(String strees);
    BuildStep locality(String locality);
    BuildStep town(String town);
    BuildStep district(String district);
    BuildStep country(String country);
    BuildStep categoryType(String categoryType);
    BuildStep recordStatus(String recordStatus);
    BuildStep longitude(Float longitude);
    BuildStep latitude(Float latitude);
  }
  

  public static class Builder implements UniqueIdentifierStep, BuildStep {
    private String id;
    private String uniqueIdentifier;
    private Integer price;
    private String transferDate;
    private String postcode;
    private String propertyType;
    private String newOrOld;
    private String duration;
    private String paon;
    private String saon;
    private String strees;
    private String locality;
    private String town;
    private String district;
    private String country;
    private String categoryType;
    private String recordStatus;
    private Float longitude;
    private Float latitude;
    @Override
     public LocationPricePaidData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LocationPricePaidData(
          id,
          uniqueIdentifier,
          price,
          transferDate,
          postcode,
          propertyType,
          newOrOld,
          duration,
          paon,
          saon,
          strees,
          locality,
          town,
          district,
          country,
          categoryType,
          recordStatus,
          longitude,
          latitude);
    }
    
    @Override
     public BuildStep uniqueIdentifier(String uniqueIdentifier) {
        Objects.requireNonNull(uniqueIdentifier);
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }
    
    @Override
     public BuildStep price(Integer price) {
        this.price = price;
        return this;
    }
    
    @Override
     public BuildStep transferDate(String transferDate) {
        this.transferDate = transferDate;
        return this;
    }
    
    @Override
     public BuildStep postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }
    
    @Override
     public BuildStep propertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }
    
    @Override
     public BuildStep newOrOld(String newOrOld) {
        this.newOrOld = newOrOld;
        return this;
    }
    
    @Override
     public BuildStep duration(String duration) {
        this.duration = duration;
        return this;
    }
    
    @Override
     public BuildStep paon(String paon) {
        this.paon = paon;
        return this;
    }
    
    @Override
     public BuildStep saon(String saon) {
        this.saon = saon;
        return this;
    }
    
    @Override
     public BuildStep strees(String strees) {
        this.strees = strees;
        return this;
    }
    
    @Override
     public BuildStep locality(String locality) {
        this.locality = locality;
        return this;
    }
    
    @Override
     public BuildStep town(String town) {
        this.town = town;
        return this;
    }
    
    @Override
     public BuildStep district(String district) {
        this.district = district;
        return this;
    }
    
    @Override
     public BuildStep country(String country) {
        this.country = country;
        return this;
    }
    
    @Override
     public BuildStep categoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
    }
    
    @Override
     public BuildStep recordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
        return this;
    }
    
    @Override
     public BuildStep longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }
    
    @Override
     public BuildStep latitude(Float latitude) {
        this.latitude = latitude;
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
    private CopyOfBuilder(String id, String uniqueIdentifier, Integer price, String transferDate, String postcode, String propertyType, String newOrOld, String duration, String paon, String saon, String strees, String locality, String town, String district, String country, String categoryType, String recordStatus, Float longitude, Float latitude) {
      super.id(id);
      super.uniqueIdentifier(uniqueIdentifier)
        .price(price)
        .transferDate(transferDate)
        .postcode(postcode)
        .propertyType(propertyType)
        .newOrOld(newOrOld)
        .duration(duration)
        .paon(paon)
        .saon(saon)
        .strees(strees)
        .locality(locality)
        .town(town)
        .district(district)
        .country(country)
        .categoryType(categoryType)
        .recordStatus(recordStatus)
        .longitude(longitude)
        .latitude(latitude);
    }
    
    @Override
     public CopyOfBuilder uniqueIdentifier(String uniqueIdentifier) {
      return (CopyOfBuilder) super.uniqueIdentifier(uniqueIdentifier);
    }
    
    @Override
     public CopyOfBuilder price(Integer price) {
      return (CopyOfBuilder) super.price(price);
    }
    
    @Override
     public CopyOfBuilder transferDate(String transferDate) {
      return (CopyOfBuilder) super.transferDate(transferDate);
    }
    
    @Override
     public CopyOfBuilder postcode(String postcode) {
      return (CopyOfBuilder) super.postcode(postcode);
    }
    
    @Override
     public CopyOfBuilder propertyType(String propertyType) {
      return (CopyOfBuilder) super.propertyType(propertyType);
    }
    
    @Override
     public CopyOfBuilder newOrOld(String newOrOld) {
      return (CopyOfBuilder) super.newOrOld(newOrOld);
    }
    
    @Override
     public CopyOfBuilder duration(String duration) {
      return (CopyOfBuilder) super.duration(duration);
    }
    
    @Override
     public CopyOfBuilder paon(String paon) {
      return (CopyOfBuilder) super.paon(paon);
    }
    
    @Override
     public CopyOfBuilder saon(String saon) {
      return (CopyOfBuilder) super.saon(saon);
    }
    
    @Override
     public CopyOfBuilder strees(String strees) {
      return (CopyOfBuilder) super.strees(strees);
    }
    
    @Override
     public CopyOfBuilder locality(String locality) {
      return (CopyOfBuilder) super.locality(locality);
    }
    
    @Override
     public CopyOfBuilder town(String town) {
      return (CopyOfBuilder) super.town(town);
    }
    
    @Override
     public CopyOfBuilder district(String district) {
      return (CopyOfBuilder) super.district(district);
    }
    
    @Override
     public CopyOfBuilder country(String country) {
      return (CopyOfBuilder) super.country(country);
    }
    
    @Override
     public CopyOfBuilder categoryType(String categoryType) {
      return (CopyOfBuilder) super.categoryType(categoryType);
    }
    
    @Override
     public CopyOfBuilder recordStatus(String recordStatus) {
      return (CopyOfBuilder) super.recordStatus(recordStatus);
    }
    
    @Override
     public CopyOfBuilder longitude(Float longitude) {
      return (CopyOfBuilder) super.longitude(longitude);
    }
    
    @Override
     public CopyOfBuilder latitude(Float latitude) {
      return (CopyOfBuilder) super.latitude(latitude);
    }
  }
  
}

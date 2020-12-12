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

/** This is an auto generated class representing the UKCrimeData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UKCrimeData")
public final class UKCrimeData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField CRIME_ID = field("crimeId");
  public static final QueryField MONTH = field("month");
  public static final QueryField REPORTED_BY = field("reportedBy");
  public static final QueryField FALLS_WITHIN = field("fallsWithin");
  public static final QueryField LONGITUDE = field("longitude");
  public static final QueryField LATITUDE = field("latitude");
  public static final QueryField LOCATION = field("location");
  public static final QueryField LSOA_CODE = field("LSOACode");
  public static final QueryField LSOA_NAME = field("LSOAName");
  public static final QueryField CRIME_TYPE = field("CrimeType");
  public static final QueryField LAST_OUT_COME = field("lastOutCome");
  public static final QueryField CONTEXT = field("Context");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String crimeId;
  private final @ModelField(targetType="String") String month;
  private final @ModelField(targetType="String") String reportedBy;
  private final @ModelField(targetType="String") String fallsWithin;
  private final @ModelField(targetType="Float") Float longitude;
  private final @ModelField(targetType="Float") Float latitude;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="String") String LSOACode;
  private final @ModelField(targetType="String") String LSOAName;
  private final @ModelField(targetType="String") String CrimeType;
  private final @ModelField(targetType="String") String lastOutCome;
  private final @ModelField(targetType="String") String Context;
  public String getId() {
      return id;
  }
  
  public String getCrimeId() {
      return crimeId;
  }
  
  public String getMonth() {
      return month;
  }
  
  public String getReportedBy() {
      return reportedBy;
  }
  
  public String getFallsWithin() {
      return fallsWithin;
  }
  
  public Float getLongitude() {
      return longitude;
  }
  
  public Float getLatitude() {
      return latitude;
  }
  
  public String getLocation() {
      return location;
  }
  
  public String getLsoaCode() {
      return LSOACode;
  }
  
  public String getLsoaName() {
      return LSOAName;
  }
  
  public String getCrimeType() {
      return CrimeType;
  }
  
  public String getLastOutCome() {
      return lastOutCome;
  }
  
  public String getContext() {
      return Context;
  }
  
  private UKCrimeData(String id, String crimeId, String month, String reportedBy, String fallsWithin, Float longitude, Float latitude, String location, String LSOACode, String LSOAName, String CrimeType, String lastOutCome, String Context) {
    this.id = id;
    this.crimeId = crimeId;
    this.month = month;
    this.reportedBy = reportedBy;
    this.fallsWithin = fallsWithin;
    this.longitude = longitude;
    this.latitude = latitude;
    this.location = location;
    this.LSOACode = LSOACode;
    this.LSOAName = LSOAName;
    this.CrimeType = CrimeType;
    this.lastOutCome = lastOutCome;
    this.Context = Context;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UKCrimeData ukCrimeData = (UKCrimeData) obj;
      return ObjectsCompat.equals(getId(), ukCrimeData.getId()) &&
              ObjectsCompat.equals(getCrimeId(), ukCrimeData.getCrimeId()) &&
              ObjectsCompat.equals(getMonth(), ukCrimeData.getMonth()) &&
              ObjectsCompat.equals(getReportedBy(), ukCrimeData.getReportedBy()) &&
              ObjectsCompat.equals(getFallsWithin(), ukCrimeData.getFallsWithin()) &&
              ObjectsCompat.equals(getLongitude(), ukCrimeData.getLongitude()) &&
              ObjectsCompat.equals(getLatitude(), ukCrimeData.getLatitude()) &&
              ObjectsCompat.equals(getLocation(), ukCrimeData.getLocation()) &&
              ObjectsCompat.equals(getLsoaCode(), ukCrimeData.getLsoaCode()) &&
              ObjectsCompat.equals(getLsoaName(), ukCrimeData.getLsoaName()) &&
              ObjectsCompat.equals(getCrimeType(), ukCrimeData.getCrimeType()) &&
              ObjectsCompat.equals(getLastOutCome(), ukCrimeData.getLastOutCome()) &&
              ObjectsCompat.equals(getContext(), ukCrimeData.getContext());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCrimeId())
      .append(getMonth())
      .append(getReportedBy())
      .append(getFallsWithin())
      .append(getLongitude())
      .append(getLatitude())
      .append(getLocation())
      .append(getLsoaCode())
      .append(getLsoaName())
      .append(getCrimeType())
      .append(getLastOutCome())
      .append(getContext())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UKCrimeData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("crimeId=" + String.valueOf(getCrimeId()) + ", ")
      .append("month=" + String.valueOf(getMonth()) + ", ")
      .append("reportedBy=" + String.valueOf(getReportedBy()) + ", ")
      .append("fallsWithin=" + String.valueOf(getFallsWithin()) + ", ")
      .append("longitude=" + String.valueOf(getLongitude()) + ", ")
      .append("latitude=" + String.valueOf(getLatitude()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("LSOACode=" + String.valueOf(getLsoaCode()) + ", ")
      .append("LSOAName=" + String.valueOf(getLsoaName()) + ", ")
      .append("CrimeType=" + String.valueOf(getCrimeType()) + ", ")
      .append("lastOutCome=" + String.valueOf(getLastOutCome()) + ", ")
      .append("Context=" + String.valueOf(getContext()))
      .append("}")
      .toString();
  }
  
  public static CrimeIdStep builder() {
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
  public static UKCrimeData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UKCrimeData(
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      crimeId,
      month,
      reportedBy,
      fallsWithin,
      longitude,
      latitude,
      location,
      LSOACode,
      LSOAName,
      CrimeType,
      lastOutCome,
      Context);
  }
  public interface CrimeIdStep {
    BuildStep crimeId(String crimeId);
  }
  

  public interface BuildStep {
    UKCrimeData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep month(String month);
    BuildStep reportedBy(String reportedBy);
    BuildStep fallsWithin(String fallsWithin);
    BuildStep longitude(Float longitude);
    BuildStep latitude(Float latitude);
    BuildStep location(String location);
    BuildStep lsoaCode(String lsoaCode);
    BuildStep lsoaName(String lsoaName);
    BuildStep crimeType(String crimeType);
    BuildStep lastOutCome(String lastOutCome);
    BuildStep context(String context);
  }
  

  public static class Builder implements CrimeIdStep, BuildStep {
    private String id;
    private String crimeId;
    private String month;
    private String reportedBy;
    private String fallsWithin;
    private Float longitude;
    private Float latitude;
    private String location;
    private String LSOACode;
    private String LSOAName;
    private String CrimeType;
    private String lastOutCome;
    private String Context;
    @Override
     public UKCrimeData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UKCrimeData(
          id,
          crimeId,
          month,
          reportedBy,
          fallsWithin,
          longitude,
          latitude,
          location,
          LSOACode,
          LSOAName,
          CrimeType,
          lastOutCome,
          Context);
    }
    
    @Override
     public BuildStep crimeId(String crimeId) {
        Objects.requireNonNull(crimeId);
        this.crimeId = crimeId;
        return this;
    }
    
    @Override
     public BuildStep month(String month) {
        this.month = month;
        return this;
    }
    
    @Override
     public BuildStep reportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }
    
    @Override
     public BuildStep fallsWithin(String fallsWithin) {
        this.fallsWithin = fallsWithin;
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
    
    @Override
     public BuildStep location(String location) {
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep lsoaCode(String lsoaCode) {
        this.LSOACode = lsoaCode;
        return this;
    }
    
    @Override
     public BuildStep lsoaName(String lsoaName) {
        this.LSOAName = lsoaName;
        return this;
    }
    
    @Override
     public BuildStep crimeType(String crimeType) {
        this.CrimeType = crimeType;
        return this;
    }
    
    @Override
     public BuildStep lastOutCome(String lastOutCome) {
        this.lastOutCome = lastOutCome;
        return this;
    }
    
    @Override
     public BuildStep context(String context) {
        this.Context = context;
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
    private CopyOfBuilder(String id, String crimeId, String month, String reportedBy, String fallsWithin, Float longitude, Float latitude, String location, String lsoaCode, String lsoaName, String crimeType, String lastOutCome, String context) {
      super.id(id);
      super.crimeId(crimeId)
        .month(month)
        .reportedBy(reportedBy)
        .fallsWithin(fallsWithin)
        .longitude(longitude)
        .latitude(latitude)
        .location(location)
        .lsoaCode(lsoaCode)
        .lsoaName(lsoaName)
        .crimeType(crimeType)
        .lastOutCome(lastOutCome)
        .context(context);
    }
    
    @Override
     public CopyOfBuilder crimeId(String crimeId) {
      return (CopyOfBuilder) super.crimeId(crimeId);
    }
    
    @Override
     public CopyOfBuilder month(String month) {
      return (CopyOfBuilder) super.month(month);
    }
    
    @Override
     public CopyOfBuilder reportedBy(String reportedBy) {
      return (CopyOfBuilder) super.reportedBy(reportedBy);
    }
    
    @Override
     public CopyOfBuilder fallsWithin(String fallsWithin) {
      return (CopyOfBuilder) super.fallsWithin(fallsWithin);
    }
    
    @Override
     public CopyOfBuilder longitude(Float longitude) {
      return (CopyOfBuilder) super.longitude(longitude);
    }
    
    @Override
     public CopyOfBuilder latitude(Float latitude) {
      return (CopyOfBuilder) super.latitude(latitude);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder lsoaCode(String lsoaCode) {
      return (CopyOfBuilder) super.lsoaCode(lsoaCode);
    }
    
    @Override
     public CopyOfBuilder lsoaName(String lsoaName) {
      return (CopyOfBuilder) super.lsoaName(lsoaName);
    }
    
    @Override
     public CopyOfBuilder crimeType(String crimeType) {
      return (CopyOfBuilder) super.crimeType(crimeType);
    }
    
    @Override
     public CopyOfBuilder lastOutCome(String lastOutCome) {
      return (CopyOfBuilder) super.lastOutCome(lastOutCome);
    }
    
    @Override
     public CopyOfBuilder context(String context) {
      return (CopyOfBuilder) super.context(context);
    }
  }
  
}

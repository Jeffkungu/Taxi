package com.example.hava.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hava.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName ="trip_table")
public class Trip extends BaseObservable implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("pickup_lat")
    @Expose
    private Double pickupLat;
    @SerializedName("pickup_lng")
    @Expose
    private Double pickupLng;
    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;
    @SerializedName("dropoff_lat")
    @Expose
    private Double dropoffLat;
    @SerializedName("dropoff_lng")
    @Expose
    private Double dropoffLng;
    @SerializedName("dropoff_location")
    @Expose
    private String dropoffLocation;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("dropoff_date")
    @Expose
    private String dropoffDate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_rating")
    @Expose
    private Integer driverRating;
    @SerializedName("driver_pic")
    @Expose
    private String driverPic;
    @SerializedName("car_make")
    @Expose
    private String carMake;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_number")
    @Expose
    private String carNumber;
    @SerializedName("car_year")
    @Expose
    private Integer carYear;
    @SerializedName("car_pic")
    @Expose
    private String carPic;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("duration_unit")
    @Expose
    private String durationUnit;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("distance_unit")
    @Expose
    private String distanceUnit;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("cost_unit")
    @Expose
    private String costUnit;

    @BindingAdapter({"carPic"})
    public static void loadCarImage(ImageView imageView, String imageUrl) {
        String imagePath = "https://image.tmdb.org/t/p/w500" + imageUrl;
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.car))
                .into(imageView);
    }

    @BindingAdapter({"driverPic"})
    public static void loadDriverImage(ImageView imageView, String imageUrl) {
        String imagePath = "https://image.tmdb.org/t/p/w500" + imageUrl;
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.driver_pic))
                .into(imageView);
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public Double getPickupLat() {
        return pickupLat;
    }

    public Double getPickupLng() {
        return pickupLng;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public Double getDropoffLat() {
        return dropoffLat;
    }

    public Double getDropoffLng() {
        return dropoffLng;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public String getDropoffDate() {
        return dropoffDate;
    }

    public String getType() {
        return type;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public Integer getDriverRating() {
        return driverRating;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public void setPickupLng(Double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public void setDropoffLng(Double dropoffLng) {
        this.dropoffLng = dropoffLng;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setDropoffDate(String dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverRating(Integer driverRating) {
        this.driverRating = driverRating;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    @Bindable
    public String getDriverPic() {
        return driverPic;
    }

    public void setCarPic(String carPic) {
        this.carPic = carPic;
        notifyPropertyChanged(BR.carPic);
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public Integer getCarYear() {
        return carYear;
    }

    @Bindable
    public String getCarPic() {
        return carPic;
    }

    public void setDriverPic(String driverPic) {
        this.driverPic = driverPic;
        notifyPropertyChanged(BR.driverPic);
    }

    public Integer getDuration() {
        return duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public Double getDistance() {
        return distance;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public Integer getCost() {
        return cost;
    }

    public String getCostUnit() {
        return costUnit;
    }

    protected Trip(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.requestDate = ((String) in.readValue((String.class.getClassLoader())));
        this.pickupLat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.pickupLng = ((Double) in.readValue((Double.class.getClassLoader())));
        this.pickupLocation = ((String) in.readValue((String.class.getClassLoader())));
        this.dropoffLat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.dropoffLng = ((Double) in.readValue((Double.class.getClassLoader())));
        this.dropoffLocation = ((String) in.readValue((String.class.getClassLoader())));
        this.pickupDate = ((String) in.readValue((String.class.getClassLoader())));
        this.dropoffDate = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.driverId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.driverName = ((String) in.readValue((String.class.getClassLoader())));
        this.driverRating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.driverPic = ((String) in.readValue((String.class.getClassLoader())));
        this.carMake = ((String) in.readValue((String.class.getClassLoader())));
        this.carModel = ((String) in.readValue((String.class.getClassLoader())));
        this.carNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.carYear = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.carPic = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.durationUnit = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((Double) in.readValue((Double.class.getClassLoader())));
        this.distanceUnit = ((String) in.readValue((String.class.getClassLoader())));
        this.cost = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.costUnit = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Trip() {
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @SuppressWarnings({
                "unchecked"
        })

        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(status);
        dest.writeValue(requestDate);
        dest.writeValue(pickupLat);
        dest.writeValue(pickupLng);
        dest.writeValue(pickupLocation);
        dest.writeValue(dropoffLat);
        dest.writeValue(dropoffLng);
        dest.writeValue(dropoffLocation);
        dest.writeValue(pickupDate);
        dest.writeValue(dropoffDate);
        dest.writeValue(type);
        dest.writeValue(driverId);
        dest.writeValue(driverName);
        dest.writeValue(driverRating);
        dest.writeValue(driverPic);
        dest.writeValue(carMake);
        dest.writeValue(carModel);
        dest.writeValue(carNumber);
        dest.writeValue(carYear);
        dest.writeValue(carPic);
        dest.writeValue(duration);
        dest.writeValue(durationUnit);
        dest.writeValue(distance);
        dest.writeValue(distanceUnit);
        dest.writeValue(cost);
        dest.writeValue(costUnit);
    }
}

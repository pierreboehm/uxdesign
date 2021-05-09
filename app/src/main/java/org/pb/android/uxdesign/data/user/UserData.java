package org.pb.android.uxdesign.data.user;

public class UserData {

    private final String id;
    private final String name;
    private final String country;
    private final String countryCode;
    private final String state;
    private final String locality;
    private final String profession;

    private int listPosition = -1;

    private UserData(Builder builder) {
        id = builder.id;
        name = builder.name;
        country = builder.country;
        countryCode = builder.countryCode;
        state = builder.state;
        locality = builder.locality;
        profession = builder.profession;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getState() {
        return state;
    }

    public String getLocality() {
        return locality;
    }

    public String getProfession() {
        return profession;
    }

    public void setListPosition(int position) {
        listPosition = position;
    }

    public int getListPosition() {
        return listPosition;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        String id;
        String name;
        String country;
        String countryCode;
        String state;
        String locality;
        String profession;

        public Builder setData(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.country = user.getCountry();
            this.countryCode = user.getCountryCode();
            this.state = user.getState();
            this.locality = user.getLocality();
            this.profession = user.getProfession();

            return this;
        }

        public UserData getUserData() {
            return new UserData(this);
        }
    }

}

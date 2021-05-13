package org.pb.android.uxdesign.data.user;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "User")
public class User {

    @Attribute(name = "id")
    String userId;

    @Element(name = "Name")
    String name;

    @Element(name = "Country")
    String country;

    @Element(name = "CountryCode", required = false)
    String countryCode;

    @Element(name = "State", required = false)
    String state;

    @Element(name = "Locality")
    String locality;

    @Element(name = "Profession")
    String profession;

    public String getId() {
        return userId == null ? "" : userId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public String getCountryCode() {
        return countryCode == null ? "" : countryCode;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public String getLocality() {
        return locality == null ? "" : locality;
    }

    public String getProfession() {
        return profession == null ? "" : profession;
    }
}

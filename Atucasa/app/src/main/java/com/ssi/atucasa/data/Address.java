package com.ssi.atucasa.data;

/**
 * Created by Enrique Vargas on 29/03/2015.
 */
public class Address {
    private long id;
    private String street;
    private String houseNumber;
    private String reference1Street;
    private String reference2Street;
    private String building;
    private String referencePhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getReference1Street() {
        return reference1Street;
    }

    public void setReference1Street(String reference1Street) {
        this.reference1Street = reference1Street;
    }

    public String getReference2Street() {
        return reference2Street;
    }

    public void setReference2Street(String reference2Street) {
        this.reference2Street = reference2Street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getReferencePhone() {
        return referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {

        return street;
    }

    public String getAllAddress(){
        String aux = this.getStreet()+" entre "+this.getReference1Street();
        return aux;
    }
}

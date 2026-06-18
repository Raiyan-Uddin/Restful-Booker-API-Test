package com.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for Booking Dates
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDates {

    @JsonProperty("checkin")
    private String checkin;

    @JsonProperty("checkout")
    private String checkout;

    public BookingDates() {
    }

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public static BookingDatesBuilder builder() {
        return new BookingDatesBuilder();
    }

    public static class BookingDatesBuilder {
        private String checkin;
        private String checkout;

        public BookingDatesBuilder checkin(String checkin) {
            this.checkin = checkin;
            return this;
        }

        public BookingDatesBuilder checkout(String checkout) {
            this.checkout = checkout;
            return this;
        }

        public BookingDates build() {
            return new BookingDates(checkin, checkout);
        }
    }
}



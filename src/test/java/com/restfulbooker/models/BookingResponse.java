package com.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for Booking Response (includes booking ID)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {

    @JsonProperty("bookingid")
    private Integer bookingid;

    @JsonProperty("booking")
    private Booking booking;

    public BookingResponse() {
    }

    public BookingResponse(Integer bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public static BookingResponseBuilder builder() {
        return new BookingResponseBuilder();
    }

    public static class BookingResponseBuilder {
        private Integer bookingid;
        private Booking booking;

        public BookingResponseBuilder bookingid(Integer bookingid) {
            this.bookingid = bookingid;
            return this;
        }

        public BookingResponseBuilder booking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public BookingResponse build() {
            return new BookingResponse(bookingid, booking);
        }
    }
}



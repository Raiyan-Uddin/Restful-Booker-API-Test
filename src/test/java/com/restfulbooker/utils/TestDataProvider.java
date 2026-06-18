package com.restfulbooker.utils;

import com.restfulbooker.models.Auth;
import com.restfulbooker.models.Booking;
import com.restfulbooker.models.BookingDates;
import org.testng.annotations.DataProvider;

/**
 * Data provider class for test data
 */
public class TestDataProvider {

    @DataProvider(name = "validBookingData")
    public Object[][] getValidBookingData() {
        return new Object[][] {
            {
                Booking.builder()
                        .firstname("Alice")
                        .lastname("Williams")
                        .totalprice(800)
                        .depositpaid(true)
                        .bookingdates(BookingDates.builder().checkin("2024-03-01").checkout("2024-03-05").build())
                        .additionalneeds("Breakfast")
                        .build()
            },
            {
                Booking.builder()
                        .firstname("Bob")
                        .lastname("Brown")
                        .totalprice(1200)
                        .depositpaid(false)
                        .bookingdates(BookingDates.builder().checkin("2024-04-01").checkout("2024-04-08").build())
                        .additionalneeds("WiFi, Parking")
                        .build()
            },
            {
                Booking.builder()
                        .firstname("Charlie")
                        .lastname("Davis")
                        .totalprice(600)
                        .depositpaid(true)
                        .bookingdates(BookingDates.builder().checkin("2024-05-01").checkout("2024-05-03").build())
                        .additionalneeds(null)
                        .build()
            }
        };
    }

    @DataProvider(name = "validAuthData")
    public Object[][] getValidAuthData() {
        return new Object[][] {
            {Auth.builder().username("admin").password("password123").build()},
            {Auth.builder().username("user").password("pass123").build()}
        };
    }
}


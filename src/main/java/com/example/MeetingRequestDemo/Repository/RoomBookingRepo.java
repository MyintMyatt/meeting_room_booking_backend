package com.example.MeetingRequestDemo.Repository;

import com.example.MeetingRequestDemo.Model.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RoomBookingRepo extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBookingID(String bookingID);

    /*In your query, you're referring to the entity Booking, but the table name in the query should be the entity name, not the table name.
    In JPQL (Java Persistence Query Language), you use the entity class name, not the table name. Since your entity class is Booking,
    the query should reference b as an alias for the Booking entity*/
    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status, b.HOD = :HOD, b.HODActionDateTime = :HODActionDateTime, b.HODcomment = :HODcomment WHERE b.bookingID = :bookingID") // not tblBooking
    void updateHODDetails(@Param("bookingID") String bookingID,@Param("status")String status, @Param("HOD") String HOD, @Param("HODActionDateTime")LocalDateTime HODActionDateTime, @Param("HODcomment") String HODcomment);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status, b.admin = :admin, b.adminActionDateTime = :adminActionDateTime, b.adminComment = :adminComment WHERE b.bookingID = :bookingID") // not tblBooking
    void updateAdminDetails(@Param("bookingID") String bookingID,@Param("status")String status, @Param("admin") String admin, @Param("adminActionDateTime")LocalDateTime adminActionDateTime, @Param("adminComment") String adminComment);



}


/*
* @Transactional?
Transaction Management:
A transaction ensures that all the operations within a method are executed together. If one operation fails, the entire transaction can be rolled back to prevent any data inconsistency.
For example, in your method, you're updating the Booking entity's status, HOD, HODActionDateTime, and HODcomment. These operations should be treated as a single unit of work.
* If any part of this process fails (e.g., a database constraint violation or any other error), all changes should be rolled back to keep the database in a consistent state.

Atomicity:
A transaction is atomic, meaning either all changes are applied, or none are. In this case,
*if you update the status, HOD, HODActionDateTime, and HODcomment together, a failure in updating any of them will trigger a rollback to avoid partial updates (which could lead to inconsistent data).

Consistency:
By marking this method with @Transactional, you ensure that if any unexpected errors occur during the update (like a network issue or database issue), the transaction is rolled back, and the system remains in a consistent state.
* */
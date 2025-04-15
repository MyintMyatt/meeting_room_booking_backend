package com.example.MeetingRequestDemo.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "tblUsers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {

    @Id
    @GeneratedValue(generator = "user-id-generator")
    @GenericGenerator(name = "user-id-generator", strategy = "com.example.MeetingRequestDemo.Util.UserIdGenerator")
    @Column(name = "userID", nullable = false, length = 10)
    private String userID;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "role", nullable = false)
    private String role;

    /*✅ cascade = CascadeType.ALL*/
//    “Any operation you perform on the parent (Users), also applies to the child (Booking) automatically.”
//    JPA will
//    Save the user
//    Save all the user.getBookings() too
//    Likewise:
//    remove() the user → all bookings will be removed
//    merge() or refresh() → cascades to bookings too
//    But CascadeType.ALL means all of these:
//    PERSIST, MERGE, REMOVE, REFRESH, DETACH

    /*✅ orphanRemoval = true*/
//    “If a Booking is removed from user.getBookings(), then delete it from the database too.”
//    user.getBookings().remove(0);
//    JPA will:
//    Delete that Booking from the database when the user is saved
//    Without orphanRemoval, the booking would just be unlinked from the user — not deleted.

//    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Booking> bookings;


}

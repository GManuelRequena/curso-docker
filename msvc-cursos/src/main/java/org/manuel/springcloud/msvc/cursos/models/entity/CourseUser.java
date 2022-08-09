package org.manuel.springcloud.msvc.cursos.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "courses_users")
public class CourseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", unique = true)
    private Long userID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if (!(obj instanceof CourseUser)){
            return false;
        }
        CourseUser o = (CourseUser) obj;
        return this.userID != null && this.userID.equals(o.userID);
    }
}

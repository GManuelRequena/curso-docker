package org.manuel.springcloud.msvc.cursos.services;

import org.manuel.springcloud.msvc.cursos.clients.UserClientRest;
import org.manuel.springcloud.msvc.cursos.models.User;
import org.manuel.springcloud.msvc.cursos.models.entity.Course;
import org.manuel.springcloud.msvc.cursos.models.entity.CourseUser;
import org.manuel.springcloud.msvc.cursos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserClientRest userClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> byId(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> courseDB = courseRepository.findById(courseId);
        if (courseDB.isPresent()) {
            User userMsvc = userClientRest.userById(user.getId());

            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserID(userMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> courseDB = courseRepository.findById(courseId);
        if (courseDB.isPresent()) {
            User newUserMsvc = userClientRest.createUser(user);

            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserID(newUserMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> unassignUser(User user, Long courseId) {
        Optional<Course> courseDB = courseRepository.findById(courseId);
        if (courseDB.isPresent()) {
            User userMsvc = userClientRest.userById(user.getId());

            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserID(userMsvc.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
}

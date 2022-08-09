package com.manuel.springcloud.msvc.ususarios.services;

import com.manuel.springcloud.msvc.ususarios.models.entity.User;
import com.manuel.springcloud.msvc.ususarios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    private UserRepository getUserRepository(){
        return this.userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return (List<User>) getUserRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> byId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return getUserRepository().save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getUserRepository().deleteById(id);
    }

    @Override
    public Optional<User> byEmail(String email) {
        return getUserRepository().findByEmail(email);
    }
    @Override
    public Optional<User> porEmail(String email){
        return getUserRepository().porEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return getUserRepository().existsByEmail(email);
    }

    @Override
    public List<User> getAllById(Iterable<Long> ids) {
        return (List<User>) getUserRepository().findAllById(ids);
    }
}

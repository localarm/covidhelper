package com.pavel.covhelper.services;

import com.pavel.covhelper.persistencelayer.repositories.DepartmentRepository;
import com.pavel.covhelper.persistencelayer.repositories.UserRepository;
import com.pavel.covhelper.persistencelayer.entities.User;
import com.pavel.covhelper.security.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public void registerDepartmentViewer(String login, String password, long departmentId) throws UserAlreadyExistException,
            DepartmentNotFoundException {
        if (!departmentRepository.existsById(departmentId)) {
            throw  new DepartmentNotFoundException("Department with given id" + departmentId + "does not exists");
        }
        if (usernameExists(login)) {
            throw new UserAlreadyExistException("There is a account with that username: "
                    + login);
        }
        User user = new User();
        user.setUsername(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setDepartmentId(departmentId);
        user.setAuthority("ROLE_Department_viewer");
        userRepository.save(user);
    }

    private boolean usernameExists(final String name) {
            return userRepository.findByUsername(name) != null;
    }

}

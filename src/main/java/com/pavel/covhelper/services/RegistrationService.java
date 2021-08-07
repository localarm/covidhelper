package com.pavel.covhelper.services;

import com.pavel.covhelper.security.UserAlreadyExistException;

public interface RegistrationService {
    /**регистрирует нового пользователя с уникальным именем в системе, который сможет контролировать отдельное управление
     * @param login логин нового пользователя, должен быть уникальным и не null
     * @param password пароль новогопользователя, не null
     * @param departmentId айди управления/филиала, к которому привязан пользователь
     * @throws UserAlreadyExistException если пользователь с таким имененм уже существует
     * @throws DepartmentNotFoundException если управление или филиал уже существуют
     */
    void registerDepartmentViewer(String login, String password, long departmentId) throws UserAlreadyExistException,
            DepartmentNotFoundException;
}

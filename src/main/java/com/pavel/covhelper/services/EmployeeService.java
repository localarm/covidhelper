package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.DepartmentWithEmployeesDTO;

public interface EmployeeService {

    /**Загружает из базы данных информацию о департаменте с отделами и сотрудниками
     * @param departmentId существующий айди департамента
     * @return управление/филиал с подсчитанным количеством статусов по нему, отделы и сотрудников в них
     * @throws DepartmentNotFoundException если управления/филиала с таким айди не существует
     */
    DepartmentWithEmployeesDTO getEmployeesByDepartmentId(Long departmentId) throws DepartmentNotFoundException;

    /**Удаляет сотрудника по его айди
     * @param employeeId существующий айди сотрудника
     * @throws EmployeeNotFoundException если сотрудника с таким айди не существует
     */
    void deleteById(long employeeId) throws EmployeeNotFoundException;

    /**Добавляет нового сотрудника к отделу
     * @param firstname Имя сотрудника, not null
     * @param secondname Отчетсво сотрудника, not null
     * @param lastname Фамилия сотрудника, not null
     * @param post Должность сотрудника, not null
     * @param address Адрес рабоыт сотрудинка, not null
     * @param status статус сотрудника по вакцинам, not null
     * @param divisionId существующий айди отдела, к которому привязан сотрудник
     * @throws DivisionNotFoundException Если отдел с переданным айди не существуют
     */
    void addEmployee(String firstname, String secondname, String lastname, String post, String address,
                     String status, long divisionId) throws DivisionNotFoundException;

    /**Обновляет информацию о сотруднике, заменяет все поля сотрудника
     * @param firstname Имя сотрудника, not null
     * @param secondname Отчетсво сотрудника, not null
     * @param lastname Фамилия сотрудника, not null
     * @param post Должность сотрудника, not null
     * @param address Адрес рабоыт сотрудинка, not null
     * @param status статус сотрудника по вакцинам, not null
     * @param divisionId существующий айди отдела, к которому привязан сотрудник
     * @param employeeId айди департамента, к которому привязан отдел
     * @throws DivisionNotFoundException если отдела с переданным айди не существует
     * @throws EmployeeNotFoundException если сотрудника с переданным айди не существует
     */
    void updateEmployee(String firstname, String secondname, String lastname, String post, String address,
                           String status, long employeeId, long divisionId) throws DivisionNotFoundException,
            EmployeeNotFoundException;
}

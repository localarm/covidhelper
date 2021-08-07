package com.pavel.covhelper.services;

public interface DivisionService {

     /**добавляет отдел с переданным именем к существующему управлению/филиалу
      * @param divisionName название добавляемого отдела, not null
      * @param departmentId айди филиала/управления, в котором будет находиться отдел
      * @throws DepartmentNotFoundException если управления с переданным айди не существует
      * @throws IllegalStateException если divisionName равен null
      */
     void addDivision (String divisionName, long departmentId) throws DepartmentNotFoundException, IllegalStateException;

     /**Удаляет отдел и сотрудников, относящихся к нему
      * @param divisionId существующий айди отдела
      * @throws DivisionNotFoundException если управления с переданным айди не существует
      */
     void deleteById(long divisionId) throws DivisionNotFoundException;
}

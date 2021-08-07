package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.UnitDepartmentsAndCountsDTO;

/**Интерфейс для взаимодействия с управлениями
 */
public interface DepartmentService {

    /**Предоставляет информацию об управлении определенного структурного подразделения
     * @param unitId существующий айди структурного подразделения
     * @return возвращает информацию о структурном подразделении, подсчитанные статусы и информацию о связанных
     * с ним управлениях
     * @throws UnitNotFoundException если структурного подразделения с переданным айди не существует
     */
    UnitDepartmentsAndCountsDTO getDepartmentsByUnitId(long unitId) throws UnitNotFoundException;

    /**Добавляет новое управление к структурному подразделению
     * @param departmentName название управления
     * @param unitId существующий айди структурного подразделения
     * @throws UnitNotFoundException если структурного подразделения с переданным айди не существует
     * @throws IllegalStateException если departmentName равен null
     */
    void addDepartment(String departmentName, long unitId) throws UnitNotFoundException, IllegalStateException;

    /**Удаляет выбранный депаратмент по айди со всеми отделами и сотрудниками в нем
     * @param departmentId айди управления, not null
     * @throws DepartmentNotFoundException если управление с таким айди не существует
     */
    void deleteDepartment(long departmentId) throws DepartmentNotFoundException;

}

package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.UnitsWithCounts;


public interface UnitService {

    /**для запроса информации о структурных подразделениях
     * @return Возвращает информацию о структурных подразделениях, такую как имя, айди, данные о прививках
     */
    UnitsWithCounts loadUnitsAndCounts();
}

package ru.akirakozov.sd.refactoring.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetResolver<T> {

    T resolve(ResultSet resultSet) throws SQLException;

}

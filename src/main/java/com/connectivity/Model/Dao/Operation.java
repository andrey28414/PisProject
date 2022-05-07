package com.connectivity.Model.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Operation{
    List<?> newOperation(Connection connection) throws Exception;
}

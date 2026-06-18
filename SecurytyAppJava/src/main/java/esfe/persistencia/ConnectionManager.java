package esfe.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String STR_CONNECTION = "jdbc:sqlserver://localhost:1433;" +
            "jdbc:sqlserver://localhost:1433;" +
            "encrypt=true;" +
            "database=SecurytyAppJava;" +
            "trustServerCertificate=true;" +
            "user=Nayeli366;" +
            "password=NAYELI1234";

    private Connection connection;

    private static ConnectionManager instance;

    private ConnectionManager()
    {
        this.connection = null;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Error al cargar el driver JDBC de SQLServer", e);
        }
    }

    public synchronized Connection connect() throws SQLException
    {
        if(this.connection == null || this.connection.isClosed())
        {
            try
            {
                this.connection = DriverManager.getConnection(STR_CONNECTION);
            }
            catch (SQLException ex)
            {
                throw new SQLException("Error al conectar a la base de datos: "
                        + ex.getMessage(), ex);
            }
        }
        return this.connection;
    }

    public void disconnect() throws SQLException
    {
        if(this.connection != null)
        {
            try
            {
                this.connection.close();
            }
            catch (SQLException ex)
            {
                throw new SQLException("Error al cerrar la conexion: "
                        + ex.getMessage(), ex);
            }
            finally {
                this.connection = null;
            }
        }
    }

    public static synchronized ConnectionManager getInstance()
    {
        if(instance == null)
        {
            instance = new ConnectionManager();
        }
        return instance;
    }
}

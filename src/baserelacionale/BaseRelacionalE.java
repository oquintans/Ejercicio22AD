/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baserelacionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oracle
 */
public class BaseRelacionalE {

    Connection conn;
    ResultSet rs;
    Product pro;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BaseRelacionalE brD = new BaseRelacionalE();
        brD.connection();
        brD.listar();
    }

    public void connection() {
        try {
            String driver = "jdbc:oracle:thin:";
            String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
            String porto = "1521";
            String sid = "orcl";
            String usuario = "hr";
            String password = "hr";
            String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

            conn = DriverManager.getConnection(url);
            System.out.println("Conexion Establecida.\n");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {

            PreparedStatement pS = conn.prepareStatement("select produtos.* from produtos");
            rs = (ResultSet) pS.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                pro = new Product();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnType(i) == Types.VARCHAR) {
                        if (rsmd.getColumnName(i).equalsIgnoreCase("CODIGO")) {
                            pro.setCod(rs.getString(i));
                        } else {
                            pro.setDesc(rs.getString(i));
                        }

                    } else if (rsmd.getColumnType(i) == Types.NUMERIC) {
                        pro.setPrice(rs.getInt(i));
                    }
                }
                System.out.println(pro.toString());
            }

            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalE.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}

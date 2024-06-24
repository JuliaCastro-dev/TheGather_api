package com.thegather.api.tests;

import com.thegather.api.infrastructure.DbContext;

import javax.swing.*;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection con = DbContext.getConnection();
        if(con==null) {
            JOptionPane.showMessageDialog(null, "Erro ao estabelecer conexão");
        }else {
            JOptionPane.showMessageDialog(null, "Conexão estabelecida com sucesso!");
        }
    }
}

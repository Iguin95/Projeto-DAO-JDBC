package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

//Fábrica: Classe auxiliar responsável por instanciar os meus DAO's através de métodos estáticos
public class DaoFactory {
	
	//Método que instanciará e retornará um SellerDAO
	//Dessa forma, minha classe vai expor um método que retorna um tipo da Interface, mas internamente ela vai instanciar uma implementação que ficará oculta para o programa principal
	public static SellerDAO createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}

}

package application;

import java.time.LocalDate;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		LocalDate data = LocalDate.now();
		Department obj = new Department(1, "Books");
		System.out.println(obj);
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", data, 3000.0, obj);
		System.out.println(seller);
		
		//injeção de dependência sem explicitar a implementação, dessa forma eu chamo a fábrica de DAO que através do método, instanciará e criara o SellerDAO
		SellerDAO sellerDao = DaoFactory.createSellerDao();
	}
	
	
}

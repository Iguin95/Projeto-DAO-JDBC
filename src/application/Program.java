package application;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {

		//injeção de dependência sem explicitar a implementação, dessa forma eu chamo a fábrica de DAO que através do método, instanciará e criara o SellerDAO
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("< ============ TEST 1: seller FindById ============ > ");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
	}
	
	
}

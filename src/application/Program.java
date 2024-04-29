package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		// injeção de dependência sem explicitar a implementação, dessa forma eu chamo a
		// fábrica de DAO que através do método, instanciará e criara o SellerDAO
		SellerDAO sellerDao = DaoFactory.createSellerDao();

		System.out.println("< ============ TEST 1: seller FindById ============ > ");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n< ============ TEST 2: seller FindByDepartment ============ > ");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n< ============ TEST 3: seller FindAll ============ > ");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n< ============ TEST 4: seller insert ============ > ");
		Seller newSeller = new Seller(null, "Kalita", "kali@gmail.com", new Date(), 9000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		
	}

}

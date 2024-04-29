package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
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
		
		System.out.println("\n< ============ TEST 4: seller Insert ============ > ");
		Seller newSeller = new Seller(null, "Kalita", "kali@gmail.com", new Date(), 9000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("\n< ============ TEST 5: seller Update ============ > ");
		seller = sellerDao.findById(1); //peguei o vendedor de id igual a 1.
		seller.setName("Martha Wayne");
		sellerDao.update(seller);
		System.out.println("Update Completed!");
		
		System.out.println("\n< ============ TEST 6: seller Delete ============ > ");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed!");
		
		sc.close();
	}

}

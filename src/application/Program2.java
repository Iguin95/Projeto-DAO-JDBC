package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		DepartmentDAO depDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n< ============ TEST 1: Department Insert ============ > ");
		Department newDep = new Department(null, "Logística");
		//depDao.insert(newDep);
		System.out.println("Departamento inserido! Novo id = " + newDep.getId());
		
		System.out.println("\n< ============ TEST 2: Department FindById ============ > ");
		Department dep = depDao.findById(3);
		System.out.println(dep);
		sc.close();

	}

}

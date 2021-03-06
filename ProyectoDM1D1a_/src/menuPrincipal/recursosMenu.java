package menuPrincipal;

import java.util.ArrayList;
import java.util.Scanner;

import ValidacionDatos.VD;
import bbdd.conexionBD;
import buscador.herramientasContactos;
import objetos.aficion;
import objetos.contacto;

public class recursosMenu {
	static boolean bucle = true;

	static int menuPrincial() {
		int aux = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("+=========== Menú ============+");
		System.out.println("1. Ver contactos.");
		System.out.println("2. Añadir contacto.");
		System.out.println("3. Eliminar Contacto.");
		System.out.println("4. Modificar Contacto");
		System.out.println("5. Añadir Aficiones.");
		System.out.println("6. Borrar todo el contenido.");
		System.out.println("7. Salir.");
		System.out.println("+=========== Menú ============+");
		try {
			System.out.println("Seleccione una opcion: ");
			aux = sc.nextInt();
		} catch (Exception e) {
			System.err.println("Dato erroneo.");
		}
		return aux;
	}

	static void verContactos() {
		System.out.println();
		System.out.println("+=========== Ver contactos ============+");
		herramientasContactos.verContactos();
		System.out.println();
	}

	static void anadirContacto() {
		Scanner sc = new Scanner(System.in);
		System.out.println("+=========== Añadir contacto ============+");
		herramientasContactos.crearContacto();
		System.out.println("+=========== =============== ============+");
	}

	static void eliminarContacto() {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("+=========== Eliminar contacto ============+");
		System.out.println("Buscar contacto que quiere eliminar: ");
		String nombre = sc.next();
		System.out.println("======= Resultados =======");
		herramientasContactos.buscarContactos(nombre);
		System.out.println("Seleciona ID: ");
		int i = sc.nextInt();
		System.out.println("Ha seleccionado: ");
		herramientasContactos.buscarContactosId(i);
		System.out.println("¿Desea eliminar? S/N");
		char c = sc.next().charAt(0);
		if (c == 'S' || c == 's') {
			herramientasContactos.deleteCont(i);
		}
	}

	static void modificarContacto() {
		conexionBD con = new conexionBD();
		con.connect();
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("+=========== Actualizar contacto ============+");
		System.out.println("Buscar contacto que quiere actualizar: ");
		String nombre = sc.next();
		System.out.println("======= Resultados =======");
		herramientasContactos.buscarContactos(nombre);
		System.out.println("Seleciona ID: ");
		int i = sc.nextInt();
		System.out.println("Ha seleccionado: ");
		herramientasContactos.buscarContactosId(i);
		System.out.println(
				"¿Qué quiere actualizar?\n" + "1. Nombre\n" + "2. Telefono\n" + "3. Aficiones\n" + "4. Ninguna");
		int optionMod = sc.nextInt();
		switch (optionMod) {
		case 1: {
			System.out.println("Nombre: ");
			String nueNombre = sc.next();
			herramientasContactos.cambiarNombre(i, nueNombre);
			break;
		}
		case 2: {
			System.out.println("¿Añadir, modificar o eliminar? 1.2.3");
			int op = sc.nextInt();
			switch (op) {
			case 1: {
				System.out.println("Nuevo telefono: ");
				String nueTelefono = sc.next();
				con.saveTelefono(1, nueTelefono);
				break;
			}
			case 2: {
				System.out.println("Introduzca el telefono que quiere modificar: ");
				String Tlf = sc.next();
				con.buscarTelefono(Tlf);
				System.out.println("S/N");
				char c = sc.next().charAt(0);
				sc.nextLine();
				if (c == 's' || c == 'S') {
					System.out.println("Nuevo tlf: ");
					String nueTlf = sc.next();
					con.actualizarTelefono(Tlf, nueTlf, i);
				}
				break;
			}
			case 3: {
				System.out.println("Introduzca el telefono que quiere eliminar: ");
				String Tlf = sc.next();
				con.buscarTelefono(Tlf);
				System.out.println("S/N");
				char c = sc.next().charAt(0);
				sc.nextLine();
				if (c == 's' || c == 'S') {
					con.deleteTelefono(Tlf, i);
				}
				break;
			}
			}
			break;
		}
		case 3: {
			System.out.println("¿Añadir, modificar o eliminar? 1.2.3");
			int op = sc.nextInt();
			switch (op) {
			case 1: { // Añadir Aficion
				ArrayList<aficion> aficiones = con.verAficiones();
				for (aficion aficion : aficiones) {
					System.out.print("ID: ");
					System.out.println(aficion.getId());
					System.out.print("Nombre: ");
					System.out.println(aficion.getNombre());
				}
				System.out.println("¿Quiere añadir alguna aficion que no exista? S/N");
				char c = sc.next().charAt(0);
				sc.nextLine();
				if (c == 's' || c == 'S') {
					System.out.println("Introduzca Aficion:");
					con.crearAficion(sc.next());
					sc.nextLine();
				}
				System.out.println("Introduzca el id que quiere añadir: ");
				int id = sc.nextInt();
				con.saveAficionContacto(i, id);
				break;
			}
			case 2: { // Modificar Aficion
				System.out.println("ID Aficion a modificar del contacto: ");
				int af = sc.nextInt();
				con.deleteAficiones(i, af);
				ArrayList<aficion> aficiones = con.verAficiones();
				for (aficion aficion : aficiones) {
					System.out.print("ID: ");
					System.out.println(aficion.getId());
					System.out.print("Nombre: ");
					System.out.println(aficion.getNombre());
				}
				System.out.println("¿Quiere añadir alguna aficion que no exista? S/N");
				char c = sc.next().charAt(0);
				sc.nextLine();
				if (c == 's' || c == 'S') {
					System.out.println("Introduzca Aficion:");
					con.crearAficion(sc.next());
					sc.nextLine();
				}
				System.out.println("Introduzca todos los ids que quiere añadir: ");
				String[] ids = sc.nextLine().split(" ");
				for (String id : ids) {
					con.saveAficionContacto(i, Integer.parseInt(id));
				}
				break;
			}
			case 3: { // Eliminar Aficion
				System.out.println("ID Aficion a eliminar del contacto: ");
				int af = sc.nextInt();
				con.deleteAficiones(i, af);
				break;
			}
			}
			break;
		}
		case 4: {
			break;
		}
		}
		con.close();
	}

	public static void crearAficiones() {
		conexionBD con = new conexionBD();
		con.connect();
		Scanner sc = new Scanner(System.in);
		ArrayList<aficion> aficiones = con.verAficiones();
		System.out.println("Las aficiones que lleva hasta ahora son: ");
		for (aficion aficion : aficiones) {
			System.out.print("ID: ");
			System.out.println(aficion.getId());
			System.out.print("Nombre: ");
			System.out.println(aficion.getNombre());
		}
		boolean crearaficion = true;
		System.out.println("Aquí podrá crear aficiones pàra mas tarde añadirlas a los contactos.");
		while (crearaficion) {
			System.out.println("Introduzca Aficion:");
			con.crearAficion(sc.next());
			sc.nextLine();
			System.out.println("¿Quiere añadir más aficiones? S/N");
			char c = sc.next().charAt(0);
			sc.nextLine();
			if (c == 'n' || c == 'N')
				crearaficion = false;
		}
		aficiones = con.verAficiones();
		System.out.println("Las aficiones que lleva hasta ahora son: ");
		for (aficion aficion : aficiones) {
			System.out.print("ID: ");
			System.out.println(aficion.getId());
			System.out.print("Nombre: ");
			System.out.println(aficion.getNombre());
		}
		con.close();
	}
	
	public static void borrarBBDD() {
		conexionBD con = new conexionBD();
		con.connect();
		Scanner sc = new Scanner(System.in);
		System.err.println("ESTÁ A PUNTO DE BORRAR TODA LA INFORMACIÓN DE LA BBDD ¿ESTA SEGURO? S/N");
		char c = sc.next().charAt(0);
		sc.nextLine();
		if (c == 's' || c == 'S')
			con.deleteBBDD();
		con.close();
	}
}

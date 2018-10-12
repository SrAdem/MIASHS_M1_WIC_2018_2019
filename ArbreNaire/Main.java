package Arbre;

public class Main {

	public static void main(String[] args) {
		NoeudAbstrait n;
		Marque m = new Marque(null);
		n = m.ajout("coucou");
		n = n.ajout("coup");
		n = n.ajout("attention");
		n = n.ajout("attenta");
		
		n = n.ajout("réminiscence");
		n = n.ajout("remaniment");
		n = n.ajout("exécrable");
		n = n.ajout("exécuter");
		
		n = n.ajout("pas");
		n = n.ajout("la");
		n = n.ajout("peine");
		n = n.ajout("de");
		n = n.ajout("me");
		n = n.ajout("remercier");
		
		n.suppr("");
		n.suppr("coup");
		n.suppr("at");
		n.suppr("attenta");
		n.suppr("exécuter");
		System.out.println(n.toString("")); 
		//On met un String vide en paramètre simplement pour appeler la fonction.
		//Vous pouvez écricre qqchose, vous verez bien ce que ca fait.
		

	}

}

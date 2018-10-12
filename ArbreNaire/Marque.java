package Arbre;

public class Marque extends NoeudAbstrait {
	public Marque(NoeudAbstrait frere) {
		super(frere);
	}

	@Override
	public boolean contient(String s) {
		if (s.isEmpty())
			return true;
		if (this.frere == null)
			return false;
		return this.frere.contient(s);
		
//		return s.isEmpty() || (this.frere != null && this.frere.contient(s));
	}

	@Override
	public boolean prefixe(String s) {
		if (s.isEmpty()) 
			return true;
		//
		if (this.frere == null)
			return false;
		return this.frere.prefixe(s);
		
//		return s.isEmpty() || (this.frere != null && this.frere.prefixe(s));
	}

	@Override
	public int nbMots() {
		return 1 + (frere == null ? 0 : frere.nbMots());
	}

	@Override
	public NoeudAbstrait ajout(String s) {
		/* Ici, si on retourne this, c'est que le paramètre 
		 * String contenait un mot qui existe déjà dans l'arbre*/
		if (s.isEmpty())
			return this;	
		
		/* Dans le cas ou la marque n'a pas de frere, cela veut dire 
		 * que nous sommes quelque part dans l'arbre où les lettres restants dans le parametre
		 * n'existent pas. C'est pour ca qu'on créer les lettres du parametre, 
		 * de la fin au début. Puis on donne la prmière lettre comme frere à la marque.
		 * On retourn this pour "dire" au noeud qui à executer la fonction ajout() sur la marque
		 * que son fils est la marque	cf ajout() de la classe Noeud */
		if (this.frere == null) {
			NoeudAbstrait n = new Marque(null);
			for (int i = s.length() - 1; i >= 0; i--)
				n = new Noeud(null, n, s.charAt(i));
			this.frere = n;
			return this;
		}
		
		/* On entre dans ce cas si la marque à un frere.
		 * sont nouveau frere sera ce que retournera la fonction ajout()
		 * sur son frere courant.
		 * ex: son frere est noeud contenant z, or le premier caractère du parametre est un e
		 * ce que doit retourner la fonction est donc le noeud contenant e */
		this.frere = this.frere.ajout(s);
		
		/* Et ca... Bein on s'en fout un peu */
		return this;
	}

	@Override
	public NoeudAbstrait suppr(String s) {
		/* Si s est vide quand on arrive sur une marque, c'est que le mot initial en parametre
		 * existe bien dans l'arbre, donc on le supprime.
		 * Pour ce faire, on return au noeud qui à appele la fontion sur le noeud, null.
		 * Pour indique qu'il n'a plus de fils, et donc qu'il n'a plus de raison d'exister
		 * Tin ca dramatise tout de suite le truc... */
		if(s.isEmpty()) {
			return null;
		}
		
		/* Si la marque à un frere, un peu comme dans la fonction ajout(), son nouveau frere
		 * sera ce que retournera la fonction suppr() de son frere courant. */
		if(this.frere != null) {
			this.frere = this.frere.suppr(s);
			return this;
		}
		
		/* Si s n'est pas vide et que la marque n'a pas de frere, c'est que le mot en parametre
		 * n'existe pas dans l'arbre. Donc on return la marque pour dire que : "tout va bien" */
		return this;
	}

	@Override 
	// Regardez pas ce toString il est politiquement pas correcte.
	// Mais c'est une facon de faire quand on a pas de parametre.
	public String toString() {
		if (this.frere != null)
			return " \n" + this.frere.toString();
		return " \n";
	}
	
	@Override
	public String toString(String s) {
		/* Ici le parmetre contient les valeurs des noeuds qui sont au dessus de la marque. 
		 * Donc la marque doit juste ajouter le caractere de fin de mot '\n' et si il a un frere
		 * (qui lui n'est pas une marque évidemment) il return en plus ce que son frere lui return.
		 * Oubliez pas que le paramètre contient les caractères d'un mot.
		 * Exemple: s = con
		 * Or "con" est un mot de notre arbre donc la marque permet "d'afficher" le mot con
		 * Et le frere de la marque contient la lettre 't' car le mot "contient" existe aussi
		 * dans l'arbre. */
		return s + "\n" + ((this.frere == null) ? "" : this.frere.toString(s));
	}
}

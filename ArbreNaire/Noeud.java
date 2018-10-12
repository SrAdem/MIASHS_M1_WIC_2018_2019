package Arbre;

public class Noeud extends NoeudAbstrait {
	
	private char valeur;
	private NoeudAbstrait fils;
	
	public Noeud(NoeudAbstrait frere, NoeudAbstrait	fils, char valeur) {
		super(frere);
		this.valeur = valeur;
		if (fils == null)
			throw new IllegalArgumentException("fils ne peut pas être null");
		this.fils = fils;
	}

	@Override
	public boolean contient(String s) {
		if (s.isEmpty()) 
			return false;
		
		char c = s.charAt(0);
		if (c < this.valeur) 
			return false;
		if (c == this.valeur)
			return fils.contient(s.substring(1)); //appelle soit .contient() d'un noeud soit d'une marque
		if (this.frere == null) 
			return false;
		return frere.contient(s);
		
	}

	@Override
	public boolean prefixe(String s) {
		if (s.isEmpty()) 
			return true;
		
		char c = s.charAt(0);
		if (c < this.valeur) 
			return false;
		if (c == this.valeur)
			return fils.prefixe(s.substring(1));
		if (this.frere == null) 
			return false;
		return frere.prefixe(s);
	}

	@Override
	public int nbMots() {
		return fils.nbMots() + (frere == null ? 0 : frere.nbMots());
//		if (this.frere != null)
//			return this.fils.nbMots() + this.frere.nbMots();
//		return this.fils.nbMots();
	}

	@Override
	public NoeudAbstrait ajout(String s) {
		/* Si notre parametre est vide, c'est qu'il nous manque la marque pour que l'arbre
		 * puisse contenir le mot du parametre. Puisque nous sommes sur un noeud, 
		 * on cree une marque qui a pour frere le noeud courrant et on retourn la marque
		 * pour dire au noeud précédent "qui" est sont nouveau fils
		 * Tin le mec change de fils comme ca ... */
		if (s.isEmpty()) {
			return new Marque(this);
		}
		
		char c = s.charAt(0);
		/* Si le caractere est plus petit que la valeur du noeud (a<s) cela veut dire
		 * quele neoud contenant le caracter c n'existe pas.
		 * Donc on cree tout les caractere du parametre s de la marque jusqu'au premier caratere
		 * cf ajout() de marque, c'est de la meme manière. 
		 * ET !! Vu que c < valeur c'est que c'est un noeud qui se place avant
		 * donc on return le nouveau noeud cree pour qu'il soit le nouveau frere ou fils
		 * du noeud ayant fait appel à la fonction. */
		if (c < this.valeur) {
			NoeudAbstrait n = new Marque(null);
			for (int i = s.length() - 1; i >= 0; i--)
				n = new Noeud(null, n, s.charAt(i));
			n.frere = this;
			return n;
		}
		
		/* Si le caractere est égal à la valeur du noeud, le fils execute la fonction ajouter()
		 * avec le premier caractere en moins.
		 * Et c'est ici que l'on change de fils selon ce que va retrun le fils. */
		if (c == this.valeur) {
			fils = fils.ajout(s.substring(1));
			return this;
		}
		
		/* Si la marque n'a pas de frere c'est que le carctere est superieur (s>a),
		 * vu qu'on a deja fait inferieur et egal, il reste supperieur.
		 * C'est la même connerie, les noeuds n'existent pas donc on les cree
		 * de la marque jusqu'au 1 er caractere de la chaine en parametre 
		 * Et comme ce noeud ce place avant le nouveau noeud cree
		 * On return celui ci. */
		if (this.frere == null) {
			NoeudAbstrait n = new Marque(null);
			for (int i = s.length() - 1; i >= 0; i--)
				n = new Noeud(null, n, s.charAt(i));
			this.frere = n;
			return this;
		}
		
		/* C'est le cas où la marque a un frere et que 'c' est superieur à la valeur du noeud
		 * Le noeud demande à son frere d'executer la fonction ajout avec pour parametre s
		 * on enleve aucune lettre...
		 * Le nouveau frere = le frere que return le frere courant.
		 * PS : On ne peut pas cree ici car on ne peut pas connaitre la valeur du frere
		 * donc si notre c = t, que la valeur du noeud = a et celui du frere = g, alors
		 * on va placer le 't' entre le 'a' et le 'g'. */
		this.frere = this.frere.ajout(s);
		
		// Pas important...
		return this;
	}

	@Override
	public NoeudAbstrait suppr(String s) {
		/* Si le parametre s est vide ou que son premier caractere est inferieur à 
		 * la valeur du noeud, c'est que le mot qui était en parametre n'existe pas dans l'arbre
		 * donc on return this pour dire que rien ne change... En gros */
		if(s.isEmpty() || s.charAt(0) < this.valeur) {
			return this;
		}

		/* Si le 1er caractere est superieur a la valeur du noeud, et qu'on a un frere
		 * on execute la fonction chez le frere avec le parametre 's'.
		 * Et le nouveau frere du noeud = le return du frere courant.
		 * Si il return null. C'est que le frere a était supprimer car le mot exister,
		 * sinon bein rien...
		 * Et on return this car meme si le frere n'existe plus 
		 * bein cette partie de l'arbre existe encore.  */
		if(this.frere != null && s.charAt(0) > this.valeur) {
			this.frere = this.frere.suppr(s);
			return this;
		}
		
		// On arrive ici si le premier caractere = a la valeur du noeud
		NoeudAbstrait n = this.fils.suppr(s.substring(1));
		// Si le fils est n'existe plus, car supprimer, car le mot existe dans l'arbre.
		if (n == null) {
			//Si le noeud a un frere, on le return pour dire au noeud précédent 
			//de changer de fils ou de frere.
			if(this.frere != null)
				return this.frere;
			return null; //Sinon on retourn null car le noeud est supprimer à son tour 
		}
		
		//Dans le dernier cas, on retourn le noeud pour qu'il y ai aucun changement. 
		return this;
	}

	@Override
	// Ne regardez pas mes crimes !!!
	// Remarquez j'aurais pu dire "meurtre" mais non j'ai dit "crime!"
	// cf: un film ...
	public String toString() {		
		String s = "";
		String[] split = new String[100]; //TODO: pas correct
		split = this.fils.toString().split("\n");
		
		for (String sub : split) {
			s+= this.valeur + sub + "\n";
		}
		if(this.frere != null)
			s += this.frere.toString();
		return s;
	}
	
	@Override
	/* Tout con...
	 * On appel la fonction chez le fils en rajoutant le caracrere du noeud dans
	 * la chaine de caractere. Et on appel la fonction chez le frere sans l'ajouter car
	 * si c'est son frere, c'est que c'est un autre mot.
	 * exemple: on est sur un noeud contenant 'n' pour écrire le mot "con" contenu dans l'arbre
	 * mais le frere c'est le caractere 's' pour "cosmopolitain" par exemple... */
	public String toString(String s) {
		return this.fils.toString(s + this.valeur) + ((this.frere == null) ? "" : this.frere.toString(s));
	}
}

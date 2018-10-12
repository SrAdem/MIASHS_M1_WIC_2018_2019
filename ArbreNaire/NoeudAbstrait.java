package Arbre;

public abstract class NoeudAbstrait {
	protected NoeudAbstrait frere;
	public NoeudAbstrait(NoeudAbstrait frere) {
		this.frere = frere;
	}
	public abstract boolean contient(String s);
	public abstract boolean prefixe(String s);
	public abstract int nbMots(); // nombre d'éléments
	public abstract NoeudAbstrait ajout(String s);
	public abstract NoeudAbstrait suppr(String s);
	public abstract String toString(); // éléments séparés par \n
	public abstract String toString(String s);
}

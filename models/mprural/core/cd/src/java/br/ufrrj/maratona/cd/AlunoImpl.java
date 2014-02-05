// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in andromda-hibernate-cartridge.
//
package br.ufrrj.maratona.cd;

/**
 * @see br.ufrrj.maratona.cd.Aluno
 */

public class AlunoImpl extends br.ufrrj.maratona.cd.AlunoAbstract {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 1818638296274348834L;

	public AlunoImpl()
	{

	}

	protected void handleAdicionarProblema(br.ufrrj.maratona.cd.Problema problema, java.util.Date data, java.lang.String codigo, br.ufrrj.maratona.eo.DificuldadeProblema dificuldade)
	{
		Resolucao resolucao = new ResolucaoImpl();
		resolucao.setProblema(problema);
		resolucao.setData(data);
		resolucao.setCodigo(codigo);
		resolucao.setDificuldade(dificuldade);

		this.getResolucaos().add(resolucao);
	}
}
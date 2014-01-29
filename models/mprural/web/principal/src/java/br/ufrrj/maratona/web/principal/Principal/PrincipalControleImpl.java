
// license-header java merge-point
package br.ufrrj.maratona.web.principal.Principal;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.andromda.presentation.bpm4struts.ViewContainer;

import br.ufrrj.maratona.cd.AlunoDAO;
import br.ufrrj.maratona.cd.AlunoDAOImpl;
import br.ufrrj.maratona.cs.maratona.MaratonaHandlerBI;
import br.ufrrj.maratona.vo.AlunoVO;

/**
 * @see br.ufrrj.maratona.web.principal.Principal.PrincipalControle
 */
public class PrincipalControleImpl extends PrincipalControle
{
    /**
     * @see br.ufrrj.maratona.web.principal.Principal.PrincipalControle#carregaDados(br.ufrrj.maratona.web.principal.Principal.CarregaDadosForm)
     */
    public final void carregaDados(br.ufrrj.maratona.web.principal.Principal.CarregaDadosForm form, ViewContainer container) throws Exception
    {
    	AlunoDAOImpl alunoDAO = new AlunoDAOImpl();
    	
    	Collection topAlunos = alunoDAO.recuperaTopAlunosMes(5, null);
    	
    	form.setTopAlunos(topAlunos);
    }
}

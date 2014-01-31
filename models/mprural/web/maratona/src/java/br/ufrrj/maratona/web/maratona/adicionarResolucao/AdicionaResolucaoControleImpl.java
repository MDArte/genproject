// license-header java merge-point
package br.ufrrj.maratona.web.maratona.adicionarResolucao;

import java.util.Collection;
import java.util.Date;

import org.andromda.presentation.bpm4struts.ViewContainer;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.action.FilterAction;
import br.ufrrj.maratona.cd.Problema;
import br.ufrrj.maratona.cd.ProblemaImpl;
import br.ufrrj.maratona.vo.ProblemaVO;

/**
 * @see br.ufrrj.maratona.web.maratona.adicionarResolucao.AdicionaResolucaoControle
 */
public class AdicionaResolucaoControleImpl extends AdicionaResolucaoControle
{
    /**
     * @see br.ufrrj.maratona.web.maratona.adicionarResolucao.AdicionaResolucaoControle#carregandoDados(br.ufrrj.maratona.web.maratona.adicionarResolucao.CarregandoDadosForm)
     */
    public final void carregandoDados(br.ufrrj.maratona.web.maratona.adicionarResolucao.CarregandoDadosForm form, ViewContainer container) throws Exception
    {
    	form.setData((new Date()).toString());
    }

	public void adicionaResolucao(AdicionaResolucaoForm form, ViewContainer container) throws Exception
	{
		form.setIdResolucao(1l);
		saveSuccessMessage("adiciona.resolucao.uc.adiciona.resolucao.adicionar.sucesso", container);
	}	

	protected String[] urlAdicionaResolucaoAutoComplete(String query, ViewContainer container) throws Exception
	{
		ProblemaVO problemaVO = new ProblemaVO();
		problemaVO.setUrl(query);
		
		Collection problemas = ServiceLocator.instance().getMaratonaHandlerBI().filterProblema(new ProblemaImpl(), new FilterAction(problemaVO, null));
		
		String[] urls = new String[problemas.size()];
		
		int i = 0;
		
		for(Problema problema:(Collection<Problema>) problemas)
		{
			urls[i] = problema.getUrl();
			i++;
		}
		
		return urls;
	}
}



package cu.uci.abcd.opac.ui.model;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class SuggestionViewArea extends BaseEditableArea {	
	
	Suggestion suggestion;
	Group suggestionGroup;
	Button closeBtn;
			
	@Override
	public boolean closable() {		
		return true;
	}

	

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,IVisualEntityManager manager) {   
	
		suggestion = entity.getRow();
		
		addComposite(parent);
		
		
		String lastStringSuggestion = MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTIONS);
        suggestionGroup = new Group(parent, SWT.NORMAL);
        add(suggestionGroup);

        List<String> leftListSuggestion = new LinkedList<>();
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_TITLE1));
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_AUTHOR));
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_PUBLICATION_DATE));
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_EDITOR));
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_STATE_FILTER));
        leftListSuggestion.add(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_NOTE));

        List<String> rigthListSuggestion = new LinkedList<>();
        rigthListSuggestion.add(suggestion.getTitle());
        if (suggestion.getAuthor().equals("")) {
        	rigthListSuggestion.add("-");
		}else
        rigthListSuggestion.add(suggestion.getAuthor());
        if (suggestion.getPublicationDate() == null) {
        	rigthListSuggestion.add("-");
		}else
        rigthListSuggestion.add(new SimpleDateFormat("dd-MM-yyyy").format(suggestion.getPublicationDate()));
        if (suggestion.getEditorial().equals("")) {
        	rigthListSuggestion.add("-");
		}else
        rigthListSuggestion.add(suggestion.getEditorial());
        rigthListSuggestion.add(suggestion.getState().toString());
        rigthListSuggestion.add(suggestion.getNote());
        
        CompoundGroup.printGroup(suggestionGroup, lastStringSuggestion, leftListSuggestion, rigthListSuggestion);
	
		
		l10n();
		return parent;
	}
	
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {

	}
}
